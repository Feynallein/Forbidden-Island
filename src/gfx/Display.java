package gfx;

import util.Handler;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    public Display(Handler handler) {
        this.setTitle("Forbidden Island");
        this.setPreferredSize(new Dimension(handler.getWidth(), handler.getHeight()));
        this.setUndecorated(Boolean.parseBoolean(handler.getSettings().getProperty("fullscreen")));
        this.setResizable(false);
        this.add(new GamePanel(handler.getGame(), handler.getWidth(), handler.getHeight()));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
