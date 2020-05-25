package util;

import gameCommons.Initializer;
import gameCommons.Island;
import gfx.Assets;
import gfx.Display;
import state.State;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
    private Initializer init;
    private ArrayList<Integer> takenNumbers = new ArrayList<>();
    public int death = 0;
    public Color color = new Color(0xCC7832);
    public int artifact = 0;

    public Handler(Initializer game){ this.init = game; }

    public int getWidth(){ return init.getWidth(); }

    public int getHeight(){ return init.getHeight(); }

    public KeyManager getKeyManager(){ return init.getKeyManager(); }

    public MouseManager getMouseManager(){ return init.getMouseManager(); }

    public Initializer getInitializer() {
        return init;
    }

    public Display getDisplay(){return this.init.getGraphic();}

    public int getPixelByCase(){return 128;}

    public int getSpacing(){
        return 10;
    }

    public int getIslandLength() {
        return this.init.getLength();
    }

    public void addNumber(int num){
        this.takenNumbers.add(num);
    }

    public ArrayList<Integer> getTakenNumbers(){
        return this.takenNumbers;
    }

    public void setDeath(int i) {
        this.death = i;
    }

    public void setColor(Color c){
        this.color = c;
    }

    public void setArtifact(int i){
        this.artifact = i;
    }
}