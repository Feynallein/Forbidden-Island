package gameCommons;

import gfx.Assets;
import gfx.Text;
import ui.Observer;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Player implements Observer {
    private Handler handler;
    public BufferedImage pawn;
    public int[] position;
    private boolean[] action;
    public Color color;
    private BufferedImage sprite;
    private int order;
    public int[] inventory;

    public Player(Handler handler, Island island, Color color, int order) {
        this.handler = handler;
        this.action = new boolean[3];
        Arrays.fill(action, false);
        this.inventory = new int[4];
        Arrays.fill(inventory, 1);
        this.color = color;
        this.pawn = Utils.colorToPawn(this.color);
        this.sprite = Utils.colorToSprite(this.color);
        this.position = island.getStarterCases(this.color);
        this.order = order;
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


    /* UPDATE & RENDER */

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(pawn, position[0], position[1], null);
        g.drawImage(sprite, 1, (handler.getHeight() - 6 * (Assets.dim + Assets.dim * 2 / 3) - handler.getSpacing() * 6) / 2 + order * handler.getSpacing() + order * (Assets.dim + Assets.dim * 2 / 3), null);
        //pour corriger le spacing on doit multiplier le nb total (ici nb joueur = 6) * spacing (*2 car ici le spacing est double)
        renderInventory(g);
    }

    private void renderInventory(Graphics g) { //simplifications mathématiques possibles ?
        int xOffset = 0;
        FontMetrics fm = g.getFontMetrics(Assets.font45);
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] > 0) {
                g.drawImage(Assets.keys[i], Assets.dim + (xOffset + 1) * handler.getSpacing() + xOffset * Assets.dim,
                        (handler.getHeight() - 6 * (Assets.dim + Assets.dim * 2 / 3) - handler.getSpacing() * 6) / 2 + order * handler.getSpacing() + order * (Assets.dim + Assets.dim * 2 / 3), null);
                Text.drawString(g, Integer.toString(inventory[i]),
                        Assets.dim + xOffset * handler.getSpacing() + xOffset * Assets.dim + (Assets.dim + (inventory.length + 1) * handler.getSpacing()) / 2 - fm.stringWidth(Integer.toString(inventory[i])),
                        (handler.getHeight() - 6 * (Assets.dim + Assets.dim * 2 / 3) - handler.getSpacing() * 6) / 2 + order * handler.getSpacing() + (order + 1) * (Assets.dim + Assets.dim * 2 / 3),
                        false, Color.WHITE, Assets.font45);
                xOffset++;
            }
        }
    }



    /* MOUSE MANAGER */

    @Override
    public void onMouseClicked(MouseEvent e) {

    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    @Override
    public void onMousePressed(MouseEvent e) {

    }

    @Override
    public void onMouseMove(MouseEvent e) {

    }



    /* GETTERS & SETTERS */

    public boolean getAction(int i) {
        if (i < action.length && i >= 0) return action[i];
        return false;
    }

    public void addAction(int i) {
        if (i < action.length && i >= 0) action[i] = true;
    }

    public void resetAction() {
        Arrays.fill(action, false);
    }

    public void addInventory(int i) {
        if (i < inventory.length) inventory[i]++;
    }

    public void delInventory(int i) {
        if (i < inventory.length) inventory[i]--;
    }

    public int getInventory(int i){
        if(i >= 0 && i < inventory.length) return inventory[i];
        return 0;
    }
}
