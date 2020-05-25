package ui;

import util.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ObservableManager {
    private Handler handler;
    private ArrayList<Observer> objects;

    public ObservableManager(Handler handler) {
        this.handler = handler;
        objects = new ArrayList<>();
    }

    public void update() {
        for (Observer o : objects) {
            o.update();
        }
    }

    public void render(Graphics g) {
        for (Observer o : objects) {
            o.render(g);
        }
    }

    public void onMouseMove(MouseEvent e) {
        for (Observer o : objects) {
            o.onMouseMove(e);
        }
    }

    public void onMousePressed(MouseEvent e) {
        for (Observer o : objects) {
            o.onMousePressed(e);
        }
    }

    public void onMouseReleased(MouseEvent e) {
        for (Observer o : objects) {
            o.onMouseReleased(e);
        }
    }

    public void onMouseClicked(MouseEvent e) {
        for (Observer o : objects) {
            o.onMouseClicked(e);
        }
    }

    public void addObserver(Observer o) {
        objects.add(o);
    }

    public void clear(){
        this.objects.clear();
    }


    /* GETTERS AND SETTERS */

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}