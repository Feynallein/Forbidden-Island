package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MultipleSpriteButtons extends Object {
    private ArrayList<BufferedImage[]> sprites;
    private int[] xes;
    private int[] widths;
    private ClickListener clicker;
    private int spriteCounter;

    public MultipleSpriteButtons(float x1, float y, int width1, int height, int[] xes, int[] widths, ArrayList<BufferedImage[]> sprites, int beginning, ClickListener clicker) {
        super(x1, y, width1, height);
        this.sprites = sprites;
        this.xes = xes;
        this.widths = widths;
        this.clicker = clicker;
        this.spriteCounter = beginning;
    }

    /* Update & Render */

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        if(spriteCounter >= sprites.size() || spriteCounter >= xes.length || spriteCounter >= widths.length) spriteCounter = 0;
        if (click) {
            g.drawImage(sprites.get(spriteCounter)[2], xes[spriteCounter], (int) y, widths[spriteCounter], height, null);
        } else if (isHovering()) {
            g.drawImage(sprites.get(spriteCounter)[1], xes[spriteCounter], (int) y, widths[spriteCounter], height, null);
        } else
            g.drawImage(sprites.get(spriteCounter)[0], xes[spriteCounter], (int) y, widths[spriteCounter], height, null);
    }

    /* Clicker */

    @Override
    public void onClick() {
        clicker.onClick();
        spriteCounter++;
        if (spriteCounter >= sprites.size()) spriteCounter = 0;
    }
}
