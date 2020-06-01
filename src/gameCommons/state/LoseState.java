package gameCommons.state;

import gfx.Assets;
import gfx.Text;
import util.Handler;
import util.Utils;

import java.awt.*;

public class LoseState extends State {
    private String[] deathReasons;

    LoseState(Handler handler) {
        super(handler);
        deathReasons = new String[]{"You lose! The heliport is flooded!",
                "You lose! the " + Utils.colorToString(handler.color) + " player drowned!",
                "You lose! All the " + Utils.artifactValueToString(handler.artifact) + " drowned!",
                "You lose! The water is too high!"};
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, deathReasons[handler.death], handler.getWidth() / 2, handler.getHeight() / 2, true, Color.BLACK, Assets.font45);
    }
}
