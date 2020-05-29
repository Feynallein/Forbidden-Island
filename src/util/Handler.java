package util;

import gameCommons.Game;
import gfx.Assets;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
    private Game game;
    private ArrayList<Integer> takenNumbers = new ArrayList<>();
    public int death = 0;
    public Color color = new Color(0xCC7832);
    public int artifact = 0;

    public Handler(Game game) {
        this.game = game;
    }

    public int getWidth() {
        return game.getWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public Game getGame() {
        return game;
    }

    public int getPixelByCase() {
        return Assets.dim*4/3;
    }

    public int getSpacing() {
        return 10;
    }

    public int getIslandLength() {
        return this.game.getLength();
    }

    public void addNumber(int num) {
        this.takenNumbers.add(num);
    }

    public ArrayList<Integer> getTakenNumbers() {
        return this.takenNumbers;
    }

    public void setDeath(int i) {
        this.death = i;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void setArtifact(int i) {
        this.artifact = i;
    }

    public int getNumberOfPlayers(){
        return 4; //TODO : make this not stable
    }
}