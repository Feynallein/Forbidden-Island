package game.state;

import gfx.Assets;
import gfx.Text;
import ui.Button;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;
import util.Utils;

import java.awt.*;

public class LoseState extends State {
    private String[] deathReasons;
    private ObjectManager manager;

    public LoseState(Handler handler) {
        super(handler);
        this.deathReasons = new String[]{"You lose! The heliport is flooded!",
                "You lose! the " + Utils.colorToString(handler.color) + " player drowned!",
                "You lose! All the " + Utils.artifactValueToString(handler.artifact) + " drowned!",
                "You lose! The water is too high!"};
        this.manager = new ObjectManager();
        this.handler.getMouseManager().setObjectManager(this.manager);
        this.manager.addObject(new ui.Button((float) (handler.getWidth() - 7 * Assets.playerDim) / 2, (float) (handler.getHeight() * 5 / 8 - Assets.buttonDim / 2), 7 * Assets.playerDim, Assets.buttonDim,
                Assets.restart, () -> State.setState(new PlayerSelectionState(this.handler))));
        this.manager.addObject(new ui.Button((float) (handler.getWidth() / 2 - Assets.dim * 3 / 2), (float) (handler.getHeight() * 6 / 8 - Assets.buttonDim / 2), Assets.dim * 3, Assets.buttonDim,
                Assets.mainMenu, () -> State.setState(new MenuState(this.handler))));
        this.manager.addObject(new Button((float) (handler.getWidth() - (Assets.dim + Assets.playerDim)) / 2, (float) (handler.getHeight() * 7 / 8 - Assets.buttonDim / 2), Assets.dim + Assets.playerDim, Assets.buttonDim,
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
        Text.drawString(g, deathReasons[handler.death], handler.getWidth() / 2, handler.getHeight() * 3 / 8, true, Color.BLACK, Assets.font45);
        manager.render(g);
    }
}
