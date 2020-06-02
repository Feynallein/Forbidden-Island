package game.menus;

import game.Player;
import game.board.Case;
import game.board.Island;
import gfx.Assets;
import gfx.Text;
import ui.Button;
import ui.Interacts;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayerSelectionMenu implements Interacts {
    private Handler handler;
    private ArrayList<Player> playersOnCase = new ArrayList<>();
    private ArrayList<Rectangle> pawnsBounds = new ArrayList<>();
    private boolean active;
    private Button go, returned;
    private ArrayList<Boolean> hovered = new ArrayList<>();
    private ArrayList<Boolean> selected = new ArrayList<>();
    private ArrayList<Player> selectedPlayers = new ArrayList<>();
    private Case clickedCase;

    public PlayerSelectionMenu(Handler handler, Island island) {
        this.handler = handler;
        this.go = new Button((float) handler.getWidth() / 2, (float) handler.getHeight() / 2 + 100, Assets.playerDim * 2, Assets.playerDim * 2, Assets.go, () -> {
            for (int i = 0; i < selected.size(); i++) {
                if (selected.get(i)) selectedPlayers.add(playersOnCase.get(i));
            }
            island.moveMultiplePlayers(selectedPlayers, clickedCase);
            selectedPlayers.clear();
            clickedCase = null;
            this.active = false;
        });
        this.returned = new Button((float) (handler.getWidth() * 2 / 3), (float) (handler.getHeight() * 3 / 4), Assets.playerDim * 2, Assets.playerDim * 2, Assets.returned, () -> {
            selectedPlayers.clear();
            clickedCase = null;
            this.active = false;
        });
    }

    /* Update & Render */

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        if (!active) return;
        if (pawnsBounds.isEmpty()) active = false;
        g.drawImage(Assets.menuBg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        Text.drawString(g, "Which player do you want to fly with?", handler.getWidth() / 2, handler.getHeight() / 4, true, Color.WHITE, Assets.font45);
        for (int i = 0; i < pawnsBounds.size(); i++) {
            if (selected.get(i))
                g.drawImage(Assets.halo[1], (handler.getWidth() - (playersOnCase.size() * Assets.playerDim * 2) - handler.getSpacing()) / 2 + i * (Assets.playerDim * 2 + handler.getSpacing()) - handler.getSpacing(),
                        handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2, null);
            else if (hovered.get(i)) {
                g.drawImage(Assets.halo[0], (handler.getWidth() - (playersOnCase.size() * Assets.playerDim * 2) - handler.getSpacing()) / 2 + i * (Assets.playerDim * 2 + handler.getSpacing()) - handler.getSpacing(),
                        handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2, null);
            }
            g.drawImage(Utils.colorToPawn(playersOnCase.get(i).color), (handler.getWidth() - (playersOnCase.size() * Assets.playerDim * 2) - handler.getSpacing()) / 2 + i * (Assets.playerDim * 2 + handler.getSpacing()) - handler.getSpacing(),
                    handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2, null);
        }
        go.render(g);
        returned.render(g);
    }

    /* Mouse Manager */

    @Override
    public void onMouseMove(MouseEvent e) {
        if (!active) return;
        for (int i = 0; i < pawnsBounds.size(); i++) {
            if (pawnsBounds.get(i).contains(e.getX(), e.getY())) {
                hovered.set(i, true);
            } else hovered.set(i, false);
        }
        go.onMouseMove(e);
        returned.onMouseMove(e);
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        if (!active) return;
        go.onMouseClicked(e);
        returned.onMouseClicked(e);
        for (int i = 0; i < pawnsBounds.size(); i++) {
            if (pawnsBounds.get(i).contains(e.getX(), e.getY())) selected.set(i, !selected.get(i));
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (!active) return;
        go.onMousePressed(e);
        returned.onMousePressed(e);
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if (!active) return;
        go.onMouseReleased(e);
        returned.onMouseReleased(e);
    }

    /* Getters & Setters */

    public void setActive(boolean b) {
        active = b;
    }

    public boolean isActive() {
        return active;
    }

    public void setClickedCase(Case c) {
        clickedCase = c;
    }

    public void setPlayersOnCase(ArrayList<Player> p) {
        pawnsBounds.clear();
        selected.clear();
        hovered.clear();
        playersOnCase = p;
        for (int i = 0; i < playersOnCase.size(); i++) {
            pawnsBounds.add(i, new Rectangle((handler.getWidth() - (playersOnCase.size() * Assets.playerDim * 2) - handler.getSpacing()) / 2 + i * (Assets.playerDim * 2 + handler.getSpacing()) - handler.getSpacing(),
                    handler.getHeight() / 2 - Assets.playerDim, Assets.playerDim * 2, Assets.playerDim * 2));
            hovered.add(false);
            selected.add(false);
        }
        if (playersOnCase.isEmpty()) {
            go.onClick();
        }
    }
}