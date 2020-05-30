package gameCommons.Board;

import gfx.Assets;
import ui.UiInteracter;
import util.Handler;
import util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Case implements UiInteracter {
    private Handler handler;
    private int state;
    public Rectangle bounds;
    public boolean isVisible;
    public int x, y;
    private boolean isHovered;
    private BufferedImage sprite;
    public Color color;
    public boolean isArtifact;
    public int artifactValue;

    public Case(Handler handler, int i, int j, int xOffset, int yOffset, Island island) {
        this.handler = handler;
        this.x = i * handler.getSpacing() + i * handler.getPixelByCase() + xOffset;
        this.y = j * handler.getSpacing() + j * handler.getPixelByCase() + yOffset;
        this.bounds = new Rectangle(x, y, handler.getPixelByCase(), handler.getPixelByCase());
        this.isVisible = (j != 0 && j != 5 && i != 0 && i != 5) || (j != 0 && j != 5 && j != 1 && j != 4) || (i != 0 && i != 1 && i != 4 && i != 5);
        this.state = this.isVisible ? 0 : 3;
        this.isHovered = false;
        if (isVisible) {
            int num = island.getBoardTile();
            this.sprite = Assets.board[num];
            this.color = Utils.getStarterColors(num);
            this.isArtifact = Utils.isArtifact(num);
            this.artifactValue = Utils.artifactToValue(num);
        }
    }

    public Case(Handler handler, int i, int j, int xOffset, int yOffset) {
        this.x = i * handler.getSpacing() + i * handler.getPixelByCase() + xOffset;
        this.y = j * handler.getSpacing() + j * handler.getPixelByCase() + yOffset;
    }


    /* UPDATE & RENDER */

    @Override
    public void update() {
        if (this.state == 2) this.isVisible = false;
    }

    @Override
    public void render(Graphics g) {
        if (this.isVisible) {
            g.drawImage(sprite, x, y, handler.getPixelByCase(), handler.getPixelByCase(), null);
            if (this.state == 1) {
                g.drawImage(Assets.floodedBg, x, y, handler.getPixelByCase(), handler.getPixelByCase(), null);
            }
            if (this.isHovered) {
                g.setColor(Color.BLACK);
                g.drawRect(x, y, handler.getPixelByCase(), handler.getPixelByCase());
            }
        }
    }

    @Override
    public String toString() {
        return x + " " + y;
    }


    /* MOUSE MANAGER */

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
        isHovered = bounds.contains(e.getX(), e.getY());
    }


    /* GETTERS AND SETTERS */

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
