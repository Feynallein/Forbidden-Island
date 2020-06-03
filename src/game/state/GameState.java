package game.state;

import game.board.Island;
import gfx.Assets;
import ui.Button;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;
import util.Utils;

import java.awt.*;

public class GameState extends State {
    public Island island;
    public ObjectManager manager;

    public GameState(Handler handler) {
        super(handler);
        this.manager = new ObjectManager();
        this.handler.getMouseManager().setObjectManager(manager);
        island = new Island(handler, handler.getIslandLength(), handler.getNumberOfPlayers(), handler.getColorArray());
        manager.addObject(island);
        manager.addObject(new Button((float) handler.getSpacing(), (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, Assets.pause, () -> {
            this.handler.saveObjectManager(manager);
            this.handler.saveGameState(this);
            State.setState(new PauseState(this.handler));
        }));
        this.manager.addObject(new MultipleSpriteButtons((float) handler.getSpacing() * 2 + Assets.playerDim, (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, new int[]{handler.getSpacing() * 2 + Assets.playerDim, handler.getSpacing() * 2 + Assets.playerDim},
                new int[]{Assets.playerDim, Assets.playerDim}, Assets.musicOnOffArray, handler.getSettings().getProperty("music").equals("on") ? 0 : 1, () -> Utils.musicOnOff(this.handler)));
    }

    /* Update & Render */

    @Override
    public void update() {
        if (island.win()) {
            manager.clear();
            State.setState(new WinState(handler));
        } else if (island.lose()) {
            manager.clear();
            State.setState(new LoseState(handler));
        } else manager.update();
    }

    @Override
    public void render(Graphics g) {
        manager.render(g);
    }
}
