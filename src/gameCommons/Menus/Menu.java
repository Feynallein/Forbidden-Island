package gameCommons.Menus;

import gameCommons.Board.Case;
import gameCommons.Board.Island;
import gameCommons.Player;
import gfx.Assets;
import gfx.Text;
import ui.Interacts;
import util.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

//TODO: faire qu'on ne voit pas l'ancien menu quand on affiche le nouveau

public class Menu implements Interacts {
    private boolean isVisible, onCase, nearby;
    private int x, y, textBackgroundWidth, textBackgroundHeight;
    private Case clickedCase;
    private Player player;
    private Island island;
    private TradesMenu tradesMenu;
    private HashMap<String, Rectangle> bounds = new HashMap<>();
    private ArrayList<String> texts = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    public Menu(Handler handler, Island island) {
        this.island = island;
        this.tradesMenu = new TradesMenu(handler, island);
        this.isVisible = false;
        this.textBackgroundWidth = Assets.dim - handler.getSpacing();
        this.textBackgroundHeight = Assets.playerDim;
    }

    /* Update & Render */

    @Override
    public void update() {
        if (!isVisible) return;
        setRectangleAndTexts();
        players = island.playersOnTheCase(clickedCase, false);
        onCase = island.onCase(player, clickedCase);
    }

    @Override
    public void render(Graphics g) {
        if (!isVisible) return;
        FontMetrics fm = g.getFontMetrics(Assets.font20);
        for (int i = 0; i < texts.size(); i++) {
            g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textBackgroundHeight, textBackgroundWidth, textBackgroundHeight, null);
            Text.drawString(g, texts.get(i), x + 1, y + 1 + fm.getHeight() / 2 + fm.getAscent() + i * textBackgroundHeight, false, Color.WHITE, Assets.font20);
        }
        tradesMenu.render(g);
    }


    /* Mouse Manager */

    @Override
    public void onMouseClicked(MouseEvent e) {
        if (!isVisible) return;
        if (tradesMenu.isActive()) tradesMenu.onMouseClicked(e);
        else if (e.getX() < x || e.getX() > x + textBackgroundWidth || e.getY() < y || e.getY() > y + textBackgroundHeight * bounds.size()) {
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
                if (clickedCase.isArtifact && player.inventory[clickedCase.artifactValue] >= 1) { //CHANGE 1 TO 4
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

    @Override
    public void onMousePressed(MouseEvent e) {
        if (!isVisible) return;
        tradesMenu.onMousePressed(e);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (!isVisible) return;
        tradesMenu.onMouseReleased(e);
    }

    public void onMouseMove(MouseEvent e) {
        if (!isVisible) return;
        tradesMenu.onMouseMove(e);
    }


    /* GETTERS & SETTERS */

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setClickedCase(Case c) {
        clickedCase = c;
    }

    public void setPlayer(Player p) {
        player = p;
    }

    public void setVisible(boolean b) {
        isVisible = b;
    }

    public boolean isActive() {
        return isVisible;
    }

    public void setNearby(boolean b) {
        nearby = b;
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
            bounds.put(texts.get(i), new Rectangle(x + 1, y + i * textBackgroundHeight, textBackgroundWidth, textBackgroundHeight));
        }
    }
}
