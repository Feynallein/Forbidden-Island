package gameCommons.Board;

import gameCommons.Menus.Menu;
import gameCommons.Player;
import gfx.Assets;
import gfx.Text;
import ui.UiInteracter;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Island implements UiInteracter {
    private Handler handler;
    public Case[][] cases;
    public int xOffset, yOffset;
    public Player[] player;
    public static Random r = new Random();
    private Color[] colors;
    private int isPlaying = 0;
    private boolean[] artifactsGathered;
    private int[] heliport = new int[4];
    private int[][] casesWithArtifacts = new int[4][4];
    public gameCommons.Menus.Menu menu;
    public Deck deck;

    public Island(Handler handler, int length, int numberOfPlayers) {
        this.handler = handler;
        if (length * handler.getPixelByCase() < handler.getWidth() && length * handler.getPixelByCase() < handler.getHeight()) {
            cases = new Case[length][length];
            xOffset = (handler.getWidth() - length * handler.getPixelByCase()) / 2 - (handler.getSpacing() * length) / 2;
            yOffset = (handler.getHeight() - length * handler.getPixelByCase()) / 2 - (handler.getSpacing() * length) / 2;
        } else {
            System.out.println("Too wide grid");
            System.exit(2);
        }
        this.player = new Player[numberOfPlayers];
        colors = new Color[]{Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.WHITE};
        this.artifactsGathered = new boolean[4];
        Arrays.fill(artifactsGathered, false);
        init();
        menu = new Menu(handler, this);
        deck = new Deck(handler);
    }

    private void init() {
        int offset;
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {
                cases[i][j] = new Case(handler, i, j, xOffset, yOffset, this);
                if (cases[i][j].isArtifact) {
                    offset = casesWithArtifacts[cases[i][j].artiValue][0] != 0 ? 2 : 0;
                    casesWithArtifacts[cases[i][j].artiValue][offset] = i;
                    casesWithArtifacts[cases[i][j].artiValue][1 + offset] = j;
                }
            }
        }
        for (int i = 0; i < player.length; i++) {
            player[i] = new Player(handler, this, colors[i], i);
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
        } else return false;
    }

    public boolean blockedPlayer() {
        Case[] c = new Case[4];
        int[] verify = new int[4];
        for (Player p : player) {
            for (int i = -1; i < 2; i += 2) {
                c[i + 1] = getClickedCase(new MouseEvent(new Button(), 0, 0, 0, p.position[0] + i * (handler.getPixelByCase() + handler.getSpacing()), p.position[1], 0, false));
                c[i + 2] = getClickedCase(new MouseEvent(new Button(), 0, 0, 0, p.position[0], p.position[1] + i * (handler.getPixelByCase() + handler.getSpacing()), 0, false));
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

    public void endOfTurn() { //version ou ca ne prend pas les zones deja inondees
        int i = 0;
        do {
            int fstRandom = r.nextInt(cases.length);
            int sndRandom = r.nextInt(cases.length);
            if (cases[fstRandom][sndRandom].getState() == 0) {
                cases[fstRandom][sndRandom].setState(1);
                i++;
            } else if (cases[fstRandom][sndRandom].getState() == 1) {
                cases[fstRandom][sndRandom].setState(2);
                i++;
            }
        } while (i < 3);
        for (Player p : player) {
            p.resetAction();
        }
        isPlaying++;
        if (isPlaying == player.length) isPlaying = 0;
    }

    public void thirstCase(Case clickedCase, boolean nearby) {
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases.length; j++) {
                if (cases[i][j] == clickedCase && cases[i][j].getState() == 1) cases[i][j].setState(0);
            }
        }
        //player[isPlaying].addAction();
        if(!nearby) player[isPlaying].specialInventory[1]--;
    }

    public void movePlayer(Case clickedCase, boolean nearby) {
        player[isPlaying].position = new int[]{clickedCase.x, clickedCase.y};
        int[] offsets = alreadyOccupied();
        player[isPlaying].position[0] += Assets.playerDim * offsets[0];
        player[isPlaying].position[1] += Assets.playerDim * offsets[1];
        //player[isPlaying].addAction();
        if(!nearby) player[isPlaying].specialInventory[0]--; //TO DO : take other's player with him
    }

    public int[] alreadyOccupied() {
        int offset = 0, backup = 0, yOffset = 0;
        do {
            if (offset != backup) backup = offset;
            if (offset >= 4) {
                offset = offset % 4;
                yOffset++;
            }
            for (int i = 0; i < player.length; i++) {
                if (i != isPlaying && player[i].position[0] == player[isPlaying].position[0] + offset * Assets.playerDim &&
                        player[i].position[1] == player[isPlaying].position[1] + yOffset * Assets.playerDim) offset++;
            }
        } while (offset != backup);
        return new int[]{offset, yOffset};
    }

    public void gatherArtifact(int num) {
        this.artifactsGathered[num] = true;
        this.player[isPlaying].inventory[num] -= 1; //a changer a 4
        //this.player[isPlaying].addAction();
    }

    public void floodCase(Case clickedCase) {
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases.length; j++) {
                if (cases[i][j] == clickedCase && cases[i][j].getState() == 0) cases[i][j].setState(1);
                else if (cases[i][j] == clickedCase && cases[i][j].getState() == 1) cases[i][j].setState(2);
            }
        }
        //player[isPlaying].addAction();
    }

    public void addInventory(int num) {
        player[isPlaying].addInventory(num);
        //player[isPlaying].addAction();
    }

    public ArrayList<Player> playersOnTheCase(Case c) {
        ArrayList<Player> res = new ArrayList<>();
        for (Player p : player) {
            if (onCase(p, c) && !p.equals(player[isPlaying])) res.add(p);
        }
        return res;
    }

    public boolean onCase(Player p, Case c) {
        return p.position[0] >= c.x && p.position[0] <= c.x + handler.getPixelByCase() && p.position[1] >= c.y && p.position[1] <= c.y + handler.getPixelByCase();
    }

    public void trade(Player p, int artifactValue) {
        for (Player players : player) {
            if (players == p) players.addInventory(artifactValue);
            else if (players == player[isPlaying]) players.delInventory(artifactValue);
        }
    }

    public void draw(){
        if(player[isPlaying].inventorySize() < 5) {
            String effect = deck.drawCard();
            if (effect.equals("flooded")) {
                //to do
            } else if (effect.equals("helicopter")) {
                player[isPlaying].addSpecialInventory(0);
            } else if (effect.equals("sandbag")) {
                player[isPlaying].addSpecialInventory(1);
            } else {
                addInventory(Utils.artifactToValue(Integer.parseInt(effect)));
            }
        }
        else {
            //to do -> défausse d'une carte
        }
    }


    /* UPDATE & RENDER */

    public void update() {
        menu.update();
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases.length; j++) {
                cases[i][j].update();
            }
        }
    }

    public void render(Graphics g) {
        Text.drawString(g, "It's " + player[isPlaying].toString() + "'s turn.", handler.getWidth() / 2, 50, true, Color.WHITE, Assets.font45);
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {
                cases[i][j].render(g);
            }
        }
        for (Player p : player) {
            p.render(g);
        }
        artifactRender(g);


        //temporary
        g.drawImage(Assets.temp, handler.getWidth() - 5 * 96 - 32, 15, null); //->recap du joueur, a passer dans la classe player comme le specialInv


        player[isPlaying].renderSpecialInventory(g);

        if(!deck.isEmpty()) g.drawImage(Assets.cardsBack, handler.getWidth() - 3*Assets.dim - 3*handler.getSpacing(), handler.getHeight() - (Assets.dim+Assets.dim*2/3 + handler.getSpacing()*4), null);
        g.drawImage(deck.lastGraveCardSprite(), handler.getWidth() - 2*Assets.dim - handler.getSpacing(), handler.getHeight() - (Assets.dim+Assets.dim*2/3 + handler.getSpacing()*4), null);

        //g.drawRect(handler.getWidth() - 20, 15, 15, handler.getHeight() - 30); //-> futur jauge

        menu.render(g);
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


    /* MOUSE MANAGER */
    public void onMouseClicked(MouseEvent e) {
        if(menu.isActive()) menu.onMouseClicked(e);
        else if (player[isPlaying].nearPlayer(e) || player[isPlaying].specialInventory[0] != 0 || player[isPlaying].specialInventory[1] != 0) {
            Case clickedCase = getClickedCase(e);
            if (clickedCase != null) {
                if(!player[isPlaying].nearPlayer(e)) this.menu.setNearby(false);
                else this.menu.setNearby(true);
                this.menu.setX(e.getX());
                this.menu.setY(e.getY());
                this.menu.setClickedCase(clickedCase);
                this.menu.setPlayer(player[isPlaying]);
                this.menu.setVisible(true);
            }
        }
    }

    public void onMouseMove(MouseEvent e) {
        if(menu.isVisible) menu.onMouseMove(e);
        else {
            for (int i = 0; i < cases.length; i++) {
                for (int j = 0; j < cases.length; j++) {
                    cases[i][j].onMouseMove(e);
                }
            }
        }
    }



    /* GETTERS & SETTER */

    public int[] getStarterCases(Color color) {
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases.length; j++) {
                if (cases[i][j].color == Color.GREEN) {
                    heliport = new int[]{i * handler.getSpacing() + i * handler.getPixelByCase() + xOffset,
                            j * handler.getSpacing() + j * handler.getPixelByCase() + yOffset, i, j};
                }
                if (cases[i][j].color == color)
                    /*return new int[]{i * handler.getSpacing() + i * handler.getPixelByCase() + xOffset,
                            j * handler.getSpacing() + j * handler.getPixelByCase() + yOffset};*/
                    return heliport;
            }
        }
        return null;
    }

    public int getBoardTile() {
        int number;
        do {
            number = r.nextInt(Assets.board.length);
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
