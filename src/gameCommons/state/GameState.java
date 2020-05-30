package gameCommons.state;

import gameCommons.Board.Island;
import gfx.Assets;
import ui.UiImageButton;
import ui.UiManager;
import util.Handler;

import java.awt.*;

public class GameState extends State {
    public Island island;
    public UiManager manager;

    public GameState(Handler handler) {
        super(handler);
        this.manager = new UiManager(handler);
        handler.getMouseManager().setUiManager(this.manager);
        this.island = new Island(handler, handler.getIslandLength(), handler.getNumberOfPlayers());
        this.manager.addObject(island);
        this.manager.addObject(new UiImageButton((float) handler.getSpacing(), (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, Assets.pause, () -> {
            handler.saveUiManager(manager);
            handler.getMouseManager().setUiManager(null);
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
