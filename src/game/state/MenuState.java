package game.state;

import gfx.Assets;
import ui.Button;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;
import util.Utils;

import java.awt.*;

public class MenuState extends State {
    private ObjectManager manager;

    public MenuState(Handler handler) {
        super(handler);
        this.manager = new ObjectManager();
        this.handler.getMouseManager().setObjectManager(this.manager);
        this.manager.addObject(new Button((float) (handler.getWidth() - Assets.buttonDim * 4) / 2, (float) (handler.getHeight() * 4 / 8 - Assets.buttonDim / 2), Assets.buttonDim * 4, Assets.buttonDim, Assets.newGame, () -> State.setState(new PlayerSelectionState(this.handler))));
        this.manager.addObject(new Button((float) (handler.getWidth() - Assets.buttonDim * 4) / 2, (float) (handler.getHeight() * 5 / 8 - Assets.buttonDim / 2), Assets.buttonDim * 4, Assets.buttonDim, Assets.settings, () -> State.setState(new SettingsState(this.handler))));
        this.manager.addObject(new Button((float) (handler.getWidth() - (Assets.cardHeightDim + Assets.playerDim)) / 2, (float) (handler.getHeight() * 6 / 8 - Assets.buttonDim / 2), Assets.cardHeightDim + Assets.playerDim, Assets.buttonDim, Assets.credits, () -> State.setState(new CreditsState(this.handler))));
        manager.addObject(new Button((float) (handler.getWidth() - (Assets.dim + Assets.playerDim)) / 2, (float) (handler.getHeight() * 7 / 8 - Assets.buttonDim / 2), Assets.dim + Assets.playerDim, Assets.buttonDim,
                Assets.quit, () -> Utils.terminate(this.handler)));
        this.manager.addObject(new MultipleSpriteButtons((float) handler.getSpacing() * 2 + Assets.playerDim, (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, new int[]{handler.getSpacing() * 2 + Assets.playerDim, handler.getSpacing() * 2 + Assets.playerDim},
                new int[]{Assets.playerDim, Assets.playerDim}, Assets.musicOnOffArray, handler.getSettings().getProperty("music").equals("on") ? 0 : 1, () -> Utils.musicOnOff(this.handler)));
    }

    /* Update & Render */

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.menuBg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        g.drawImage(Assets.menuTitle, (handler.getWidth() - 16*Assets.buttonDim)/2, handler.getHeight()/3 - 2*Assets.buttonDim, 16*Assets.buttonDim, Assets.buttonDim*2, null);
        manager.render(g);
    }
}
