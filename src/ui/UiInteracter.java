package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface UiInteracter {
    void update();

    void render(Graphics g);

    void onMouseMove(MouseEvent e);

    void onMouseClicked(MouseEvent e);

    void onMousePressed(MouseEvent e);

    void onMouseReleased(MouseEvent e);
}
