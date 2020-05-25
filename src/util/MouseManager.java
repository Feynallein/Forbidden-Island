package util;

import ui.ObservableManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
    private boolean leftPressed, rightPressed;
    private int mouseX, mouseY;
    private ObservableManager observableManager;

    public MouseManager(){
    }

    public void setObservableManager(ObservableManager observableManager){
        this.observableManager = observableManager;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if(observableManager != null)
            observableManager.onMouseMove(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)//button1 = lmb (2 = middle mouse button)
            leftPressed = true;
        else if(e.getButton() == MouseEvent.BUTTON3)//buton3 = rmb
            rightPressed = true;
        if(observableManager != null)
            observableManager.onMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)//button1 = lmb (2 = middle mouse button)
            leftPressed = false;
        else if(e.getButton() == MouseEvent.BUTTON3)//buton3 = rmb
            rightPressed = false;
        if(observableManager != null)
            observableManager.onMouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(observableManager != null)
            observableManager.onMouseClicked(e);
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


    /* GETTERS AND SETTERS */

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}