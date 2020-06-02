package game.state;

import gfx.Assets;
import gfx.Text;
import ui.Button;
import ui.ObjectManager;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.io.IOException;

public class WinState extends State {
    private ObjectManager manager;

    public WinState(Handler handler) {
        super(handler);
        manager = new ObjectManager();
        handler.getMouseManager().setObjectManager(manager);
        manager.addObject(new Button((float) (handler.getWidth() - 7 * Assets.playerDim) / 2, (float) (handler.getHeight() * 5 / 8 - Assets.buttonDim / 2), 7 * Assets.playerDim, Assets.buttonDim,
                Assets.restart, () -> State.setState(new PlayerSelectionState(handler))));
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
    }

    /* Update & Render */

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, "You win!", handler.getWidth() / 2, handler.getHeight() * 3 / 8, true, Color.BLACK, Assets.font45);
        manager.render(g);
    }
}
