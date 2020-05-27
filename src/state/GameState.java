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
        //not yet finished
        this.manager.addObserver(new UiImageButton((float) (handler.getWidth() - 162) / 2, (float) (handler.getHeight() - 2 * 26), 162, 26, Assets.turn, () -> {
            if(!island.menu.tradesMenu.isActive()) island.endOfTurn();
        }));
        this.manager.addObserver(island);
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

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && !island.menu.tradesMenu.isActive()) {
            //handler.getDisplay().close();
        }
        else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && island.menu.tradesMenu.isActive()){
            island.menu.tradesMenu.setActive(false);
            island.menu.setActive(false);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.menuBg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        this.manager.render(g);
    }
}
