import gameCommons.Game;
import gfx.Display;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        int width = (int) tk.getScreenSize().getWidth();
        int height = (int) tk.getScreenSize().getHeight();
        SwingUtilities.invokeLater(() -> {
            Game game = new Game(width, height);
            Display display = new Display(game, width, height);
            Timer timer = new Timer(10, e -> game.run());
            timer.start();
        });
    }
}
