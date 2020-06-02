package game.menus;

import game.Player;
import game.board.Island;
import gfx.Assets;
import gfx.Text;
import ui.Interacts;
import util.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DiscardMenu implements Interacts {
    private Handler handler;
    private Player player;
    private int size;
    private ArrayList<Rectangle> bounds = new ArrayList<>();
    private boolean isActive;
    private Island island;

    public DiscardMenu(Handler handler, Island island) {
        this.handler = handler;
        this.island = island;
        isActive = false;
    }

    /* Update & Render */

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        if (!isActive) return;

        g.drawImage(Assets.menuBg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        int y = 0;
        Text.drawString(g, "Which card do you want to throw?", handler.getWidth() / 2, handler.getHeight() / 4, true, Color.WHITE, Assets.font45);
        FontMetrics fm = g.getFontMetrics(Assets.font45);
        for (int i = 0; i < bounds.size(); i++) {
            String str;
            if (player.inventory[i] > 0) {
                if (i < 4) {
                    str = Integer.toString(player.inventory[i]);
                    g.drawImage(Assets.keys[i], (handler.getWidth() - (size * (Assets.dim + handler.getSpacing() * 2)) + handler.getSpacing()) / 2 + y * (Assets.dim + (handler.getSpacing() * 2)),
                            (handler.getHeight() - Assets.dim) / 2, Assets.dim, Assets.cardHeightDim, null);
                } else {
                    str = Integer.toString(player.inventory[i]);
                    g.drawImage(Assets.specialCards[i - 4], (handler.getWidth() - (size * (Assets.dim + handler.getSpacing() * 2)) + handler.getSpacing()) / 2 + y * (Assets.dim + (handler.getSpacing() * 2)),
                            (handler.getHeight() - Assets.dim) / 2, Assets.dim, Assets.cardHeightDim, null);
                }
                Text.drawString(g, str, (handler.getWidth() - (size * (Assets.dim + handler.getSpacing() * 2)) + handler.getSpacing()) / 2 + y * (Assets.dim + (handler.getSpacing() * 2)) + (Assets.dim - fm.stringWidth(str)) / 2,
                        (handler.getHeight() - Assets.dim) / 2 + Assets.cardHeightDim, false, Color.WHITE, Assets.font45);
                y++;
            }
        }
    }

    /* Mouse Manager */

    @Override
    public void onMouseMove(MouseEvent e) {

    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        for (int i = 0; i < bounds.size(); i++) {
            if (bounds.get(i).contains(e.getX(), e.getY())) {
                island.discard(i);
                this.isActive = false;
            }
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {

    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    /* Getters & Setters */

    public void setActive(boolean b) {
        isActive = b;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setPlayer(Player p) {
        player = p;
        int y = 0;
        bounds.clear();
        size = 0;
        for (int i : player.inventory) {
            if (i > 0) size++;
        }
        for (int i = 0; i < player.inventory.length; i++) {
            if (player.inventory[i] > 0) {
                bounds.add(new Rectangle((handler.getWidth() - (size * (Assets.dim + handler.getSpacing() * 2)) + handler.getSpacing()) / 2 + y * (Assets.dim + (handler.getSpacing() * 2)),
                        handler.getHeight() / 2 - Assets.dim / 2, Assets.dim, Assets.cardHeightDim));
                y++;
            } else {
                bounds.add(new Rectangle(0, 0, 0, 0));
            }
        }
    }
}
