package game.state;

import ui.ObjectManager;
import util.Handler;

import java.awt.*;

public abstract class State {
    /* --------------- STATIC --------------- */

    private static State currentState = null;
    protected ObjectManager manager;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }

    /* --------------- CLASS --------------- */

    protected Handler handler;

    State(Handler handler) {
        this.handler = handler;
    }

    /* Update & Render */

    public abstract void update();

    public abstract void render(Graphics g);
}