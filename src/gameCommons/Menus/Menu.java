package gameCommons.Menus;

import gameCommons.Board.Case;
import gameCommons.Board.Island;
import gameCommons.Player;
import gfx.Assets;
import gfx.Text;
import ui.UiInteracter;
import util.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Menu implements UiInteracter {
    private Handler handler;
    public boolean isVisible = false;
    private int x, y; //precise location where we clicked
    private Case clickedCase;
    private Player player;
    private Island island;
    private int textWidth = 80, textHeight = 30; //textHeight = height of the text block, textWidth = width of the text block (not the text itself)
    public TradesMenu tradesMenu;
    HashMap<String, Rectangle> bounds = new HashMap<>();
    ArrayList<String> texts = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private boolean onCase;
    private boolean nearby;

    public Menu(Handler handler, Island island) {
        this.handler = handler;
        this.island = island;
        this.tradesMenu = new TradesMenu(handler, island);
    }

    /* UPDATE & RENDER */

    public void update() {
        if (!isVisible)
            return;

        setRectangleAndTexts();
        players = island.playersOnTheCase(clickedCase);
        onCase = island.onCase(player, clickedCase);
    }

    public void render(Graphics g) {
        if (!isVisible)
            return;
        FontMetrics fm = g.getFontMetrics(Assets.font20);
        for (int i = 0; i < texts.size(); i++) {
            g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textHeight, textWidth, textHeight, null);
            Text.drawString(g, texts.get(i), x + 1, y + 1 + fm.getHeight() / 2 + fm.getAscent() + i * textHeight, false, Color.WHITE, Assets.font20); //a changer le 25
        }
        tradesMenu.render(g);
    }


    /* MOUSE MANAGER */

    public void onMouseClicked(MouseEvent e) {
        if (!isVisible)
            return;

        if (tradesMenu.isActive()) tradesMenu.onMouseClicked(e);
        else if (e.getX() < x || e.getX() > x + textWidth || e.getY() < y || e.getY() > y + textHeight * bounds.size()) {
            isVisible = false;
        } else {
            if (bounds.get("Move") != null && bounds.get("Move").contains(e.getX(), e.getY())) {
                island.movePlayer(clickedCase, nearby);
                isVisible = false;
            } else if (bounds.get("Thirst") != null && bounds.get("Thirst").contains(e.getX(), e.getY())) {
                island.thirstCase(clickedCase, nearby);
                isVisible = false;
            } else if (bounds.get("Dig") != null && bounds.get("Dig").contains(e.getX(), e.getY())) {
                island.draw();
                isVisible = false;
            } else if (bounds.get("Gather") != null && bounds.get("Gather").contains(e.getX(), e.getY())) {
                if (clickedCase.isArtifact && player.inventory[clickedCase.artifactValue] >= 1) { //change 1 to 4
                    island.gatherArtifact(clickedCase.artifactValue);
                    isVisible = false;
                }
            } else if ((bounds.get("Trade") != null && bounds.get("Trade").contains(e.getX(), e.getY()))) {
                tradesMenu.setPlayers(players);
                tradesMenu.setInventoryPlayer(player);
                tradesMenu.setActive(true);
            }
        }
    }

    public void onMouseMove(MouseEvent e) {

    }


    /* GETTERS & SETTERS */

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setClickedCase(Case clickedCase) {
        this.clickedCase = clickedCase;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setVisible(boolean b) {
        this.isVisible = b;
    }

    public boolean isActive() {
        return this.isVisible;
    }

    public void setNearby(boolean b) {
        this.nearby = b;
    }

    public void setRectangleAndTexts() {
        bounds.clear();
        texts.clear();
        if (!onCase && clickedCase.getState() == 0 && player.getAction() < 3 && (nearby || player.getSpecialInventory(0) != 0)) {
            texts.add("Move");
        }
        if (clickedCase.getState() == 1 && player.getAction() < 3 && (nearby || player.getSpecialInventory(1) != 0)) {
            texts.add("Thirst");
        }
        if (onCase && clickedCase.getState() != 2 && player.getAction() < 3 && nearby) {
            if (clickedCase.isArtifact) {
                texts.add("Gather");
            } else {
                texts.add("Dig");
            }
        }
        if (onCase && !players.isEmpty() && player.getAction() < 3 && nearby) {
            texts.add("Trade");
        }
        if (texts.size() == 0) isVisible = false;
        for (int i = 0; i < texts.size(); i++) {
            bounds.put(texts.get(i), new Rectangle(x + 1, y + i * textHeight, textWidth, textHeight));
        }
    }
}
