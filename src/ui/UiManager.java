package ui;

import util.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UiManager {
    private Handler handler;
    public ArrayList<UiInteracter> objects;

    public UiManager(Handler handler) {
        this.handler = handler;
        objects = new ArrayList<>();
    }

    public void update() {
        for (UiInteracter o : objects) {
            o.update();
        }
    }

    public void render(Graphics g) {
        for (UiInteracter o : objects) {
            o.render(g);
        }
    }

    public void onMouseMove(MouseEvent e) {
        for (UiInteracter o : objects) {
            o.onMouseMove(e);
        }
    }

    public void onMousePressed(MouseEvent e){
        for(UiInteracter o : objects){
            o.onMousePressed(e);
        }
    }

    public void onMouseReleased(MouseEvent e){
        for(UiInteracter o : objects){
            o.onMouseReleased(e);
        }
    }

    public void onMouseClicked(MouseEvent e) {
        for (UiInteracter o : objects) {
            o.onMouseClicked(e);
        }
    }

    public void addObject(UiInteracter o) {
        objects.add(o);
    }

    public void clear() {
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