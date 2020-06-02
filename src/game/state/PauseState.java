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

public class PauseState extends State {
    ObjectManager manager;

    public PauseState(Handler handler) {
        super(handler);
        manager = new ObjectManager();
        ArrayList<BufferedImage[]> sprites = new ArrayList<>();
        sprites.add(Assets.musicOn);
        sprites.add(Assets.musicOff);
        handler.getMouseManager().setObjectManager(this.manager);
        manager.addObject(new Button((float) (handler.getWidth() - 7 * Assets.playerDim) / 2, (float) (handler.getHeight() * 4 / 8 - Assets.buttonDim / 2), 7 * Assets.playerDim, Assets.buttonDim,
                Assets.restart, () -> State.setState(new PlayerSelectionState(handler))));
        manager.addObject(new Button((float) (handler.getWidth() - (Assets.cardHeightDim + Assets.playerDim)) / 2, (float) (handler.getHeight() * 5 / 8 - Assets.buttonDim / 2), Assets.cardHeightDim + Assets.playerDim, Assets.buttonDim,
                Assets.resume, () -> {
            handler.getMouseManager().setObjectManager(handler.getUiManager());
            State.setState(handler.getSavedGameState());
        }));
        manager.addObject(new Button((float) (handler.getWidth() / 2 - Assets.dim * 3 / 2), (float) (handler.getHeight() * 6 / 8 - Assets.buttonDim / 2), Assets.dim * 3, Assets.buttonDim,
                Assets.mainMenu, () -> {
            handler.getMouseManager().setObjectManager(null);
            State.setState(new MenuState(handler));
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

    /* Update & render */

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.menuBg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        g.drawImage(Assets.pauseIndicator, (handler.getWidth() - Assets.cardHeightDim * 2) / 2, handler.getHeight() / 8, Assets.cardHeightDim * 2, Assets.buttonDim * 2, null);
        manager.render(g);
    }
}
