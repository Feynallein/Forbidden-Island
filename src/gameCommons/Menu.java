package gameCommons;

import gfx.Assets;
import gfx.Text;
import util.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu {
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
    public TradesMenu tradesMenu;
    Rectangle depla = new Rectangle(0, 0, 0, 0);
    Rectangle thirst = new Rectangle(0, 0, 0, 0);
    Rectangle dig = new Rectangle(0, 0, 0, 0);
    Rectangle trade = new Rectangle(0, 0, 0, 0);
    private ArrayList<Player> players;
    private boolean onCase;

    public Menu(Handler handler, Island island) {
        this.handler = handler;
        this.island = island;
        this.i = 0;
        this.tradesMenu = new TradesMenu(handler, island);
        players = new ArrayList<>();
    }



    /* UPDATE & RENDER */

    public void update() {
        if (!active)
            return;

        tradesMenu.update();
        players = island.playersOnTheCase(clickedCase);
        onCase = island.onCase(player, clickedCase);

        if (hovering[0]) color[0] = Color.RED;
        else color[0] = Color.WHITE;

        if (hovering[1]) color[1] = Color.RED;
        else color[1] = Color.WHITE;

        if (hovering[2]) color[2] = Color.RED;
        else color[2] = Color.WHITE;
    }

    public void render(Graphics g) {
        i = 0;
        if (!active)
            return;
        if(!tradesMenu.isActive()) {
            if (clickedCase.getState() != 2 && !player.getAction(0) && !onCase) {
                g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textHeight, textWidth, textHeight, null);
                Text.drawString(g, "Move", x + 1, y + 1 + 25 + i * textHeight, false, color[0], Assets.font20); //a changer le 25
                depla = new Rectangle(x + 1, y + 1 + i * textHeight, textWidth, textHeight);
                i++;
            }
            if (clickedCase.getState() == 1 && !player.getAction(1)) {
                g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textHeight, textWidth, textHeight, null);
                Text.drawString(g, "Thirst", x + 1, y + 1 + 25 + i * textHeight, false, color[1], Assets.font20); //a changer le 25
                thirst = new Rectangle(x + 1, y + 1 + i * textHeight, textWidth, textHeight);
                i++;
            }
            if (onCase && clickedCase.getState() != 2 && !player.getAction(2)) {
                String str;
                if (clickedCase.isArtifact) {
                    str = "Gather";
                } else {
                    str = "Dig";
                }
                g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textHeight, 80, textHeight, null);
                Text.drawString(g, str, x + 1, y + 1 + 25 + i * textHeight, false, color[2], Assets.font20); //a changer le 25
                dig = new Rectangle(x + 1, y + 1 + i * textHeight, textWidth, textHeight);
                i++;
            }
            if (onCase && !players.isEmpty()) {
                g.drawImage(Assets.menuBg, x + 1, y + 1 + i * textHeight, textWidth, textHeight, null);
                Text.drawString(g, "Trade", x + 1, y + 1 + 25 + i * textHeight, false, color[1], Assets.font20); //a changer le 25
                trade = new Rectangle(x + 1, y + 1 + i * textHeight, textWidth, textHeight);
                i++;
            }
            if (i == 0) active = false;
        }
        tradesMenu.render(g);
    }


    /* MOUSE MANAGER */

    public void onMouseClicked(MouseEvent e) {
        if (tradesMenu.isActive()) tradesMenu.onMouseClicked(e);

        else if (e.getX() < x + 1 || e.getX() > x + 1 + textWidth || e.getY() < y + 1 || e.getY() > y + 1 + textHeight * i) {
            active = false;
        } else {
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
            } else if (trade.contains(e.getX(), e.getY())){
                tradesMenu.setPlayers(players);
                tradesMenu.setActive(true);
            }
        }
    }

    public void onMouseMove(MouseEvent e) {
        if (tradesMenu.isActive()) tradesMenu.onMouseMove(e);
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
