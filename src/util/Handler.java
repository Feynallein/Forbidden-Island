package util;

import gameCommons.Game;
import gameCommons.state.GameState;
import gfx.Assets;
import gfx.Display;
import ui.ObjectManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Handler {
    private Properties settings;
    private Game game;
    private ArrayList<Integer> takenNumbers = new ArrayList<>();
    public int death = 0;
    public Color color = new Color(0xCC7832);
    public int artifact = 0;
    public static Random r = new Random();
    private ObjectManager manager;
    private ArrayList<Color> colors = new ArrayList<>();
    private GameState gamestate;
    private Display display;

    public Handler(Properties p) {
        this.settings = p;
        /* debugging */
        colors.add(Color.GREEN);
        colors.add(Color.WHITE);
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Display getDisplay() {
        return display;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getWidth() {
        return Integer.parseInt(settings.getProperty("width"));
    }

    public int getHeight() {
        return Integer.parseInt(settings.getProperty("height"));
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public Game getGame() {
        return game;
    }

    public int getPixelByCase() {
        return Assets.dim * 4 / 3;
    } /* Pixel by case = 128 */

    public int getSpacing() {
        return 10;
    }

    public int getIslandLength() {
        return 6;
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

    public int getNumberOfPlayers() {
        return colors.size();
    }

    public ObjectManager getUiManager() {
        return manager;
    }

    public void saveObjectManager(ObjectManager m) {
        manager = m;
    }

    public ArrayList<Color> getColorArray() {
        return colors;
    }

    public void setColors(ArrayList<Color> c) {
        colors = c;
    }

    public void saveGameState(GameState state) {
        gamestate = state;
    }

    public GameState getSavedGameState() {
        return gamestate;
    }

    public void resetTakenNumbers() {
        takenNumbers = new ArrayList<>();
    }

    public Properties getSettings() {
        return settings;
    }
}