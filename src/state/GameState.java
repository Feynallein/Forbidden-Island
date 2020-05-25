package state;

import gameCommons.Island;
import gfx.Assets;
import gfx.Text;
import ui.ObservableManager;
import ui.UiImageButton;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameState extends State {
    public Island island;
    private ObservableManager manager;

    public GameState(Handler handler) {
        super(handler);
        this.island = new Island(handler, handler.getIslandLength(), 6);
        this.manager = new ObservableManager(handler);
        handler.getMouseManager().setObservableManager(this.manager);
        this.manager.addObserver(island);
        //not yet finished
        this.manager.addObserver(new UiImageButton((float) (handler.getWidth() - 162) / 2, (float) (handler.getHeight() - 2 * 26), 162, 26, Assets.turn, () -> {
            island.endOfTurn();
        }));
    }

    @Override
    public void update() {
        if (island.win()) {
            this.manager.clear();
            handler.getInitializer().winState = new WinState(handler);
            State.setState(handler.getInitializer().winState);
        } else if (island.lose()) {
            this.manager.clear();
            handler.getInitializer().loseState = new LoseState(handler);
            State.setState(handler.getInitializer().loseState);
        } else this.manager.update();

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            handler.getDisplay().close();
        }
    }

    @Override
    public void render(Graphics g) {
        this.manager.render(g);
    }
}
