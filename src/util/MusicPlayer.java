package util;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;

public class MusicPlayer {
    //todo: ameliorer pour avoir un controle sur le volume
    public static void player(String path) {
        Media hit = new Media(new File(path).toURI().toString());
        AudioClip mediaPlayer = new AudioClip(hit.getSource());
        mediaPlayer.play(0.1);
    }
}
