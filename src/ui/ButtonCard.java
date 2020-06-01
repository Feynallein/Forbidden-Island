package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ButtonCard extends Object {
    private BufferedImage[] animation;
    private BufferedImage sprite;
    private ClickListener clicker;
    public boolean clicked;
    private int animationFlag = 0;

    public ButtonCard(float x, float y, int width, int height, BufferedImage[] animation, BufferedImage sprite,  ClickListener clicker) {
        super(x, y, width, height);
        this.animation = animation;
        this.clicker = clicker;
        this.sprite = sprite;
    }

    @Override
    public void update() {

    }

    //TODO: tenter d'optimiser quand on retourne plusieurs cartes, + faire qu'on ne puisse pas retourner plus de 4 cartes
    @Override
    public void render(Graphics g) {
        if(clicked) {
            if(animationFlag >= animation.length){ g.drawImage(sprite, (int) x, (int) y, width, height, null); }
            else {
                g.drawImage(animation[animationFlag], (int) x, (int) y, width, height, null);
                animationFlag++;
                try{Thread.sleep(200);} catch (InterruptedException ignored) {}
            }
        }
        else {
            /*if(hovering) g.drawImage(Assets.cardBg, (int) x, (int) y, width, height, null);*/
            g.drawImage(animation[0], (int) x, (int) y, width, height, null);
        }
    }

    @Override
    public void onClick() {
        clicker.onClick();
        clicked = true;
    }
}
