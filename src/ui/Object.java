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

    public abstract void update();

    public abstract void render(Graphics g);

    public abstract void onClick();

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


    /* GETTERS and SETTERS */

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }
}
