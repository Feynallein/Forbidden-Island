package gfx;

import gameCommons.Game;
import ui.Observer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Observer {
    private Game game;

    public GamePanel(Game game, int width, int height) {
        this.game = game;
        game.addObserver(this);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(new Color(0x3C3F41));
        this.addMouseListener(game.mouseManager);
        this.addMouseMotionListener(game.mouseManager);
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.repaint();
        game.render(g);
    }
}
