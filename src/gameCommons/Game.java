package gameCommons;

import gameCommons.state.MenuState;
import gameCommons.state.State;
import ui.Observable;
import util.Handler;
import util.MouseManager;

import java.awt.*;

public class Game extends Observable implements Runnable {
    private MouseManager mouseManager;

    public Game(Handler handler) {
        handler.setGame(this);
        mouseManager = new MouseManager();
        State menuState = new MenuState(handler);
        State.setState(menuState);
    }

    /* Render method */
    public void render(Graphics g) {
        if (State.getState() != null) {
            State.getState().render(g);
        }
    }

    /* Game loop */
    @Override
    public void run() {
        if (State.getState() != null) {
            State.getState().update();
        }
        updateObservers();
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }
}