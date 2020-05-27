package gameCommons;

import gfx.Assets;
import gfx.Text;
import ui.UiInteracter;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TradesMenu implements UiInteracter {
    private Handler handler;
    private Island island;
    private boolean isActive = false;
    private ArrayList<Player> players;
    private Player selectedPlayer;
    private ArrayList<Rectangle> pawnsBounds = new ArrayList<>();
    private ArrayList<Rectangle> artifactsBounds = new ArrayList<>();
    private boolean toTrade;

    public TradesMenu(Handler handler, Island island) {
        this.handler = handler;
        this.island = island;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        if (!isActive)
            return;
        g.drawImage(Assets.menuBg, 0, 0, handler.getWidth(), handler.getHeight(), null);

        if (!toTrade) selectPlayer(g);

        if (selectedPlayer != null) {
            Text.drawString(g, "Which key do you want to give to " + Utils.colorToString(selectedPlayer.color) + "?", handler.getWidth() / 2, handler.getHeight() / 4, true, Color.WHITE, Assets.font45);
            for (int i = 0; i < artifactsBounds.size(); i++) {
                g.drawImage(Assets.keys[i], handler.getWidth() / 2 - Assets.keys.length * Assets.dim / 2 + i * Assets.dim + i * handler.getSpacing() * 2 - Assets.keys.length / 2 * handler.getSpacing() * 2,
                        handler.getHeight() / 2 - Assets.dim / 2, Assets.dim, Assets.dim + Assets.dim * 2 / 3, null);
            }
        }
    }

    private void selectPlayer(Graphics g) {
        Text.drawString(g, "Which player do you want to trade with?", handler.getWidth() / 2, handler.getHeight() / 4, true, Color.WHITE, Assets.font45);
        for (int i = 0; i < pawnsBounds.size(); i++) {
            g.drawImage(Utils.colorToPawn(players.get(i).color), handler.getWidth() / 2 - players.size() * Assets.playerDim + i * Assets.playerDim * 2 + i * handler.getSpacing() - players.size() / 2 * handler.getSpacing(),
                    handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2, null);
        }
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        if (!toTrade) {
            for (int i = 0; i < pawnsBounds.size(); i++) {
                if (pawnsBounds.get(i).contains(e.getX(), e.getY())) {
                    selectedPlayer = players.get(i);
                    toTrade = true;
                    artifactsBounds.clear();
                    for (int y = 0; y < selectedPlayer.inventory.length; y++) {
                        artifactsBounds.add(new Rectangle(handler.getWidth() / 2 - Assets.keys.length * Assets.dim / 2 + y * Assets.dim + y * handler.getSpacing() * 2 - Assets.keys.length / 2 * handler.getSpacing() * 2,
                                handler.getHeight() / 2 - Assets.dim / 2, Assets.dim, Assets.dim));
                    }
                }
            }
        } else {
            for (int i = 0; i < artifactsBounds.size(); i++) {
                if (artifactsBounds.get(i).contains(e.getX(), e.getY())) {
                    island.trade(selectedPlayer, i);
                    selectedPlayer = null;
                    this.isActive = false;
                    //island.menu.setActive(false);
                }
            }
        }
    }

    @Override
    public void onMouseMove(MouseEvent e) {

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean b) {
        isActive = b;
    }

    public void setPlayers(ArrayList<Player> p) {
        pawnsBounds.clear();
        players = p;
        if (p.size() > 1) {
            toTrade = false;
            for (int i = 0; i < players.size(); i++) {
                pawnsBounds.add(i, new Rectangle(handler.getWidth() / 2 - players.size() * Assets.playerDim + i * Assets.playerDim * 2 + i * handler.getSpacing() - players.size() / 2 * handler.getSpacing(),
                        handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2));
            }
        } else {
            selectedPlayer = p.get(0);
            toTrade = true;
        }
    }
}
