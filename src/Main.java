import game.Game;
import gfx.Assets;
import gfx.Display;
import util.Handler;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        Properties settings = new Properties();

        /* Creating our handler */
        Handler handler = new Handler(settings);

        /* Loading settings */
        try {
            settings.load(new FileInputStream("Resources/settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (settings.getProperty("fullscreen").equals("true")) {
            settings.setProperty("width", Integer.toString((int) handler.getTk().getScreenSize().getWidth()));
            settings.setProperty("height", Integer.toString((int) handler.getTk().getScreenSize().getHeight()));
        }

        /* Loading assets */
        Assets.init(handler);

        /* Game loop */
        SwingUtilities.invokeLater(() -> {
            handler.setGame(new Game(handler));
            handler.setDisplay(new Display(handler));
            Timer timer = new Timer(10, e -> handler.getGame().run());
            timer.start();
        });
    }
}