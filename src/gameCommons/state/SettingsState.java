package gameCommons.state;

import gameCommons.Game;
import gfx.Assets;
import gfx.Display;
import ui.Button;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Properties;

public class SettingsState extends State {
    private ObjectManager manager;
    private boolean fullscreen;
    ArrayList<BufferedImage[]> screenSizeButtonSprites = new ArrayList<>();
    int[][] xes;
    int[][] widths;

    public SettingsState(Handler handler) {
        super(handler);
        Properties backup = handler.getSettings();
        manager = new ObjectManager();
        initializeArrays();
        fullscreen = Boolean.parseBoolean(handler.getSettings().getProperty("fullscreen"));
        this.handler.getMouseManager().setObjectManager(manager);
        this.manager.addObject(new MultipleSpriteButtons((float) (xes[0][fullscreen ? 0 : 1]), (float) handler.getHeight() / 3, widths[0][fullscreen ? 0 : 1], Assets.buttonDim, xes[0], widths[0], screenSizeButtonSprites, fullscreen ? 0 : 1, () -> fullscreen = !fullscreen));
        this.manager.addObject(new Button((float) (handler.getWidth() * 3 / 4), (float) (handler.getHeight() * 3 / 4), Assets.playerDim * 2, Assets.playerDim * 2, Assets.returned, () -> {
            if (!backup.equals(handler.getSettings())) {
                //handler.getSettings().setProperty("width", "1500");
                handler.getSettings().setProperty("fullscreen", Boolean.toString(fullscreen));
                handler.getDisplay().dispose();
                handler.setGame(new Game(handler));
                handler.setDisplay(new Display(handler));
            } else State.setState(new MenuState(handler));
        }));

    }

    /* Initialize the arrays */

    private void initializeArrays() {
        screenSizeButtonSprites.add(Assets.fullscreen);
        screenSizeButtonSprites.add(Assets.windowed);
        xes = new int[][]{{(handler.getWidth() - Assets.playerDim * 19) / 2, (handler.getWidth() - Assets.buttonDim * 4) / 2}};
        widths = new int[][]{{Assets.playerDim * 19, Assets.buttonDim * 4}};
    }

    /* Update & Render */

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render(Graphics g) {
        manager.render(g);
    }
}
