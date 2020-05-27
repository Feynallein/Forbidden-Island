package gfx;

import gameCommons.Initializer;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    public Display(int width, int height) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(0x3C3F41));
        panel.setPreferredSize(new Dimension(width, height));
        panel.add(new Initializer(width, height));

        this.add(panel);

        this.setTitle("Forbidden Island");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(width, height));
        this.setUndecorated(true);
        //frame.setResizable(false);

        this.pack();
        this.setVisible(true);
    }
}
