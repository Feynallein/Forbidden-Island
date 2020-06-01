package gameCommons.state;

import gfx.Assets;
import ui.Button;
import ui.ObjectManager;
import util.Handler;

import java.awt.*;

public class PauseState extends State {
    ObjectManager manager;

    public PauseState(Handler handler) {
        super(handler);
        manager = new ObjectManager(handler);
        handler.getMouseManager().setObjectManager(this.manager);
        manager.addObject(new Button((float) (handler.getWidth() - (Assets.cardHeightDim+Assets.playerDim)) / 2, (float) (handler.getHeight() * 5 / 8 - Assets.buttonDim / 2), Assets.cardHeightDim+Assets.playerDim, Assets.buttonDim,
                Assets.resume, () -> {
            handler.getMouseManager().setObjectManager(handler.getUiManager());
            State.setState(handler.getSavedGameState());
        }));
        manager.addObject(new Button((float) (handler.getWidth()/2 - Assets.dim*3/2),  (float) (handler.getHeight()*6/8 - Assets.buttonDim/2), Assets.dim*3, Assets.buttonDim,
                Assets.mainMenu, () -> {
               handler.getMouseManager().setObjectManager(null);
               State.setState(handler.getGame().menuState);
        }));
        manager.addObject(new Button((float) (handler.getWidth() - (Assets.dim+Assets.playerDim)) / 2, (float) (handler.getHeight() * 7 / 8 - Assets.buttonDim / 2), Assets.dim+Assets.playerDim, Assets.buttonDim,
                Assets.quit, () -> {
            //TODO: qq chose pour exit moins... brutalement
            System.exit(0);
        }));
    }

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.pauseIndicator, (handler.getWidth() - Assets.cardHeightDim*2)/2, handler.getHeight()/3, Assets.cardHeightDim*2, Assets.buttonDim*2, null);
        manager.render(g);
    }
}
