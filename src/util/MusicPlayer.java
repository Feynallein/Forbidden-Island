package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private Handler handler;
    private Clip clip;
    private boolean playing;

    public MusicPlayer(Handler handler, String path)  {
        this.handler = handler;
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File(path));
            try {
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        setVolume();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        playing = true;
    }

    public void stop() {
        clip.stop();
        playing = false;
    }

    public void setVolume(){
        float volume = Float.parseFloat(handler.getSettings().getProperty("volume"))/100;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    public int getFrame(){
        return clip.getFrameLength();
    }

    public boolean isPlaying(){
        return playing;
    }
}
