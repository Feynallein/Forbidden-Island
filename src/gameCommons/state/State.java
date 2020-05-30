package gameCommons.state;

import ui.UiManager;
import util.Handler;

import java.awt.*;

public abstract class State {
    private static State currentState = null;
    protected UiManager manager;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }

    protected Handler handler;

    State(Handler handler) {
        this.handler = handler;
    }

    public abstract void update();

    public abstract void render(Graphics g);
}