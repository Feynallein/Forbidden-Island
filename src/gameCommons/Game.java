package gameCommons;

import gameCommons.state.PlayerSelectionState;
import gameCommons.state.State;
import gfx.Assets;
import ui.Observable;
import util.Handler;
import util.MouseManager;

import java.awt.*;

public class Game extends Observable implements Runnable {
    public State menuState;
    public MouseManager mouseManager;
    private int width;
    private int height;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        Handler handler = new Handler(this);
        Assets.init();
        mouseManager = new MouseManager();
        menuState = new PlayerSelectionState(handler);
        State.setState(menuState);
    }

    /* Update & Render */

    public void render(Graphics g) {
        if (State.getState() != null) {
            State.getState().render(g);
        }
    }

    @Override
    public void run() {
        if (State.getState() != null) {
            State.getState().update();
        }
        updateObservers();
    }


    /* GETTERS AND SETTERS */

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public int getLength() {
        return 6;
    }
}
