package state;

import gfx.Assets;
import gfx.Text;
import util.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WinState extends State{
    WinState(Handler handler) {
        super(handler);
    }

    @Override
    public void update() {
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
            handler.getDisplay().close();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.menuBg, 0, 0, handler.getWidth(), handler.getHeight(), null);
        Text.drawString(g, "You win!", handler.getWidth()/2, handler.getHeight()/2, true, Color.BLACK, Assets.font45);
    }
}
