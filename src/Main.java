import game.Game;
import gfx.Display;
import util.Handler;
import util.MusicPlayer;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        Properties settings = new Properties();

        /* Loading settings */
        try {
            settings.load(new FileInputStream("Resources/settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Creating our handler */
        Handler handler = new Handler(settings);

        /* Setting the music */
        handler.setMusicPlayer(new MusicPlayer(handler, "Resources/music/music.wav"));

        /* Setting the size */
        if (settings.getProperty("fullscreen").equals("true")) {
            settings.setProperty("width", Integer.toString((int) handler.getTk().getScreenSize().getWidth()));
            settings.setProperty("height", Integer.toString((int) handler.getTk().getScreenSize().getHeight()));
        }

        /* Game loop */
        SwingUtilities.invokeLater(() -> {
            handler.setGame(new Game(handler));
            handler.setDisplay(new Display(handler));
            Timer timer = new Timer(10, e -> handler.getGame().run());
            timer.start();
        });
    }
}