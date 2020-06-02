package ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ObjectManager {
    public ArrayList<Interacts> objects;

    public ObjectManager() {
        objects = new ArrayList<>();
    }

    public void addObject(Interacts o) {
        objects.add(o);
    }

    public void clear() {
        this.objects.clear();
    }

    /* Update & Render */
    public void update() {
        for (Interacts o : objects) {
            o.update();
        }
    }

    public void render(Graphics g) {
        for (Interacts o : objects) {
            o.render(g);
        }
    }

    /* Mouse Manager */
    public void onMouseMove(MouseEvent e) {
        for (Interacts o : objects) {
            o.onMouseMove(e);
        }
    }

    public void onMousePressed(MouseEvent e) {
        for (Interacts o : objects) {
            o.onMousePressed(e);
        }
    }

    public void onMouseReleased(MouseEvent e) {
        for (Interacts o : objects) {
            o.onMouseReleased(e);
        }
    }

    public void onMouseClicked(MouseEvent e) {
        for (Interacts o : objects) {
            o.onMouseClicked(e);
        }
    }
}