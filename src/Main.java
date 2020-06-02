import gameCommons.Game;
import gfx.Assets;
import gfx.Display;
import util.Handler;
import util.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws IOException {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Properties settings = new Properties();

        /* Loading settings */
        settings.load(new FileInputStream("Resources/settings.properties"));

        if (settings.getProperty("fullscreen").equals("true")) {
            settings.setProperty("width", Integer.toString((int) tk.getScreenSize().getWidth()));
            settings.setProperty("height", Integer.toString((int) tk.getScreenSize().getHeight()));
        }
        if (settings.getProperty("music").equals("on")) MusicPlayer.player("Resources/music/music.mp3");

        /* Creating our handler */
        Handler handler = new Handler(settings);

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