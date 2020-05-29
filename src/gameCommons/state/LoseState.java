package gameCommons.state;

import gfx.Assets;
import gfx.Text;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LoseState extends State {
    LoseState(Handler handler) {
        super(handler);
    }

    @Override
    public void update() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            //handler.getDisplay().close();
        }
    }

    @Override
    public void render(Graphics g) {
        String[] deathReasons = new String[]{"You lose! The heliport is flooded!",
                "You lose! the " + Utils.colorToString(handler.color) + " player drowned!",
                "You lose! All the " + Utils.artifactValueToString(handler.artifact) + " drowned!",
                "You lose! The water is too high!"};
        Text.drawString(g, deathReasons[handler.death], handler.getWidth() / 2, handler.getHeight() / 2, true, Color.BLACK, Assets.font45);
    }
}
