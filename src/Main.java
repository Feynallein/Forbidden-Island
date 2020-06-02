import gameCommons.Game;
import gfx.Display;
import util.Handler;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws IOException {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Properties settings = new Properties();
        settings.load(new FileInputStream("Resources/settings.properties"));
        Handler handler = new Handler(settings);

        if(settings.getProperty("width").equals("0")) settings.setProperty("width", Integer.toString((int) tk.getScreenSize().getWidth()));
        if(settings.getProperty("height").equals("0")) settings.setProperty("height", Integer.toString((int) tk.getScreenSize().getHeight()));

        /* if troubles with JAVAFX comment the line below */
        //MusicPlayer.player("Resources/music/music.mp3");
        SwingUtilities.invokeLater(() -> {
            handler.setGame(new Game(handler));
            handler.setDisplay(new Display(handler.getGame(), handler));
            Timer timer = new Timer(10, e -> handler.getGame().run());
            timer.start();
        });
    }
}