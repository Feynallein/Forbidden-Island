package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UiImageButton extends UiObject {
    private BufferedImage[] images;
    private ClickListener clicker;

    public UiImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
        super(x, y, width, height);
        this.images = images;
        this.clicker = clicker;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        if (click) {
            g.drawImage(images[2], (int) x, (int) y, width, height, null);
        } else if (isHovering()) {
            g.drawImage(images[1], (int) x, (int) y, width, height, null);
        } else
            g.drawImage(images[0], (int) x, (int) y, width, height, null);
    }

    @Override
    public void onClick() {
        clicker.onClick();
    }
}