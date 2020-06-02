package gfx;

import gameCommons.Game;
import util.Handler;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    public Display(Game game, Handler handler) {
        this.setTitle("Forbidden Island");
        this.setPreferredSize(new Dimension(handler.getWidth(), handler.getHeight()));
        this.setUndecorated(true);
        //frame.setResizable(false);

        this.add(new GamePanel(game, handler.getWidth(), handler.getHeight()));

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
