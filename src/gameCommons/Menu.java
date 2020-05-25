package gameCommons;

import gfx.Assets;
import gfx.Text;
import ui.Observer;
import util.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Menu implements Observer {
    private Handler handler;
    public boolean active = false;
    private int x, y; //precise location where we clicked
    private Case clickedCase;
    private Player player;
    private Island island;
    private Color[] color = new Color[]{Color.WHITE, Color.WHITE, Color.WHITE};
    private boolean[] hovering = new boolean[3];
    private int textWidth = 80, textHeight = 30; //arbitraire
    int i;

    public Menu(Handler handler, Island island) {
        this.handler = handler;
        this.island = island;
        this.i = 0;
    }



    /* UPDATE & RENDER */

    @Override
    public void update() {
        if (hovering[0]) color[0] = Color.RED;
        else color[0] = Color.WHITE;

        if (hovering[1]) color[1] = Color.RED;
        else color[1] = Color.WHITE;

        if (hovering[2]) color[2] = Color.RED;
        else color[2] = Color.WHITE;
    }

    @Override
    public void render(Graphics g) {
        i = 0;
        if (!active)
            return;
        if (clickedCase.getState() != 2 && !player.getAction(0)) {
            g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textHeight, textWidth, textHeight, null);
            Text.drawString(g, "Move", x + 1, y + 1 + 25 + i * textHeight, false, color[0], Assets.font20); //a changer le 25
            i++;
        }
        if (clickedCase.getState() == 1 && !player.getAction(1)) {
            g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textHeight, textWidth, textHeight, null);
            Text.drawString(g, "Thirst", x + 1, y + 1 + 25 + i * textHeight, false, color[1], Assets.font20); //a changer le 25
            i++;
        }
        if (Arrays.equals(player.position, new int[]{clickedCase.x, clickedCase.y}) && clickedCase.getState() == 0 && !player.getAction(2)) {
            String str;
            if (clickedCase.isArtifact) {
                str = "Gather";
            } else {
                str = "Dig";
            }
            g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textHeight, 80, textHeight, null);
            Text.drawString(g, str, x + 1, y + 1 + 25 + i * textHeight, false, color[2], Assets.font20); //a changer le 25
            i++;
        }
        if (i == 0) active = false;
    }


    /* MOUSE MANAGER */

    @Override
    public void onMouseClicked(MouseEvent e) {
        if (!active)
            return;

        if (e.getX() < x + 1 || e.getX() > x + 1 + textWidth || e.getY() < y + 1 || e.getY() > y + 1 + textHeight * i) {
            active = false;
        } else {
            int k = 0;
            Rectangle depla = new Rectangle(0, 0, 0, 0);
            Rectangle thirst = new Rectangle(0, 0, 0, 0);
            Rectangle dig = new Rectangle(0, 0, 0, 0);
            if (clickedCase.getState() != 2 && !player.getAction(0)) {
                depla = new Rectangle(x + 1, y + 1 + k * textHeight, textWidth, textHeight);
                k++;
            }
            if (clickedCase.getState() == 1 && !player.getAction(1)) {
                thirst = new Rectangle(x + 1, y + 1 + k * textHeight, textWidth, textHeight);
                k++;
            }
            if (Arrays.equals(player.position, new int[]{clickedCase.x, clickedCase.y}) && clickedCase.getState() == 0 && !player.getAction(3)) {
                dig = new Rectangle(x + 1, y + 1 + k * textHeight, textWidth, textHeight);
            }
            if (depla.contains(e.getX(), e.getY())) {
                island.movePlayer(clickedCase);
                this.active = false;
            } else if (thirst.contains(e.getX(), e.getY())) {
                island.thirstCase(clickedCase);
                this.active = false;
            } else if (dig.contains(e.getX(), e.getY())) {
                if (clickedCase.isArtifact) {
                    if (player.inventory[clickedCase.artiValue] >= 1) { //changer le 1 en 4
                        island.gatherArtifact(clickedCase.artiValue);
                    }
                } else {
                    int randomNum = Island.r.nextInt(3);
                    if (randomNum == 0) {
                        island.floodCase(clickedCase);
                    } else if (randomNum == 1) {
                        island.addInventory(Island.r.nextInt(4));
                    }
                }
            }
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    @Override
    public void onMousePressed(MouseEvent e) {

    }

    @Override
    public void onMouseMove(MouseEvent e) {
//        hovering[0] = new Rectangle(x + 1, y + 1 + 25, 80, 27).contains(e.getX(), e.getY());
//        hovering[1] = new Rectangle(x + 1, y + 1 + 25, 80, 27).contains(e.getX(), e.getY());
//        hovering[2] = new Rectangle(x + 1, y + 1 + 25, 80, 27).contains(e.getX(), e.getY());
    }


    /* GETTERS & SETTERS */

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isActive() {
        return active;
    }

    public void setClickedCase(Case clickedCase) {
        this.clickedCase = clickedCase;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
