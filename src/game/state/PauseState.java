package game.state;

import gfx.Assets;
import ui.Button;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;
import util.Utils;

import java.awt.*;

public class PauseState extends State {
    ObjectManager manager;

    public PauseState(Handler handler) {
        super(handler);
        manager = new ObjectManager();
        handler.getMouseManager().setObjectManager(this.manager);
        manager.addObject(new Button((float) (handler.getWidth() - 7 * Assets.playerDim) / 2, (float) (handler.getHeight() * 4 / 8 - Assets.buttonDim / 2), 7 * Assets.playerDim, Assets.buttonDim,
                Assets.restart, () -> State.setState(new PlayerSelectionState(this.handler))));
        manager.addObject(new Button((float) (handler.getWidth() - (Assets.cardHeightDim + Assets.playerDim)) / 2, (float) (handler.getHeight() * 5 / 8 - Assets.buttonDim / 2), Assets.cardHeightDim + Assets.playerDim, Assets.buttonDim,
                Assets.resume, () -> {
            this.handler.getMouseManager().setObjectManager(this.handler.getUiManager());
            State.setState(this.handler.getSavedGameState());
        }));
        manager.addObject(new Button((float) (handler.getWidth() / 2 - Assets.dim * 3 / 2), (float) (handler.getHeight() * 6 / 8 - Assets.buttonDim / 2), Assets.dim * 3, Assets.buttonDim,
                Assets.mainMenu, () -> State.setState(new MenuState(this.handler))));
        manager.addObject(new Button((float) (handler.getWidth() - (Assets.dim + Assets.playerDim)) / 2, (float) (handler.getHeight() * 7 / 8 - Assets.buttonDim / 2), Assets.dim + Assets.playerDim, Assets.buttonDim,
                Assets.quit, () -> Utils.terminate(this.handler)));
        this.manager.addObject(new MultipleSpriteButtons((float) handler.getSpacing() * 2 + Assets.playerDim, (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, new int[]{handler.getSpacing() * 2 + Assets.playerDim, handler.getSpacing() * 2 + Assets.playerDim},
                new int[]{Assets.playerDim, Assets.playerDim}, Assets.musicOnOffArray, handler.getSettings().getProperty("music").equals("on") ? 0 : 1, () -> Utils.musicOnOff(this.handler)));
    }

    /* Update & render */

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.selectionBg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        g.drawImage(Assets.pauseIndicator, (handler.getWidth() - Assets.cardHeightDim * 2) / 2, handler.getHeight() / 8, Assets.cardHeightDim * 2, Assets.buttonDim * 2, null);
        manager.render(g);
    }
}
