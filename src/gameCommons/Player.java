package gameCommons;

import gameCommons.Board.Island;
import gfx.Assets;
import gfx.Text;
import ui.UiInteracter;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Player implements UiInteracter {
    private Handler handler;
    public BufferedImage pawn;
    public int[] position;
    private int action;
    public Color color;
    private BufferedImage sprite;
    private int order;
    public int[] inventory;
    public int[] specialInventory;

    public Player(Handler handler, Island island, Color color, int order) {
        this.handler = handler;
        this.inventory = new int[4];
        this.specialInventory = new int[2];
        Arrays.fill(inventory, 0); //set to 0
        Arrays.fill(specialInventory, 0); //set to 0
        this.color = color;
        this.pawn = Utils.colorToPawn(this.color);
        this.sprite = Utils.colorToSprite(this.color);
        this.position = island.getStarterCases(this.color);
        this.order = order;
        this.action = 0;
    }

    public boolean nearPlayer(MouseEvent e) {
        Rectangle horizontalPos = new Rectangle(position[0] - handler.getPixelByCase(), position[1], 3 * handler.getPixelByCase(), handler.getPixelByCase());
        Rectangle verticalPos = new Rectangle(position[0], position[1] - handler.getPixelByCase(), handler.getPixelByCase(), 3 * handler.getPixelByCase());
        return horizontalPos.contains(e.getX(), e.getY()) || verticalPos.contains(e.getX(), e.getY());
    }

    @Override
    public String toString() {
        return Utils.colorToString(this.color);
    }

    public int inventorySize(){
        int size = 0;
        for(int i : inventory){
            size+=i;
        }
        for(int i : specialInventory){
            size+=i;
        }
        return size;
    }


    /* UPDATE & RENDER */

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(pawn, position[0], position[1], null);
        g.drawImage(sprite, handler.getSpacing(), (handler.getHeight() - 6 * (Assets.dim + Assets.dim * 2 / 3) - handler.getSpacing() * 6) / 2 + order * handler.getSpacing() + order * (Assets.dim + Assets.dim * 2 / 3), null);
        //pour corriger le spacing on doit multiplier le nb total (ici nb joueur = 6) * spacing (*2 car ici le spacing est double)
        renderInventory(g);
    }

    private void renderInventory(Graphics g) { //simplifications mathématiques possibles ?
        int xOffset = 0;
        FontMetrics fm = g.getFontMetrics(Assets.font45);
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] > 0) {
                g.drawImage(Assets.keys[i], handler.getSpacing()*2 + Assets.dim + xOffset * handler.getSpacing() + xOffset * Assets.dim,
                        (handler.getHeight() - 6 * (Assets.dim + Assets.dim * 2 / 3) - handler.getSpacing() * 6) / 2 + order * handler.getSpacing() + order * (Assets.dim + Assets.dim * 2 / 3), null);
                Text.drawString(g, Integer.toString(inventory[i]),
                        handler.getSpacing() + Assets.dim + xOffset * handler.getSpacing() + xOffset * Assets.dim + (Assets.dim + (inventory.length + 1) * handler.getSpacing()) / 2 - fm.stringWidth(Integer.toString(inventory[i])),
                        (handler.getHeight() - 6 * (Assets.dim + Assets.dim * 2 / 3) - handler.getSpacing() * 6) / 2 + order * handler.getSpacing() + (order + 1) * (Assets.dim + Assets.dim * 2 / 3),
                        false, Color.WHITE, Assets.font45);
                xOffset++;
            }
        }
    }

    public void renderSpecialInventory(Graphics g){
        for(int i = 0; i < specialInventory[0]; i++){
            g.drawImage(Assets.specialCards[0], handler.getWidth() - 3*handler.getSpacing() - (i+1)*Assets.dim - i*handler.getSpacing(), 32 + handler.getHeight()/3 + handler.getSpacing(), null);
        }
        for(int i = 0; i < specialInventory[1]; i++){
            g.drawImage(Assets.specialCards[1], handler.getWidth() - 3*handler.getSpacing() - (i+1)*Assets.dim - i*handler.getSpacing(), 32 + handler.getHeight()/3 + handler.getSpacing()*2 + Assets.dim+Assets.dim*2/3, null);
        }
    }


    /* MOUSE MANAGER */

    @Override
    public void onMouseClicked(MouseEvent e) {

    }

    @Override
    public void onMouseMove(MouseEvent e) {

    }



    /* GETTERS & SETTERS */

    public int getAction() {
        return action;
    }

    public void addAction() {
        action++;
    }

    public void resetAction() {
        action = 0;
    }

    public void addInventory(int i) {
        if (i < inventory.length) inventory[i]++;
    }

    public void delInventory(int i) {
        if (i < inventory.length && i >= 0) inventory[i]--;
    }

    public void addSpecialInventory(int i){
        if(i < specialInventory.length && i >= 0) specialInventory[i]++;
    }

}
