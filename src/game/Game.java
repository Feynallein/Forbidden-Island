package game;

import game.state.MenuState;
import game.state.State;
import gfx.Assets;
import ui.Observable;
import util.Handler;
import util.MouseManager;

import java.awt.*;

public class Game extends Observable implements Runnable {
    private MouseManager mouseManager;
    private Handler handler;

    public Game(Handler handler) {
        handler.setGame(this);
        this.handler = handler;
        /* Loading assets */
        Assets.init(handler);
        /* Creating the mouse manager */
        mouseManager = new MouseManager();
        /* Creating the first state (= the menu state) */
        State menuState = new MenuState(handler);
        /* Setting the State */
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
        if (handler.getSettings().getProperty("music").equals("off") && handler.getMusicPlayer().isPlaying())
            handler.getMusicPlayer().stop();
        else if (handler.getSettings().getProperty("music").equals("on") && !handler.getMusicPlayer().isPlaying())
            handler.getMusicPlayer().play();
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }
}