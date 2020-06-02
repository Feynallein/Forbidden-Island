package game.state;

import gfx.Assets;
import ui.AnimatedCard;
import ui.Button;
import ui.MultipleSpriteButtons;
import ui.ObjectManager;
import util.Handler;
import util.MusicPlayer;
import util.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlayerSelectionState extends State {
    private ObjectManager manager;
    private ArrayList<Color> color = new ArrayList<>();
    private ArrayList<Color> selectedColors = new ArrayList<>();

    public PlayerSelectionState(Handler handler) {
        super(handler);
        ArrayList<BufferedImage[]> sprites = new ArrayList<>();
        sprites.add(Assets.musicOn);
        sprites.add(Assets.musicOff);
        int beginning = handler.getSettings().getProperty("music").equals("on") ? 0 : 1;
        this.manager = new ObjectManager();
        initializeColor();
        handler.getMouseManager().setObjectManager(manager);
        int i = 0;
        while (!color.isEmpty()) {
            Color c = color.get(Handler.r.nextInt(color.size()));
            this.color.remove(c);
            int maxSize = 6;
            this.manager.addObject(new AnimatedCard((float) ((handler.getWidth() - maxSize * (Assets.dim + handler.getSpacing() * 2)) / 2 + i * (Assets.dim + handler.getSpacing() * 2)),
                    (float) ((handler.getHeight() - Assets.cardHeightDim) * 4 / 7), Assets.dim, Assets.cardHeightDim, Assets.animation, Utils.colorToSprite(c), this.handler, () -> {
                if (selectedColors.size() < 4 && !selectedColors.contains(c)) selectedColors.add(c);
            }));
            i++;
        }
        this.manager.addObject(new Button((float) ((handler.getWidth() - Assets.buttonDim * 2) / 2), (float) (handler.getHeight() * 6 / 7), Assets.buttonDim * 2, Assets.buttonDim, Assets.play, () -> {
            if (selectedColors.size() >= 2) {
                this.handler.setColors(selectedColors);
                this.handler.getMouseManager().setObjectManager(null);
                this.handler.getMusicPlayer().stop();
                this.handler.setMusicPlayer(new MusicPlayer(this.handler,"Resources/music/game.wav"));
                this.handler.getMusicPlayer().play();
                State.setState(new GameState(this.handler));
            }
        }));
        this.manager.addObject(new MultipleSpriteButtons((float) handler.getSpacing()*2 + Assets.playerDim, (float) handler.getSpacing(), Assets.playerDim, Assets.playerDim, new int[]{handler.getSpacing()*2 + Assets.playerDim, handler.getSpacing()*2 + Assets.playerDim},
                new int[]{Assets.playerDim, Assets.playerDim}, sprites, beginning, () -> {
            if(handler.getSettings().getProperty("music").equals("on")) {
                handler.getSettings().setProperty("music", "off");
            } else {
                handler.getSettings().setProperty("music", "on");
            }
        }));
    }

    /* Initialize the color array */

    private void initializeColor() {
        color.add(Color.GREEN);
        color.add(Color.RED);
        color.add(Color.YELLOW);
        color.add(Color.BLUE);
        color.add(Color.BLACK);
        color.add(Color.WHITE);
    }

    /* Update & Render */

    @Override
    public void update() {
        manager.update();
        if (selectedColors.size() >= 4) this.handler.setColors(selectedColors);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.selection, (handler.getWidth() - Assets.buttonDim * 8) / 2, handler.getHeight() / 3, Assets.buttonDim * 8, Assets.buttonDim, null);
        manager.render(g);
    }
}
