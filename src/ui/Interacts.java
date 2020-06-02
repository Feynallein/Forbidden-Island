package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Interacts {
    /* Update & Render */

    void update();

    void render(Graphics g);

    /* Mouse Manager */

    void onMouseMove(MouseEvent e);

    void onMouseClicked(MouseEvent e);

    void onMousePressed(MouseEvent e);

    void onMouseReleased(MouseEvent e);
}
