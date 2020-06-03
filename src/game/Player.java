package game;

import game.board.Island;
import gfx.Assets;
import gfx.Text;
import ui.Interacts;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Player implements Interacts {
    private Handler handler;
    public BufferedImage pawn, sprite, descSprite;
    public int[] position, inventory;
    private int action, order;
    public Color color;

    public Player(Handler handler, Island island, Color color, int order) {
        this.handler = handler;
        this.inventory = new int[6];
        //Arrays.fill(inventory, 1); //debugging purpose
        this.color = color;
        this.pawn = Utils.colorToPawn(this.color);
        this.sprite = Utils.colorToSprite(this.color);
        this.position = island.getStarterCases(this.color);
        this.descSprite = Utils.colorToDesc(this.color);
        this.order = order;
        this.action = 0;
    }

    /* Return the sum of the player's inventory */
    public int inventorySize() {
        int size = 0;
        for (int i : inventory) {
            size += i;
        }
        return size;
    }

    /* Update & Render */

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        /* Render the pawn */
        g.drawImage(pawn, position[0], position[1], Assets.playerDim, Assets.playerDim, null);
        /* Render the card on the left */
        g.drawImage(sprite, handler.getSpacing(),
                (handler.getHeight() - handler.getNumberOfPlayers() * (Assets.cardHeightDim + handler.getSpacing())) / 2 + order * (handler.getSpacing() + Assets.cardHeightDim),
                Assets.dim, Assets.cardHeightDim, null);
        renderInventory(g);
    }

    /* Render the inventory */
    private void renderInventory(Graphics g) {
        int xOffset = 0;
        FontMetrics fm = g.getFontMetrics(Assets.font45);
        for (int i = 0; i < inventory.length - 2; i++) {
            if (inventory[i] > 0) {
                g.drawImage(Assets.keys[i], handler.getSpacing() * 2 + Assets.dim + xOffset * (handler.getSpacing() + Assets.dim),
                        (handler.getHeight() - handler.getNumberOfPlayers() * (Assets.cardHeightDim + handler.getSpacing())) / 2 + order * (handler.getSpacing() + Assets.cardHeightDim),
                        Assets.dim, Assets.cardHeightDim, null);
                String txt = Integer.toString(inventory[i]);
                Text.drawString(g, txt,
                        handler.getSpacing() * 2 + Assets.dim * 3 / 2 + xOffset * (handler.getSpacing() + Assets.dim) - fm.stringWidth(txt) / 2,
                        (handler.getHeight() - handler.getNumberOfPlayers() * (Assets.cardHeightDim + handler.getSpacing())) / 2 + order * (handler.getSpacing() + Assets.cardHeightDim) + Assets.cardHeightDim,
                        false, Color.WHITE, Assets.font45);
                xOffset++;
            }
        }
    }

    /* Render the special Inventory */
    public void renderSpecialInventory(Graphics g) {
        for (int j = 0; j < inventory.length - 4; j++) {
            for (int i = 0; i < inventory[j + 4]; i++) {
                g.drawImage(Assets.specialCards[j], handler.getWidth() - (3 + i) * handler.getSpacing() - (i + 1) * Assets.dim,
                        Assets.dim * 4 + j * Assets.cardHeightDim + (j + 3) * handler.getSpacing(), Assets.dim, Assets.cardHeightDim, null);
            }
        }
    }

    /* Render the description */
    public void renderDescription(Graphics g) {
        g.drawImage(descSprite, handler.getWidth() - 5 * Assets.dim - handler.getSpacing() * 3, handler.getSpacing() * 2, 5 * Assets.dim, 4 * Assets.dim, null);
    }


    /* Mouse manager */

    @Override
    public void onMouseClicked(MouseEvent e) {

    }

    @Override
    public void onMousePressed(MouseEvent e) {

    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    @Override
    public void onMouseMove(MouseEvent e) {

    }

    /* Getters & Setters */

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
        if (i < inventory.length && i >= 0) inventory[i]++;
    }

    public void delInventory(int i) {
        if (i < inventory.length && i >= 0) inventory[i]--;
    }

    public void addSpecialInventory(int i) {
        if (i < inventory.length - 4 && i >= 0) inventory[i + 4]++;
    }

    public int getSpecialInventory(int i) {
        if (i < inventory.length - 4 && i >= 0) return inventory[i + 4];
        else return -1;
    }

    /* To String */
    @Override
    public String toString() {
        return Utils.colorToString(this.color);
    }
}