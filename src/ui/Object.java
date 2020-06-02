package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Object implements Interacts {
    float x, y;
    int width, height;
    private Rectangle bounds;
    public boolean hovering = false, click = false;

    Object(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }

    /* Update & Render */
    public abstract void update();

    public abstract void render(Graphics g);

    /* Clicker */

    public abstract void onClick();

    /* Mouse Manager */

    public void onMouseMove(MouseEvent e) {
        hovering = bounds.contains(e.getX(), e.getY());
    }

    public void onMouseClicked(MouseEvent e) {
        if (hovering) onClick();
    }

    public void onMousePressed(MouseEvent e) {
        if (hovering) click = true;
    }

    public void onMouseReleased(MouseEvent e) {
        if (hovering) click = false;
    }


    /* Getter */

    boolean isHovering() {
        return hovering;
    }
}
