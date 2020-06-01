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
        manager.addObject(new Button((float) (handler.getWidth() - Assets.resume[0].getWidth()) / 2, (float) (handler.getHeight() * 2 / 3 - Assets.resume[0].getHeight() / 2), Assets.resume[0].getWidth(), Assets.resume[0].getHeight(),
                Assets.resume, () -> {
            handler.getMouseManager().setObjectManager(handler.getUiManager());
            //State.setState(handler.getGame().gameState);
        }));
        //TODO: bouton 'main menu' qui retourne au menu
//        manager.addObject(new UiImageButton((float) (handler.getWidth()/2 - Assets.widths.get(2)/2),  (float) (handler.getHeight()*5/8 - Assets.buttonSize/2), Assets.widths.get(2), Assets.buttonSize,
//                Assets.mainMenu, () -> {
//               handler.getMouseManager().setUiManager(null);
//        }));
        manager.addObject(new Button((float) (handler.getWidth() - Assets.quit[0].getWidth()) / 2, (float) (handler.getHeight() * 7 / 8 - Assets.quit[0].getHeight() / 2), Assets.quit[0].getWidth(), Assets.quit[0].getHeight(),
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
