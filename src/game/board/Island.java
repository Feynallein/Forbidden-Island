package game.board;

import game.Player;
import game.menus.ActionMenu;
import game.menus.DiscardMenu;
import game.menus.PlayerSelectionMenu;
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
    private int isPlaying, floodGauge, flood;
    private boolean[] artifactsGathered;
    private int[] heliport = new int[4];
    private int[][] casesWithArtifacts = new int[4][4];
    private ArrayList<Color> colors;
    public ActionMenu actionMenu;
    public TreasureDeck treasureDeck;
    private DiscardMenu discardMenu;
    private FloodDeck floodDeck;
    private Button endOfTurnButton;
    private PlayerSelectionMenu playerSelectionMenu;

    public Island(Handler handler, int length, int numberOfPlayers, ArrayList<Color> colors) {
        this.handler = handler;
        this.handler.resetTakenNumbers();
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
        this.actionMenu = new ActionMenu(handler, this);
        this.treasureDeck = new TreasureDeck();
        this.discardMenu = new DiscardMenu(handler, this);
        this.isPlaying = 0;
        this.floodGauge = 0;
        this.flood = 0;
        this.floodDeck = new FloodDeck(handler);
        this.endOfTurnButton = new Button((float) (handler.getWidth() - (3 * Assets.dim + Assets.buttonDim)) / 2, (float) (handler.getHeight() - 2 * (Assets.playerDim + handler.getSpacing())), (3 * Assets.dim + Assets.buttonDim), Assets.buttonDim, Assets.turn, this::endOfTurn);
        this.playerSelectionMenu = new PlayerSelectionMenu(handler, this);
        init(numberOfPlayers);
    }

    /* Initialize the board and the players */
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

    /* End of turn methode */
    public void endOfTurn() {
        int i = 0;
        if (floodGauge >= 7) flood = 5;
        else if (floodGauge >= 5) flood = 4;
        else if (floodGauge >= 2) flood = 3;
        else flood = 2;
        do {
            Integer[] tile = floodDeck.drawCard();
            if (cases[tile[0]][tile[1]].getState() == 0) cases[tile[0]][tile[1]].setState(1);
            else if (cases[tile[0]][tile[1]].getState() == 1) {
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

    /* Return if the players win */
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

    /* Return if the player lose */
    public boolean lose() {
        /* The heliport is flooded */
        if (cases[heliport[2]][heliport[3]].getState() == 2) {
            handler.setDeath(0);
            return true;
        }
        /* A player drowned */
        else if (drownedPlayer()) {
            handler.setDeath(1);
            return true;
        }
        /* All one type of non-gathered artifact's temple drowned */
        else if (drownedArtifact()) {
            handler.setDeath(2);
            return true;
        }
        /* The water level is too high */
        else if (floodGauge == 9) {
            handler.setDeath(3);
            return true;
        } else return false;
    }

    /* Return if a player drowned */
    private boolean drownedPlayer() {
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

    /* Return if all one type of non-gathered artifact's temple drowned */
    private boolean drownedArtifact() {
        for (int i = 0; i < casesWithArtifacts.length; i++) {
            if (!cases[casesWithArtifacts[i][0]][casesWithArtifacts[i][1]].isVisible && !cases[casesWithArtifacts[i][2]][casesWithArtifacts[i][3]].isVisible && !artifactsGathered[i]) {
                handler.setArtifact(i);
                return true;
            }
        }
        return false;
    }

    /* Move a player to an other random case */ //todo make it random
    private void moveToOtherCase(Player p, int x, int y) {
        if (y + 1 < cases.length && cases[x][y + 1].getState() != 2) movePlayer(cases[x][y + 1], p); //bas
        else if (y - 1 >= 0 && cases[x][y - 1].getState() != 2) movePlayer(cases[x][y - 1], p); //haut
        else if (x + 1 < cases.length && cases[x + 1][y].getState() != 2) movePlayer(cases[x + 1][y], p); //gauche
        else if (x - 1 >= 0 && cases[x - 1][y].getState() != 2) movePlayer(cases[x - 1][y], p); //droite
        else System.exit(-1);
    }

    /* Thirst the selected case */
    public void thirstCase(Case clickedCase, boolean nearby) {
        for (Case[] aCase : cases) {
            for (int j = 0; j < cases.length; j++) {
                if (aCase[j] == clickedCase && aCase[j].getState() == 1) aCase[j].setState(0);
            }
        }
        if (!nearby) discard(5);
        else player.get(isPlaying).addAction();
    }

    /* Move a player to the selected case */
    public void movePlayer(Case clickedCase, boolean nearby) {
        if (!nearby) {
            int i = (player.get(isPlaying).position[0] - xOffset) / (handler.getSpacing() + handler.getPixelByCase());
            int j = (player.get(isPlaying).position[1] - yOffset) / (handler.getSpacing() + handler.getPixelByCase());
            playerSelectionMenu.setClickedCase(clickedCase);
            playerSelectionMenu.setPlayersOnCase(playersOnTheCase(new Case(handler, i, j, xOffset, yOffset), false));
            playerSelectionMenu.setActive(true);
        } else {
            player.get(isPlaying).position = new int[]{clickedCase.x, clickedCase.y};
            player.get(isPlaying).position[0] += Assets.playerDim * alreadyOccupied(player.get(isPlaying));
            player.get(isPlaying).addAction();
        }
    }

    /* Move player to a selected case */
    private void movePlayer(Case c, Player p2) {
        if (!drownedPlayer()) {
            for (Player p : player) {
                if (p.equals(p2)) {
                    p.position = new int[]{c.x, c.y};
                    p.position[0] += Assets.playerDim * alreadyOccupied(p);
                }
            }
        }
    }

    /* Move multiple players to a selected case */
    public void moveMultiplePlayers(ArrayList<Player> playerToMove, Case c) {
        playerToMove.add(0, player.get(isPlaying));
        for (Player p : playerToMove) {
            movePlayer(c, p);
        }
        discard(4);
    }

    /* Return an offset if the case is already occupied by a player */
    private int alreadyOccupied(Player p) {
        int offset = 0, backup = 0;
        do {
            if (offset != backup) backup = offset;
            for (Player value : player) {
                if (!value.equals(p) && value.position[0] == p.position[0] + offset * Assets.playerDim && value.position[1] == p.position[1])
                    offset++;
            }
        } while (offset != backup);
        return offset;
    }

    /* Gather a type of artifact */
    public void gatherArtifact(int num) {
        artifactsGathered[num] = true;
        player.get(isPlaying).inventory[num] -= 4;
        player.get(isPlaying).addAction();
    }

    /* Return an array with all the players on a selected case */
    public ArrayList<Player> playersOnTheCase(Case c, boolean noPlayerPlaying) {
        ArrayList<Player> res = new ArrayList<>();
        for (Player p : player) {
            if (onCase(p, c) && (noPlayerPlaying || !p.equals(player.get(isPlaying)))) res.add(p);
        }
        return res;
    }

    /* Return if there is a player on a selected case */
    public boolean onCase(Player p, Case c) {
        return p.position[0] >= c.x && p.position[0] <= c.x + handler.getPixelByCase() && p.position[1] >= c.y && p.position[1] <= c.y + handler.getPixelByCase();
    }

    /* Trade an artifact with a player */
    public void trade(Player p, int artifactValue) {
        for (Player players : player) {
            if (players == p) players.addInventory(artifactValue);
            else if (players == player.get(isPlaying)) players.delInventory(artifactValue);
        }
    }

    /* Draw a card */
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

    /* Discard a card */
    public void discard(int i) {
        if (i < 4) treasureDeck.discard(new TreasureDeck.Card(Assets.keys[i], Utils.invValueToString(i)));
        else treasureDeck.discard(new TreasureDeck.Card(Assets.specialCards[i - 4], Utils.invValueToString(i)));
        player.get(isPlaying).delInventory(i);
    }


    /* Update & Render */

    public void update() {
        actionMenu.update();
        for (Case[] aCase : cases) {
            for (int j = 0; j < cases.length; j++) {
                aCase[j].update();
            }
        }
    }

    public void render(Graphics g) {
        /* Render the end of turn button */
        endOfTurnButton.render(g);
        /* Render the text */
        Text.drawString(g, "It's " + player.get(isPlaying).toString() + "'s turn.", handler.getWidth() / 2, 50, true, Color.WHITE, Assets.font45);
        /* Render the board */
        for (Case[] aCase : cases) {
            for (int j = 0; j < cases[0].length; j++) {
                aCase[j].render(g);
            }
        }
        /* Render the players */
        for (Player p : player) {
            p.render(g);
        }
        /* Render the gathered artifacts */
        artifactRender(g);
        /* Render the special inventory */
        player.get(isPlaying).renderSpecialInventory(g);
        /* Render the player's description */
        player.get(isPlaying).renderDescription(g);
        /* Render the deck */
        if (!treasureDeck.isEmpty())
            g.drawImage(Assets.cardsBack, handler.getWidth() - 3 * Assets.dim - 3 * handler.getSpacing(), handler.getHeight() - (Assets.dim + Assets.dim * 2 / 3 + handler.getSpacing() * 4), null);
        g.drawImage(treasureDeck.lastGraveCardSprite(), handler.getWidth() - 2 * Assets.dim - handler.getSpacing(), handler.getHeight() - (Assets.dim + Assets.dim * 2 / 3 + handler.getSpacing() * 4), Assets.dim, Assets.cardHeightDim, null);
        /* Render the action's text */
        Text.drawString(g, "Actions left :", handler.getWidth() * 2 / 3 + Assets.dim / 2, handler.getHeight() * 2 / 3 + Assets.dim * 2 + handler.getSpacing()*5, true, Color.WHITE, Assets.font45);
        Text.drawString(g, Integer.toString(3 - player.get(isPlaying).getAction()), handler.getWidth() * 2 / 3 + Assets.dim / 2, handler.getHeight() * 2 / 3 + Assets.dim * 2 + 50 + handler.getSpacing()*5, true, Color.WHITE, Assets.font45);
        /* Render the gauge */
        g.drawImage(Assets.gauge[floodGauge], handler.getWidth() - 2 * handler.getSpacing(), handler.getSpacing(), Assets.gauge[flood].getWidth(), handler.getHeight() - 30, null); //-> futur jauge
        /* Render the different menus if they are active */
        actionMenu.render(g);
        discardMenu.render(g);
        playerSelectionMenu.render(g);
    }

    /* Render the gathered artifacts */
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
        if (actionMenu.isActive()) actionMenu.onMouseClicked(e);
        else if (discardMenu.isActive()) discardMenu.onMouseClicked(e);
        else if (playerSelectionMenu.isActive()) playerSelectionMenu.onMouseClicked(e);
        else if (nearPlayer(e) || player.get(isPlaying).getSpecialInventory(0) > 0 || player.get(isPlaying).getSpecialInventory(1) > 0) {
            Case clickedCase = getClickedCase(e);
            if (clickedCase != null) {
                actionMenu.setNearby(nearPlayer(e));
                actionMenu.setX(e.getX());
                actionMenu.setY(e.getY());
                actionMenu.setClickedCase(clickedCase);
                actionMenu.setPlayer(player.get(isPlaying));
                actionMenu.setVisible(true);
            }
        }
        endOfTurnButton.onMouseClicked(e);
    }

    public boolean nearPlayer(MouseEvent e) {
        int i = (player.get(isPlaying).position[0] - xOffset) / (handler.getSpacing() + handler.getPixelByCase());
        int j = (player.get(isPlaying).position[1] - yOffset) / (handler.getSpacing() + handler.getPixelByCase());
        i = i * (handler.getSpacing() + handler.getPixelByCase()) + xOffset;
        j = j * (handler.getSpacing() + handler.getPixelByCase()) + yOffset;
        Rectangle horizontalPos = new Rectangle(i - handler.getPixelByCase() - handler.getSpacing(), j, 3 * handler.getPixelByCase() + 2 * handler.getSpacing(), handler.getPixelByCase());
        Rectangle verticalPos = new Rectangle(i, j - handler.getPixelByCase() - handler.getSpacing(), handler.getPixelByCase(), 3 * handler.getPixelByCase() + 2 * handler.getSpacing());
        return horizontalPos.contains(e.getX(), e.getY()) || verticalPos.contains(e.getX(), e.getY());
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        endOfTurnButton.onMousePressed(e);
        actionMenu.onMousePressed(e);
        playerSelectionMenu.onMousePressed(e);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        endOfTurnButton.onMouseReleased(e);
        actionMenu.onMouseReleased(e);
        playerSelectionMenu.onMouseReleased(e);
    }

    public void onMouseMove(MouseEvent e) {
        if (actionMenu.isActive()) actionMenu.onMouseMove(e);
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

    /* Return an array with they location of the starter cases for each colors */
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

    /* Get a random tile in the array */
    public int getBoardTile() {
        int number;
        do {
            number = Handler.r.nextInt(Assets.board.length);
        } while (handler.getTakenNumbers().contains(number));
        handler.addNumber(number);
        return number;
    }

    /* Get the clicked case */
    public Case getClickedCase(MouseEvent e) {
        int i = (e.getX() - xOffset) / (handler.getSpacing() + handler.getPixelByCase());
        int j = (e.getY() - yOffset) / (handler.getSpacing() + handler.getPixelByCase());
        if (i < cases.length && j < cases.length && i >= 0 && j >= 0 && cases[i][j].isVisible) return cases[i][j];
        else return null;
    }
}