package game.state;

import game.Game;
import gfx.Assets;
import gfx.Display;
import ui.Button;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Properties;

public class SettingsState extends State {
    private ObjectManager manager;
    private boolean fullscreen;
    private ArrayList<BufferedImage[]> screenTypeButton = new ArrayList<>();
    private ArrayList<BufferedImage[]> screenSizeButtonSprites = new ArrayList<>();
    private int[][] xes;
    private int[][] widths;
    private int[] size = new int[2];

    public SettingsState(Handler handler) {
        super(handler);
        Properties backup = handler.getSettings();
        manager = new ObjectManager();
        initializeArrays();
        fullscreen = Boolean.parseBoolean(handler.getSettings().getProperty("fullscreen"));
        size[0] = Integer.parseInt(handler.getSettings().getProperty("width"));
        size[1] = Integer.parseInt(handler.getSettings().getProperty("height"));
        this.handler.getMouseManager().setObjectManager(manager);
        int[] i = {Utils.sizeToNumber(size[0], size[1])};
        this.manager.addObject(new MultipleSpriteButtons((float) (xes[0][fullscreen ? 0 : 1]), (float) handler.getHeight() / 3, widths[0][fullscreen ? 0 : 1], Assets.buttonDim, xes[0], widths[0], screenTypeButton, fullscreen ? 0 : 1, () -> fullscreen = !fullscreen));
        this.manager.addObject(new MultipleSpriteButtons((float) xes[1][i[0]], (float) handler.getHeight() * 2 / 3, widths[1][i[0]], Assets.buttonDim, xes[1], widths[1], screenSizeButtonSprites, i[0], () -> {
            i[0]++;
            if (i[0] > 3) i[0] = 0;
            size = Utils.numberToSize(i[0]);
        }));
        this.manager.addObject(new Button((float) (handler.getWidth() * 3 / 4), (float) (handler.getHeight() * 3 / 4), Assets.playerDim * 2, Assets.playerDim * 2, Assets.returned, () -> {
            handler.getSettings().setProperty("fullscreen", Boolean.toString(fullscreen));
            try {
                handler.getSettings().store(new FileWriter("Resources/settings.properties"), "Settings File");
            } catch (Exception ignored) {
            }
            if (handler.getSettings().getProperty("fullscreen").equals("true")) {
                handler.getSettings().setProperty("width", Integer.toString((int) handler.getTk().getScreenSize().getWidth()));
                handler.getSettings().setProperty("height", Integer.toString((int) handler.getTk().getScreenSize().getHeight()));
            } else {
                handler.getSettings().setProperty("width", Integer.toString(size[0]));
                handler.getSettings().setProperty("height", Integer.toString(size[1]));
            }
            try {
                handler.getSettings().store(new FileWriter("Resources/settings.properties"), "Settings File");
            } catch (Exception ignored) {
            }
            //if (!backup.equals(handler.getSettings())) { //todo: corriger ici
            handler.getDisplay().dispose();
            handler.setGame(new Game(handler));
            handler.setDisplay(new Display(handler));
            //} else State.setState(new MenuState(handler));
        }));
    }

    /* Initialize the arrays */

    private void initializeArrays() {
        screenTypeButton.add(Assets.fullscreen);
        screenTypeButton.add(Assets.windowed);
        screenSizeButtonSprites.add(Assets.res800x600);
        screenSizeButtonSprites.add(Assets.res1280x720);
        screenSizeButtonSprites.add(Assets.res1600x1200);
        screenSizeButtonSprites.add(Assets.res1920x1080);
        xes = new int[][]{{(handler.getWidth() - Assets.playerDim * 19) / 2, (handler.getWidth() - Assets.buttonDim * 4) / 2},
                {(handler.getWidth() - Assets.dim * 3) / 2, (handler.getWidth() - Assets.buttonDim * 5) / 2, (handler.getWidth() - Assets.playerDim * 11) / 2, (handler.getWidth() - Assets.playerDim * 11) / 2,}};
        widths = new int[][]{{Assets.playerDim * 19, Assets.buttonDim * 4}, {Assets.dim * 3, Assets.buttonDim * 5, Assets.playerDim * 11, Assets.playerDim * 11}};
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
