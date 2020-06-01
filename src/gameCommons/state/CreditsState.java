package gameCommons.state;

import gfx.Assets;
import gfx.Text;
import ui.Button;
import ui.ObjectManager;
import util.Handler;

import java.awt.*;

public class CreditsState extends State{
    private ObjectManager manager;

    CreditsState(Handler handler) {
        super(handler);
        this.manager = new ObjectManager(handler);
        this.handler.getMouseManager().setObjectManager(manager);
        this.manager.addObject(new Button((float) (handler.getWidth()*3/4), (float) (handler.getHeight()*3/4), Assets.playerDim*2, Assets.playerDim*2, Assets.returned, () -> {
            State.setState(new MenuState(handler));
        }));
    }

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void render(Graphics g) {
        manager.render(g);
        Text.drawString(g, "Original game by Matt Leacock", handler.getWidth()/2, handler.getHeight()*3/9, true, Color.GRAY, Assets.font45);
        Text.drawString(g, "A game by Feynallein & Dreyc", handler.getWidth()/2, handler.getHeight()*4/9, true, Color.GRAY, Assets.font45);
        Text.drawString(g, "Font by Codeman38", handler.getWidth()/2, handler.getHeight()*5/9, true, Color.GRAY, Assets.font45);
    }
}
