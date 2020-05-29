package gfx;

import gameCommons.Game;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    public Display(Game game, int width, int height) {
        this.setTitle("Forbidden Island");
        this.setPreferredSize(new Dimension(width, height));
        //this.setUndecorated(true);
        //frame.setResizable(false);

        this.add(new GamePanel(game, width, height));

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
