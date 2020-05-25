package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Observer {
    public void update();
    public void render(Graphics g);
    public void onMouseClicked(MouseEvent e);
    public void onMouseReleased(MouseEvent e);
    public void onMousePressed(MouseEvent e);
    public void onMouseMove(MouseEvent e);
}
