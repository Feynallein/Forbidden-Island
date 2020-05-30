package util;

import ui.UiManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
    private UiManager manager;

    public MouseManager() {
    }

    public void setUiManager(UiManager uiManager) {
        this.manager = uiManager;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (manager != null)
            manager.onMouseMove(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (manager != null)
            manager.onMouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(manager != null) manager.onMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(manager != null) manager.onMouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}