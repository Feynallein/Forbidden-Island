package game.state;

import gfx.Assets;
import ui.Button;
import ui.ObjectManager;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.io.IOException;

public class MenuState extends State {
    private ObjectManager manager;

    public MenuState(Handler handler) {
        super(handler);
        this.manager = new ObjectManager();
        this.handler.getMouseManager().setObjectManager(manager);
        this.manager.addObject(new Button((float) (handler.getWidth() - Assets.buttonDim * 4) / 2, (float) (handler.getHeight() * 4 / 8 - Assets.buttonDim / 2), Assets.buttonDim * 4, Assets.buttonDim, Assets.newGame, () -> {
            this.handler.getMouseManager().setObjectManager(null);
            State.setState(new PlayerSelectionState(this.handler));
        }));
        this.manager.addObject(new Button((float) (handler.getWidth() - Assets.buttonDim * 4) / 2, (float) (handler.getHeight() * 5 / 8 - Assets.buttonDim / 2), Assets.buttonDim * 4, Assets.buttonDim, Assets.settings, () -> {
            //todo: options
            // - la dim (et la taille des fonts) en fct de la dimension de l'écran. Resolutions : 1920x1080, 1600x1200, 1280x720, 800x600
            // - music on/off (la mettre aussi dans le jeu et dans la pause)
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