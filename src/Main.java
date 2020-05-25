import gameCommons.Initializer;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        int width = (int) tk.getScreenSize().getWidth();
        int height = (int) tk.getScreenSize().getHeight();
        Initializer game = new Initializer(width, height);
        game.start();
    }
}
