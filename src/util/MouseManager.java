package util;

import ui.UiManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
    private UiManager uiManager;

    public MouseManager() {
    }

    public void setUiManager(UiManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (uiManager != null)
            uiManager.onMouseMove(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (uiManager != null)
            uiManager.onMouseClicked(e);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

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