package gameCommons;

import gfx.Assets;
import state.*;
import util.*;

import javax.swing.*;
import java.awt.*;

public class Initializer extends JComponent{

    /* states */
    //public State menuState;
    public State gameState;
    public State loseState;
    public State winState;

    /* managers */
    private MouseManager mouseManager;
    private KeyManager keyManager;

    /* window size */
    private int width;
    private int height;

    public Initializer(int width, int height) {
        this.width = width;
        this.height = height;
        mouseManager = new MouseManager();
        keyManager = new KeyManager();
        Handler handler = new Handler(this);
        //        graphic.getFrame().addKeyListener(keyManager);
//        graphic.getFrame().addMouseListener(mouseManager);
//        graphic.getFrame().addMouseMotionListener(mouseManager);
//        graphic.getCanvas().addMouseListener(mouseManager);
//        graphic.getCanvas().addMouseMotionListener(mouseManager);
        Assets.init();
        gameState = new GameState(handler);
        State.setState(gameState);
    }

    /* update and render methods */

    private void update() {
        keyManager.update();
        if (State.getState() != null) {
            State.getState().update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (State.getState() != null) {
            State.getState().render(g);
        }
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
