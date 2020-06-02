package gameCommons.Menus;

import gameCommons.Board.Island;
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

public class TradesMenu implements Interacts {
    private Handler handler;
    private Island island;
    private boolean isActive = false;
    private ArrayList<Player> players;
    private Player selectedPlayer;
    private Player inventoryPlayer;
    private ArrayList<Rectangle> pawnsBounds = new ArrayList<>();
    private ArrayList<Rectangle> artifactsBounds = new ArrayList<>();
    private boolean toTrade;
    private int size;
    private Button returned;
    private ArrayList<Boolean> hovered = new ArrayList<>();

    public TradesMenu(Handler handler, Island island) {
        this.handler = handler;
        this.island = island;
        returned = new Button((float) (handler.getWidth() * 2 / 3), (float) (handler.getHeight() * 3 / 4), Assets.playerDim * 2, Assets.playerDim * 2, Assets.returned, () -> {
            this.selectedPlayer = null;
            this.isActive = false;
            island.menu.setVisible(false);
        });
    }

    /* Update & Render */

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        if (!isActive) return;
        g.drawImage(Assets.menuBg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        returned.render(g);
        if (!toTrade) selectPlayer(g);
        if (selectedPlayer != null) {
            int y = 0;
            Text.drawString(g, "Which key do you want to give to " + Utils.colorToString(selectedPlayer.color) + "?", handler.getWidth() / 2, handler.getHeight() / 4, true, Color.WHITE, Assets.font45);
            FontMetrics fm = g.getFontMetrics(Assets.font45);
            for (int i = 0; i < artifactsBounds.size(); i++) {
                String str = Integer.toString(inventoryPlayer.inventory[i]);
                if (inventoryPlayer.inventory[i] > 0) {
                    g.drawImage(Assets.keys[i], (handler.getWidth() - (size * (Assets.dim + handler.getSpacing() * 2)) + handler.getSpacing()) / 2 + y * (Assets.dim + (handler.getSpacing() * 2)),
                            (handler.getHeight() - Assets.dim) / 2, Assets.dim, Assets.cardHeightDim, null);
                    Text.drawString(g, str, (handler.getWidth() - (size * (Assets.dim + handler.getSpacing() * 2)) + handler.getSpacing()) / 2 + y * (Assets.dim + (handler.getSpacing() * 2)) + (Assets.dim - fm.stringWidth(str)) / 2,
                            (handler.getHeight() - Assets.dim) / 2 + Assets.cardHeightDim, false, Color.WHITE, Assets.font45);
                    y++;
                }
            }
        }
    }

    private void selectPlayer(Graphics g) {
        Text.drawString(g, "Which player do you want to trade with?", handler.getWidth() / 2, handler.getHeight() / 4, true, Color.WHITE, Assets.font45);
        for (int i = 0; i < pawnsBounds.size(); i++) {
            if (hovered.get(i))
                g.drawImage(Assets.halo[0], (handler.getWidth() - (players.size() * Assets.playerDim * 2) - handler.getSpacing()) / 2 + i * (Assets.playerDim * 2 + handler.getSpacing()) - handler.getSpacing(),
                        handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2, null);
            g.drawImage(Utils.colorToPawn(players.get(i).color), (handler.getWidth() - (players.size() * Assets.playerDim * 2) - handler.getSpacing()) / 2 + i * (Assets.playerDim * 2 + handler.getSpacing()) - handler.getSpacing(),
                    handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2, null);
        }
    }

    /* Mouse Manager */

    @Override
    public void onMouseClicked(MouseEvent e) {
        if (!isActive) return;
        returned.onMouseClicked(e);
        if (!toTrade) {
            for (int i = 0; i < pawnsBounds.size(); i++) {
                if (pawnsBounds.get(i).contains(e.getX(), e.getY())) {
                    selectedPlayer = players.get(i);
                    toTrade = true;
                }
            }
        } else {
            for (int i = 0; i < artifactsBounds.size(); i++) {
                if (artifactsBounds.get(i).contains(e.getX(), e.getY())) {
                    island.trade(selectedPlayer, i);
                    selectedPlayer = null;
                    isActive = false;
                    island.menu.setVisible(false);
                }
            }
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (!isActive) return;
        returned.onMousePressed(e);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (!isActive) return;
        returned.onMouseReleased(e);
    }

    @Override
    public void onMouseMove(MouseEvent e) {
        if (!isActive) return;
        returned.onMouseMove(e);
        for (int i = 0; i < pawnsBounds.size(); i++) {
            if (pawnsBounds.get(i).contains(e.getX(), e.getY())) {
                hovered.set(i, true);
            } else hovered.set(i, false);
        }
    }

    /* Getters & Setters */

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean b) {
        isActive = b;
    }

    public void setPlayers(ArrayList<Player> p) {
        pawnsBounds.clear();
        hovered.clear();
        players = p;
        if (p.size() > 1) {
            toTrade = false;
            for (int i = 0; i < players.size(); i++) {
                pawnsBounds.add(i, new Rectangle((handler.getWidth() - (players.size() * Assets.playerDim * 2) - handler.getSpacing()) / 2 + i * (Assets.playerDim * 2 + handler.getSpacing()) - handler.getSpacing(),
                        handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2));
                hovered.add(false);
            }
        } else {
            selectedPlayer = p.get(0);
            toTrade = true;
        }
    }

    public void setInventoryPlayer(Player p) {
        int y = 0;
        size = 0;
        inventoryPlayer = p;
        for (int i = 0; i < inventoryPlayer.inventory.length - 2; i++) { // -2 because we don't want the special inventory
            if (inventoryPlayer.inventory[i] > 0) size++;
        }
        artifactsBounds.clear();
        for (int i = 0; i < inventoryPlayer.inventory.length - 2; i++) {
            if (inventoryPlayer.inventory[i] > 0) {
                artifactsBounds.add(new Rectangle((handler.getWidth() - (size * (Assets.dim + handler.getSpacing() * 2)) + handler.getSpacing()) / 2 + y * (Assets.dim + (handler.getSpacing() * 2)),
                        handler.getHeight() / 2 - Assets.dim / 2, Assets.dim, Assets.cardHeightDim));
                y++;
            } else {
                artifactsBounds.add(new Rectangle(0, 0, 0, 0));
            }
        }
    }
}