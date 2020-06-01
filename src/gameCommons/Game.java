package gameCommons;

import gameCommons.state.GameState;
import gameCommons.state.State;
import gfx.Assets;
import ui.Observable;
import util.Handler;
import util.MouseManager;

import java.awt.*;

public class Game extends Observable implements Runnable {
    //public State menuState;
    public State gameState; //temporary (because we don't want to go to the menu state first
    public MouseManager mouseManager;
    private int width;
    private int height;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        Handler handler = new Handler(this);
        Assets.init();
        mouseManager = new MouseManager();
        gameState = new GameState(handler);
        State.setState(gameState);

    }

    /* Update & Render */

    public void render(Graphics g) { //called by GamePanel to repaint
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
