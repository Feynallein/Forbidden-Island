package gameCommons;

import gfx.Assets;
import gfx.Display;
import state.*;
import util.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Initializer implements Runnable {

    /* the thread */
    private Thread thread;

    /* is our thread running ? */
    private boolean running = false;

    /* states */
    //public State menuState;
    public State gameState;
    public State loseState;
    public State winState;

    /* window */
    private Display graphic;

    /* managers */
    private MouseManager mouseManager;
    private KeyManager keyManager;

    /* handler */
    private Handler handler;

    /* window size */
    private int width;
    private int height;

    /* other */
    private int length = 6;

    public Initializer(int width, int height) {
        this.width = width;
        this.height = height;
        mouseManager = new MouseManager();
        keyManager = new KeyManager();
    }

    /* initialisation of the states, display, handler and sprite */

    private void init() {
        handler = new Handler(this);
        graphic = new Display(handler);
        graphic.getFrame().addKeyListener(keyManager);
        graphic.getFrame().addMouseListener(mouseManager);
        graphic.getFrame().addMouseMotionListener(mouseManager);
        graphic.getCanvas().addMouseListener(mouseManager);
        graphic.getCanvas().addMouseMotionListener(mouseManager);
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

    public void render() {
        BufferStrategy bs = graphic.getCanvas().getBufferStrategy();
        if (bs == null) {
            graphic.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        if (State.getState() != null) {
            State.getState().render(g);
        }
        bs.show();
        g.dispose();
    }

    /* what our thread does */

    @Override
    public void run() {
        init();

        int fps = 60;
        double timePerUpdate = 1000000000f / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerUpdate;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                delta--;
            }

            if (timer >= 1000000000) {
                timer = 0;
            }
        }
        stop();
    }

    /* start and stop methods */

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public Display getGraphic() {
        return graphic;
    }

    public int getLength() {
        return length;
    }
}
