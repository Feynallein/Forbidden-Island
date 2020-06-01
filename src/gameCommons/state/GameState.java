package gameCommons.state;

import gameCommons.Board.Island;
import gfx.Assets;
import ui.Button;
import ui.ObjectManager;
import util.Handler;

import java.awt.*;

public class GameState extends State {
    public Island island;
    public ObjectManager manager;

    public GameState(Handler handler) {
        super(handler);
        this.manager = new ObjectManager(handler);
        handler.getMouseManager().setObjectManager(this.manager);
        this.island = new Island(handler, handler.getIslandLength(), handler.getNumberOfPlayers(), handler.getColorArray());
        this.manager.addObject(island);
        this.manager.addObject(new Button((float) handler.getSpacing(), (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, Assets.pause, () -> {
            handler.saveObjectManager(manager);
            handler.saveGameState(this);
            handler.getMouseManager().setObjectManager(null);
            State.setState(new PauseState(handler));
        }));
    }

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
