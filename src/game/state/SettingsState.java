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
        Properties backup = (Properties) this.handler.getSettings().clone();
        this.manager = new ObjectManager();
        initialize();
        this.handler.getMouseManager().setObjectManager(this.manager);
        int[] i = {Utils.sizeToNumber(size[0], size[1])};
        this.manager.addObject(new MultipleSpriteButtons((float) (xes[0][fullscreen ? 0 : 1]), (float) handler.getHeight() / 3, widths[0][fullscreen ? 0 : 1], Assets.buttonDim, xes[0], widths[0], screenTypeButton, fullscreen ? 0 : 1, () -> fullscreen = !fullscreen));
        this.manager.addObject(new MultipleSpriteButtons((float) xes[1][i[0]], (float) handler.getHeight() * 2 / 3, widths[1][i[0]], Assets.buttonDim, xes[1], widths[1], screenSizeButtonSprites, i[0], () -> {
            i[0]++;
            if (i[0] > 3) i[0] = 0;
            size = Utils.numberToSize(i[0]);
        }));
        this.manager.addObject(new Button((float) (handler.getWidth() * 3 / 4), (float) (handler.getHeight() * 3 / 4), Assets.playerDim * 2, Assets.playerDim * 2, Assets.returned, () -> {
            this.handler.getSettings().setProperty("fullscreen", Boolean.toString(fullscreen));
            Utils.saveProperties(this.handler);
            if (this.handler.getSettings().getProperty("fullscreen").equals("true")) {
                this.handler.getSettings().setProperty("width", Integer.toString((int) this.handler.getTk().getScreenSize().getWidth()));
                this.handler.getSettings().setProperty("height", Integer.toString((int) this.handler.getTk().getScreenSize().getHeight()));
            } else {
                this.handler.getSettings().setProperty("width", Integer.toString(size[0]));
                this.handler.getSettings().setProperty("height", Integer.toString(size[1]));
            }
            Utils.saveProperties(this.handler);
            if (!backup.equals(this.handler.getSettings())) {
                this.handler.getDisplay().dispose();
                this.handler.setGame(new Game(this.handler));
                this.handler.setDisplay(new Display(this.handler));
            } else State.setState(new MenuState(this.handler));
        }));
        this.manager.addObject(new MultipleSpriteButtons((float) handler.getSpacing() * 2 + Assets.playerDim, (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, new int[]{handler.getSpacing() * 2 + Assets.playerDim, handler.getSpacing() * 2 + Assets.playerDim},
                new int[]{Assets.playerDim, Assets.playerDim}, Assets.musicOnOffArray, handler.getSettings().getProperty("music").equals("on") ? 0 : 1, () -> Utils.musicOnOff(this.handler)));
    }

    /* Initialize the local variables */

    private void initialize() {
        screenTypeButton.add(Assets.fullscreen);
        screenTypeButton.add(Assets.windowed);
        screenSizeButtonSprites.add(Assets.res800x600);
        screenSizeButtonSprites.add(Assets.res1280x720);
        screenSizeButtonSprites.add(Assets.res1600x1200);
        screenSizeButtonSprites.add(Assets.res1920x1080);
        xes = new int[][]{{(handler.getWidth() - Assets.playerDim * 19) / 2, (handler.getWidth() - Assets.buttonDim * 4) / 2},
                {(handler.getWidth() - Assets.dim * 3) / 2, (handler.getWidth() - Assets.buttonDim * 5) / 2, (handler.getWidth() - Assets.playerDim * 11) / 2, (handler.getWidth() - Assets.playerDim * 11) / 2,}};
        widths = new int[][]{{Assets.playerDim * 19, Assets.buttonDim * 4}, {Assets.dim * 3, Assets.buttonDim * 5, Assets.playerDim * 11, Assets.playerDim * 11}};
        fullscreen = Boolean.parseBoolean(handler.getSettings().getProperty("fullscreen"));
        size[0] = Integer.parseInt(handler.getSettings().getProperty("width"));
        size[1] = Integer.parseInt(handler.getSettings().getProperty("height"));
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
