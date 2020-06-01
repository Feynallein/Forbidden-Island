import gameCommons.Game;
import gfx.Display;
import util.MusicPlayer;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        int width = (int) tk.getScreenSize().getWidth();
        int height = (int) tk.getScreenSize().getHeight();
        MusicPlayer.player("Resources/music/music.mp3");
        SwingUtilities.invokeLater(() -> {
            Game game = new Game(width, height);
            Display display = new Display(game, width, height);
            Timer timer = new Timer(10, e -> game.run());
            timer.start();
        });
    }
}

//TODO:
// - le menu
// - la dim (et la taille des fonts) en fct de la dimension de l'écran
// - commenter le code
// - ajouter des boutons "restart, main menu & quit" aux win & lose states

//todo: soucis redimension "end of turn" & le dos de la carte qui s'affiche
// - résolution : 1920x1080, 1600x1200, 1280x720, 800x600
