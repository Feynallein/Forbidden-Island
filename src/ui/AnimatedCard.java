package ui;

import util.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedCard extends Object {
    private BufferedImage[] animation;
    private BufferedImage sprite;
    private ClickListener clicker;
    public boolean clicked;
    private int animationFlag = 0;
    private Handler handler;

    public AnimatedCard(float x, float y, int width, int height, BufferedImage[] animation, BufferedImage sprite, Handler handler, ClickListener clicker) {
        super(x, y, width, height);
        this.animation = animation;
        this.clicker = clicker;
        this.sprite = sprite;
        this.handler = handler;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        if (clicked) {
            if (animationFlag >= animation.length) {
                g.drawImage(sprite, (int) x, (int) y, width, height, null);
            } else {
                g.drawImage(animation[animationFlag], (int) x, (int) y, width, height, null);
                animationFlag++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        } else {
            /*if(hovering) g.drawImage(Assets.cardBg, (int) x, (int) y, width, height, null);*/
            g.drawImage(animation[0], (int) x, (int) y, width, height, null);
        }
    }

    @Override
    public void onClick() {
        if(handler.getColorArray().size() < 4) {
            clicker.onClick();
            clicked = true;
        }
    }
}
