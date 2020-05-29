package gameCommons.state;

import gameCommons.Board.Island;
import gfx.Assets;
import ui.UiImageButton;
import ui.UiManager;
import util.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameState extends State {
    public Island island;
    private UiManager manager;

    public GameState(Handler handler) {
        super(handler);
        this.island = new Island(handler, handler.getIslandLength(), handler.getNumberOfPlayers());
        this.manager = new UiManager(handler);
        handler.getMouseManager().setUiManager(this.manager);
        //not yet finished
        this.manager.addObject(new UiImageButton((float) (handler.getWidth() - 162) / 2, (float) (handler.getHeight() - 2 * 26), 162, 26, Assets.turn, () -> {
            island.endOfTurn();
        }));

        this.manager.addObject(island);
    }

    @Override
    public void update() {
        if (island.win()) {
            this.manager.clear();
            handler.getGame().winState = new WinState(handler);
            State.setState(handler.getGame().winState);
        } else if (island.lose()) {
            this.manager.clear();
            handler.getGame().loseState = new LoseState(handler);
            State.setState(handler.getGame().loseState);
        } else this.manager.update();

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && island.menu.tradesMenu.isActive()) {
            island.menu.tradesMenu.setActive(false);
            island.menu.setVisible(false);
        }
    }

    @Override
    public void render(Graphics g) {
        this.manager.render(g);
    }
}
