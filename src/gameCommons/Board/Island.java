package gameCommons.Board;

import gameCommons.Menus.DiscardMenu;
import gameCommons.Menus.Menu;
import gameCommons.Menus.PlayerSelectionMenu;
import gameCommons.Player;
import gfx.Assets;
import gfx.Text;
import ui.Button;
import ui.Interacts;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Island implements Interacts {
    private Handler handler;
    public Case[][] cases;
    public int xOffset, yOffset;
    public ArrayList<Player> player;
    private int isPlaying;
    private boolean[] artifactsGathered;
    private int[] heliport = new int[4];
    private int[][] casesWithArtifacts = new int[4][4];
    private ArrayList<Color> colors;
    public Menu menu;
    public TreasureDeck treasureDeck;
    private DiscardMenu discardMenu;
    private int floodGauge, flood;
    private FloodDeck floodDeck;
    private Button endOfTurnButton;
    private PlayerSelectionMenu playerSelectionMenu;

    public Island(Handler handler, int length, int numberOfPlayers, ArrayList<Color> colors) {
        this.handler = handler;
        if (length * handler.getPixelByCase() < handler.getWidth() && length * handler.getPixelByCase() < handler.getHeight()) {
            cases = new Case[length][length];
            xOffset = (handler.getWidth() - length * handler.getPixelByCase()) / 2 - (handler.getSpacing() * length) / 2;
            yOffset = (handler.getHeight() - length * handler.getPixelByCase()) / 2 - (handler.getSpacing() * length) / 2;
        } else {
            System.out.println("Too wide grid");
            System.exit(-1);
        }
        this.colors = colors;
        this.player = new ArrayList<>();
        this.artifactsGathered = new boolean[4];
        //Arrays.fill(artifactsGathered, true); //debugging purpose
        this.menu = new Menu(handler, this);
        this.treasureDeck = new TreasureDeck();
        this.discardMenu = new DiscardMenu(handler, this);
        this.isPlaying = 0;
        this.floodGauge = 0;
        this.flood = 0;
        this.floodDeck = new FloodDeck(handler);
        this.endOfTurnButton = new Button((float) (handler.getWidth() - Assets.turn[0].getWidth()) / 2, (float) (handler.getHeight() - 2 * (Assets.playerDim + handler.getSpacing())), Assets.turn[0].getWidth(), Assets.playerDim * 2, Assets.turn, this::endOfTurn);
        this.playerSelectionMenu = new PlayerSelectionMenu(handler, this);
        init(numberOfPlayers);
    }

    private void init(int numberOfPlayer) {
        int offset;
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {
                cases[i][j] = new Case(handler, i, j, xOffset, yOffset, this);
                if (cases[i][j].isArtifact) {
                    offset = casesWithArtifacts[cases[i][j].artifactValue][0] != 0 ? 2 : 0;
                    casesWithArtifacts[cases[i][j].artifactValue][offset] = i;
                    casesWithArtifacts[cases[i][j].artifactValue][1 + offset] = j;
                }
            }
        }
        for (int i = 0; i < numberOfPlayer; i++) {
            player.add(new Player(handler, this, colors.get(i), i));
        }
    }

    public boolean win() {
        for (boolean b : artifactsGathered) {
            if (!b) return false;
            for (Player p : player) {
                if (!(p.position[0] >= heliport[0] && p.position[0] <= heliport[0] + handler.getPixelByCase() && p.position[1] >= heliport[1] && p.position[1] <= heliport[1] + handler.getPixelByCase()))
                    return false;
            }
        }
        return false;
    }

    public boolean lose() {
        if (cases[heliport[2]][heliport[3]].getState() == 2) { //flooded heliport
            handler.setDeath(0);
            return true;
        } else if (blockedPlayer()) { //blocked player
            handler.setDeath(1);
            return true;
        } else if (drownedArtifact()) { //artifact's location flooded
            handler.setDeath(2);
            return true;
        } else if (floodGauge == 9) { //water level too high
            handler.setDeath(3);
            return true;
        } else return false;
    }

    public boolean blockedPlayer() {
        Case[] c = new Case[4];
        int[] verify = new int[4];
        for (Player p : player) {
            for (int i = -1; i < 2; i += 2) {
                c[i + 1] = getClickedCase(new MouseEvent(new java.awt.Button(), 0, 0, 0, p.position[0] + i * (handler.getPixelByCase() + handler.getSpacing()), p.position[1], 0, false));
                c[i + 2] = getClickedCase(new MouseEvent(new java.awt.Button(), 0, 0, 0, p.position[0], p.position[1] + i * (handler.getPixelByCase() + handler.getSpacing()), 0, false));
            }
            for (int i = 0; i < verify.length; i++) {
                verify[i] = c[i] == null ? 1 : 0;
            }
            if (Utils.allEquals(verify, 1)) {
                handler.setColor(p.color);
                return true;
            }
        }
        return false;
    }

    public boolean drownedArtifact() {
        for (int i = 0; i < casesWithArtifacts.length; i++) {
            if (!cases[casesWithArtifacts[i][0]][casesWithArtifacts[i][1]].isVisible && !cases[casesWithArtifacts[i][2]][casesWithArtifacts[i][3]].isVisible && !artifactsGathered[i]) {
                handler.setArtifact(i);
                return true;
            }
        }
        return false;
    }

    public void endOfTurn() {
        int i = 0;
        if (floodGauge >= 7) flood = 5;
        else if (floodGauge >= 5) flood = 4;
        else if (floodGauge >= 2) flood = 3;
        else flood = 2;
        do {
            Integer[] tile = floodDeck.drawCard();
            if (cases[tile[0]][tile[1]].getState() == 0) cases[tile[0]][tile[1]].setState(1);
            else if(cases[tile[0]][tile[1]].getState() == 1){
                ArrayList<Player> onCasePlayers = playersOnTheCase(cases[tile[0]][tile[1]], true);
                if (onCasePlayers.size() != 0) {
                    for (Player p : onCasePlayers) {
                        moveToOtherCase(p, tile[0], tile[1]);
                    }
                }
                cases[tile[0]][tile[1]].setState(2);
                floodDeck.discard(tile);
            }
            i++;
        } while (i < flood);
        for (Player p : player) {
            p.resetAction();
        }
        isPlaying++;
        if (isPlaying == player.size()) isPlaying = 0;
    }

    private void moveToOtherCase(Player p, int x, int y) {
        if (y + 1 < cases.length && cases[x][y + 1].getState() != 2) movePlayer(cases[x][y + 1], p); //bas
        else if (y - 1 >= 0 && cases[x][y - 1].getState() != 2) movePlayer(cases[x][y - 1], p); //haut
        else if (x + 1 < cases.length && cases[x + 1][y].getState() != 2) movePlayer(cases[x + 1][y], p); //gauche
        else if (x - 1 >= 0 && cases[x - 1][y].getState() != 2) movePlayer(cases[x - 1][y], p); //droite
        else System.exit(-1);
    }

    public void thirstCase(Case clickedCase, boolean nearby) {
        for (Case[] aCase : cases) {
            for (int j = 0; j < cases.length; j++) {
                if (aCase[j] == clickedCase && aCase[j].getState() == 1) aCase[j].setState(0);
            }
        }
        if (!nearby) player.get(isPlaying).delSpecialInventory(1);
        else player.get(isPlaying).addAction();
    }

    public void movePlayer(Case clickedCase, boolean nearby) {
        if (!nearby) {
            int i = (player.get(isPlaying).position[0] - xOffset) / (handler.getSpacing() + handler.getPixelByCase());
            int j = (player.get(isPlaying).position[1] - yOffset) / (handler.getSpacing() + handler.getPixelByCase());
            playerSelectionMenu.setPlayersOnCase(playersOnTheCase(new Case(handler, i, j, xOffset, yOffset), false));
            playerSelectionMenu.setClickedCase(clickedCase);
            playerSelectionMenu.setActive(true);
        } else {
            player.get(isPlaying).position = new int[]{clickedCase.x, clickedCase.y};
            player.get(isPlaying).position[0] += Assets.playerDim * alreadyOccupied(player.get(isPlaying));
            player.get(isPlaying).addAction();
        }
    }

    public void movePlayer(Case c, Player p2) {
        if (!blockedPlayer()) {
            for (Player p : player) {
                if (p.equals(p2)) {
                    p.position = new int[]{c.x, c.y};
                    p.position[0] += Assets.playerDim * alreadyOccupied(p);
                }
            }
        }
    }

    public void moveMultiplePlayers(ArrayList<Player> playerToMove, Case c) {
        player.get(isPlaying).delSpecialInventory(0);
        playerToMove.add(0, player.get(isPlaying));
        for (Player p : playerToMove) {
            movePlayer(c, p);
        }
        player.get(isPlaying).addAction();
    }

    public int alreadyOccupied(Player p) {
        int offset = 0, backup = 0;
        do {
            if (offset != backup) backup = offset;
            for (Player value : player) {
                if (!value.equals(p) && value.position[0] == p.position[0] + offset * Assets.playerDim && value.position[1] == p.position[1]) offset++;
            }
        } while (offset != backup);
        return offset;
    }

    public void gatherArtifact(int num) {
        artifactsGathered[num] = true;
        player.get(isPlaying).inventory[num] -= 1; //a changer a 4
        player.get(isPlaying).addAction();
    }

    public ArrayList<Player> playersOnTheCase(Case c, boolean noPlayerPlaying) {
        ArrayList<Player> res = new ArrayList<>();
        for (Player p : player) {
            if (onCase(p, c) && (noPlayerPlaying || !p.equals(player.get(isPlaying)))) res.add(p);
        }
        return res;
    }

    public boolean onCase(Player p, Case c) {
        return p.position[0] >= c.x && p.position[0] <= c.x + handler.getPixelByCase() && p.position[1] >= c.y && p.position[1] <= c.y + handler.getPixelByCase();
    }

    public void trade(Player p, int artifactValue) {
        for (Player players : player) {
            if (players == p) players.addInventory(artifactValue);
            else if (players == player.get(isPlaying)) players.delInventory(artifactValue);
        }
    }

    public void draw() {
        String effect = treasureDeck.drawCard();
        switch (effect) {
            case "flooded":
                floodGauge++;
                break;
            case "helicopter":
                player.get(isPlaying).addSpecialInventory(0);
                break;
            case "sandbag":
                player.get(isPlaying).addSpecialInventory(1);
                break;
            default:
                player.get(isPlaying).addInventory(Utils.artifactToValue(Integer.parseInt(effect)));
                break;
        }
        player.get(isPlaying).addAction();
        if (player.get(isPlaying).inventorySize() > 5) {
            discardMenu.setPlayer(player.get(isPlaying));
            discardMenu.setActive(true);
        }
    }

    public void discard(int i) {
        if (i < 4) treasureDeck.discard(new TreasureDeck.Card(Assets.keys[i], Utils.invValueToString(i)));
        else treasureDeck.discard(new TreasureDeck.Card(Assets.specialCards[i - 4], Utils.invValueToString(i)));
        player.get(isPlaying).delInventory(i);
    }


    /* UPDATE & RENDER */

    public void update() {
        menu.update();
        for (Case[] aCase : cases) {
            for (int j = 0; j < cases.length; j++) {
                aCase[j].update();
            }
        }
    }

    public void render(Graphics g) {
        //render the button
        endOfTurnButton.render(g);
        //render "it's player.isPlaying's turn"
        Text.drawString(g, "It's " + player.get(isPlaying).toString() + "'s turn.", handler.getWidth() / 2, 50, true, Color.WHITE, Assets.font45);
        //render the board
        for (Case[] aCase : cases) {
            for (int j = 0; j < cases[0].length; j++) {
                aCase[j].render(g);
            }
        }
        //render the players
        for (Player p : player) {
            p.render(g);
        }
        //render the gathered artifacts
        artifactRender(g);
        //render the special inventory
        player.get(isPlaying).renderSpecialInventory(g);
        //render player's description
        player.get(isPlaying).renderDescription(g);
        //render the deck
        if (!treasureDeck.isEmpty())
            g.drawImage(Assets.cardsBack, handler.getWidth() - 3 * Assets.dim - 3 * handler.getSpacing(), handler.getHeight() - (Assets.dim + Assets.dim * 2 / 3 + handler.getSpacing() * 4), null);
        g.drawImage(treasureDeck.lastGraveCardSprite(), handler.getWidth() - 2 * Assets.dim - handler.getSpacing(), handler.getHeight() - (Assets.dim + Assets.dim * 2 / 3 + handler.getSpacing() * 4), null);
        //render action
        Text.drawString(g, "Actions left :", handler.getWidth() * 2 / 3 + Assets.dim / 2, handler.getHeight() * 2 / 3 + Assets.dim * 2, true, Color.WHITE, Assets.font45);
        Text.drawString(g, Integer.toString(3 - player.get(isPlaying).getAction()), handler.getWidth() * 2 / 3 + Assets.dim / 2, handler.getHeight() * 2 / 3 + Assets.dim * 2 + 50, true, Color.WHITE, Assets.font45);
        //render the gauge
        g.drawImage(Assets.gauge[floodGauge], handler.getWidth() - 2 * handler.getSpacing(), handler.getSpacing(), Assets.gauge[flood].getWidth(), handler.getHeight() - 30, null); //-> futur jauge
        //render the menus
        menu.render(g);
        discardMenu.render(g);
        playerSelectionMenu.render(g);
    }

    private void artifactRender(Graphics g) {
        for (int i = 0; i < artifactsGathered.length; i++) {
            if (artifactsGathered[i]) {
                int a = Utils.numToX(i);
                int b = Utils.numToY(i);
                g.drawImage(Assets.artifacts[i], cases[b][a].x, cases[b][a].y, null);
            }
        }
    }

    /* Mouse Manager */
    public void onMouseClicked(MouseEvent e) {
        if (menu.isActive()) menu.onMouseClicked(e);
        else if (discardMenu.isActive()) discardMenu.onMouseClicked(e);
        else if (playerSelectionMenu.isActive()) playerSelectionMenu.onMouseClicked(e);
        else if (nearPlayer(e) || player.get(isPlaying).getSpecialInventory(0) != 0 || player.get(isPlaying).getSpecialInventory(0) != 0) {
            Case clickedCase = getClickedCase(e);
            if (clickedCase != null) {
                if (!nearPlayer(e)) this.menu.setNearby(false);
                else this.menu.setNearby(true);
                this.menu.setX(e.getX());
                this.menu.setY(e.getY());
                this.menu.setClickedCase(clickedCase);
                this.menu.setPlayer(player.get(isPlaying));
                this.menu.setVisible(true);
            }
        }
        endOfTurnButton.onMouseClicked(e);
    }

    public boolean nearPlayer(MouseEvent e) {
        int i = (player.get(isPlaying).position[0] - xOffset) / (handler.getSpacing() + handler.getPixelByCase());
        int j = (player.get(isPlaying).position[1] - yOffset) / (handler.getSpacing() + handler.getPixelByCase());
        i = i*(handler.getSpacing() + handler.getPixelByCase()) + xOffset;
        j = j*(handler.getSpacing() + handler.getPixelByCase()) + yOffset;
        Rectangle horizontalPos = new Rectangle(i - handler.getPixelByCase() - handler.getSpacing(), j, 3 * handler.getPixelByCase() + 2*handler.getSpacing(), handler.getPixelByCase());
        Rectangle verticalPos = new Rectangle(i, j - handler.getPixelByCase() - handler.getSpacing(), handler.getPixelByCase(), 3 * handler.getPixelByCase() + 2*handler.getSpacing());
        return horizontalPos.contains(e.getX(), e.getY()) || verticalPos.contains(e.getX(), e.getY());
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        endOfTurnButton.onMousePressed(e);
        menu.onMousePressed(e);
        playerSelectionMenu.onMousePressed(e);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        endOfTurnButton.onMouseReleased(e);
        menu.onMouseReleased(e);
        playerSelectionMenu.onMouseReleased(e);
    }

    public void onMouseMove(MouseEvent e) {
        if (menu.isActive()) menu.onMouseMove(e);
        else if (discardMenu.isActive()) discardMenu.onMouseMove(e);
        else if (playerSelectionMenu.isActive()) playerSelectionMenu.onMouseMove(e);
        else {
            for (Case[] aCase : cases) {
                for (int j = 0; j < cases.length; j++) {
                    aCase[j].onMouseMove(e);
                }
            }
            endOfTurnButton.onMouseMove(e);
        }
    }

    /* Getters & Setters */

    public int[] getStarterCases(Color color) {
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases.length; j++) {
                if (cases[i][j].color == Color.BLUE) {
                    heliport = new int[]{i * (handler.getSpacing() + handler.getPixelByCase()) + xOffset,
                            j * (handler.getSpacing() + handler.getPixelByCase()) + yOffset, i, j};
                }
                if (cases[i][j].color == color)
                    return new int[]{i * handler.getSpacing() + i * handler.getPixelByCase() + xOffset,
                            j * handler.getSpacing() + j * handler.getPixelByCase() + yOffset};
            }
        }
        return null;
    }

    public int getBoardTile() {
        int number;
        do {
            number = Handler.r.nextInt(Assets.board.length);
        } while (handler.getTakenNumbers().contains(number));
        handler.addNumber(number);
        return number;
    }

    public Case getClickedCase(MouseEvent e) {
        int i = (e.getX() - xOffset) / (handler.getSpacing() + handler.getPixelByCase());
        int j = (e.getY() - yOffset) / (handler.getSpacing() + handler.getPixelByCase());
        if (i < cases.length && j < cases.length && i >= 0 && j >= 0 && cases[i][j].isVisible) return cases[i][j];
        else return null;
    }
}