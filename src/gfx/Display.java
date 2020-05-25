package gfx;

import util.Handler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Display {
    private JFrame frame;
    private Canvas canvas;
    private Handler handler;

    public Display(Handler handler) {
        this.handler = handler;
        JFrame frame = new JFrame("Forbidden Island");
        this.frame = frame;
        frame.setSize(handler.getWidth(), handler.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(handler.getWidth(), handler.getHeight()));
        canvas.setMaximumSize(new Dimension(handler.getWidth(), handler.getHeight()));
        canvas.setMinimumSize(new Dimension(handler.getWidth(), handler.getHeight()));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    public void close(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }


    /* GETTERS AND SETTERS */

    public JFrame getFrame(){
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}