package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends Object {
    private BufferedImage[] sprite;
    private ClickListener clicker;

    public Button(float x, float y, int width, int height, BufferedImage[] sprite, ClickListener clicker) {
        super(x, y, width, height);
        this.sprite = sprite;
        this.clicker = clicker;
    }

    /* Update & Render */

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        if (click) {
            g.drawImage(sprite[2], (int) x, (int) y, width, height, null);
        } else if (isHovering()) {
            g.drawImage(sprite[1], (int) x, (int) y, width, height, null);
        } else
            g.drawImage(sprite[0], (int) x, (int) y, width, height, null);
    }

    /* Clicker */

    @Override
    public void onClick() {
        try {
            clicker.onClick();
        } catch (Exception ignored) {
        }
    }
}