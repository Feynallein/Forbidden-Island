package gameCommons.state;

import gfx.Assets;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SettingsState extends State {
    private ObjectManager manager;
    private boolean fullscreen;
    ArrayList<BufferedImage[]> screenSizeButtonSprites = new ArrayList<>();
    int[][] xes;
    int[][] widths;

    public SettingsState(Handler handler) {
        super(handler);
        manager = new ObjectManager(handler);
        initializeArrays();
        fullscreen = Boolean.parseBoolean(handler.getSettings().getProperty("fullscreen"));
        this.handler.getMouseManager().setObjectManager(manager);
        this.manager.addObject(new MultipleSpriteButtons(0, 0, Assets.playerDim*19, Assets.buttonDim, xes[0], widths[0], screenSizeButtonSprites, () -> {
            fullscreen = !fullscreen;
        }));
    }

    private void initializeArrays(){
        screenSizeButtonSprites.add(Assets.fullscreen);
        screenSizeButtonSprites.add(Assets.windowed);
        xes = new int[][]{{0, 0}};
        widths = new int[][]{{Assets.playerDim*19, Assets.buttonDim*4}};
    }

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render(Graphics g) {
        manager.render(g);
    }
}
