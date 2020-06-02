package game.state;

import gfx.Assets;
import ui.Button;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MenuState extends State {
    private ObjectManager manager;

    public MenuState(Handler handler) {
        super(handler);
        this.manager = new ObjectManager();
        ArrayList<BufferedImage[]> sprites = new ArrayList<>();
        sprites.add(Assets.musicOn);
        sprites.add(Assets.musicOff);
        this.handler.getMouseManager().setObjectManager(manager);
        this.manager.addObject(new Button((float) (handler.getWidth() - Assets.buttonDim * 4) / 2, (float) (handler.getHeight() * 4 / 8 - Assets.buttonDim / 2), Assets.buttonDim * 4, Assets.buttonDim, Assets.newGame, () -> {
            this.handler.getMouseManager().setObjectManager(null);
            State.setState(new PlayerSelectionState(this.handler));
        }));
        this.manager.addObject(new Button((float) (handler.getWidth() - Assets.buttonDim * 4) / 2, (float) (handler.getHeight() * 5 / 8 - Assets.buttonDim / 2), Assets.buttonDim * 4, Assets.buttonDim, Assets.settings, () -> {
            State.setState(new SettingsState(handler));
        }));
        this.manager.addObject(new Button((float) (handler.getWidth() - (Assets.cardHeightDim + Assets.playerDim)) / 2, (float) (handler.getHeight() * 6 / 8 - Assets.buttonDim / 2), Assets.cardHeightDim + Assets.playerDim, Assets.buttonDim, Assets.credits, () -> {
            this.handler.getMouseManager().setObjectManager(null);
            State.setState(new CreditsState(this.handler));
        }));
        manager.addObject(new Button((float) (handler.getWidth() - (Assets.dim + Assets.playerDim)) / 2, (float) (handler.getHeight() * 7 / 8 - Assets.buttonDim / 2), Assets.dim + Assets.playerDim, Assets.buttonDim,
                Assets.quit, () -> {
            try {
                Utils.terminate(handler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        this.manager.addObject(new MultipleSpriteButtons((float) handler.getSpacing() * 2 + Assets.playerDim, (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, new int[]{handler.getSpacing() * 2 + Assets.playerDim, handler.getSpacing() * 2 + Assets.playerDim},
                new int[]{Assets.playerDim, Assets.playerDim}, sprites, handler.getSettings().getProperty("music").equals("on") ? 0 : 1, () -> {
            if (handler.getSettings().getProperty("music").equals("on")) {
                handler.getSettings().setProperty("music", "off");
            } else {
                handler.getSettings().setProperty("music", "on");
            }
        }));
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
