package gameCommons;

import gfx.Assets;
import state.GameState;
import state.State;
import ui.Observable;
import util.Handler;
import util.KeyManager;
import util.MouseManager;

import java.awt.*;

public class Game extends Observable implements Runnable {
    /* states */
    //public State menuState;
    public State gameState;
    public State loseState;
    public State winState;

    /* managers */
    public MouseManager mouseManager;
    public KeyManager keyManager;

    /* window size */
    private int width;
    private int height;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        Handler handler = new Handler(this);
        Assets.init();
        mouseManager = new MouseManager();
        keyManager = new KeyManager();
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
        keyManager.update();
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

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public int getLength() {
        return 6;
    }
}
