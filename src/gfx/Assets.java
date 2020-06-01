package gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {
    public static int dim = 96, playerDim = dim / 3, cardHeightDim = dim * 5 / 3, buttonDim = playerDim*2;
    /* -> dim = 96, playerDim = 32, cardHeightDim = 160, buttonDim = 64, pixelByCase = 128 */
    public static BufferedImage[] turn;
    public static BufferedImage[] board;
    public static BufferedImage[] pawn;
    public static BufferedImage[] player;
    public static BufferedImage[] keys;
    public static BufferedImage[] artifacts;
    public static BufferedImage[] specialCards;
    public static BufferedImage[] deck;
    public static BufferedImage[] animation;
    public static BufferedImage[] gauge;
    public static BufferedImage[] playerDescription;
    public static BufferedImage[] pause;
    public static BufferedImage[] returned;
    public static BufferedImage[] go;
    public static BufferedImage[] quit;
    public static BufferedImage[] resume;
    public static BufferedImage[] halo;
    public static BufferedImage[] play;
    public static BufferedImage[] mainMenu;
    public static BufferedImage selection;
    public static BufferedImage pauseIndicator;
    public static BufferedImage cardsBack;
    public static BufferedImage floodedBg;
    public static BufferedImage menuBg;
    public static Font font20, font45;

    public static void init() {
        int y;

        font20 = FontLoader.loadFont("Resources/fonts/manaspc.ttf", 20);
        font45 = FontLoader.loadFont("Resources/fonts/manaspc.ttf", 45);

        floodedBg = ImageLoader.loadImage("/textures/Flooded_Bg.png");
        menuBg = ImageLoader.loadImage("/textures/Menu_Bg.png");
        pauseIndicator = ImageLoader.loadImage("/textures/Pause_2.png");
        selection = ImageLoader.loadImage("/textures/PlayerSelection.png");

        SpriteSheet endTurnButtonSheet = new SpriteSheet(ImageLoader.loadImage("/textures/End_of_Turn.png"));
        SpriteSheet boardSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Board.png"));
        SpriteSheet pawnSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Pions.png"));
        SpriteSheet playerSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Aventurers.png"));
        SpriteSheet keysSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Artefacts.png"));
        SpriteSheet artifactsSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Artefacts_solo.png"));
        SpriteSheet specialCardsSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Special_Cards.png"));
        SpriteSheet animationSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/animation.png"));
        SpriteSheet gaugeSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Jauge.png"));
        SpriteSheet descriptionSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Description_Area.png"));
        SpriteSheet pauseSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Pause.png"));
        SpriteSheet goSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Go.png"));
        SpriteSheet returnedSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Go_Back.png"));
        SpriteSheet resumeSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Resume.png"));
        SpriteSheet quitSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Quit.png"));
        SpriteSheet haloSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Halo.png"));
        SpriteSheet playSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Play.png"));
        SpriteSheet mainMenuSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Main_Menu.png"));


        board = new BufferedImage[24];
        deck = new BufferedImage[28];
        gauge = new BufferedImage[10];
        keys = new BufferedImage[4];
        artifacts = new BufferedImage[4];
        pawn = new BufferedImage[6];
        player = new BufferedImage[6];
        playerDescription = new BufferedImage[6];
        animation = new BufferedImage[6];
        turn = new BufferedImage[3];
        specialCards = new BufferedImage[3];
        pause = new BufferedImage[3];
        returned = new BufferedImage[3];
        go = new BufferedImage[3];
        quit = new BufferedImage[3];
        resume = new BufferedImage[3];
        play = new BufferedImage[3];
        mainMenu = new BufferedImage[3];

        halo = new BufferedImage[2];

        for (int i = 0; i < board.length; i++) { //length == 24
            board[i] = boardSpriteSheet.crop((i % 6) * dim, (i / 6) * dim, dim, dim);
        }

        for (int i = 0; i < pawn.length; i++) { //length == 6
            if (i < 3) y = 0;
            else y = 1;
            pawn[i] = pawnSpriteSheet.crop((i % 3) * playerDim, y * playerDim, playerDim, playerDim);
            player[i] = playerSpriteSheet.crop(i * dim, 0, dim, dim + dim * 2 / 3);
            animation[i] = animationSpriteSheet.crop(i * dim, 0, dim, dim + dim * 2 / 3);
            playerDescription[i] = descriptionSpriteSheet.crop((i%3)*5*dim, (i/3)*4*dim, 5*dim, 4*dim);
        }

        for (int i = 0; i < keys.length; i++) { //length == 4
            keys[i] = keysSpriteSheet.crop(i * dim, 0, dim, 160);
            artifacts[i] = artifactsSpriteSheet.crop(i * (dim + 32), 0, dim + 32, dim + 32);
        }

        for(int i = 0; i < gauge.length; i++){ //length == 10
            gauge[i] = gaugeSpriteSheet.crop(i*15, 0, 15, cardHeightDim);
        }

        for(int i = 0; i < halo.length; i++){ //length == 2
            halo[i] = haloSpriteSheet.crop(i*playerDim, 0, playerDim, playerDim);
        }

        for (int i = 0; i < specialCards.length; i++) { // length == 3
            specialCards[i] = specialCardsSpriteSheet.crop(i * dim, 0, dim, dim + dim * 2 / 3);
            pause[i] = pauseSpriteSheet.crop(i*playerDim, 0, playerDim, playerDim);
            go[i] = goSpriteSheet.crop(2*playerDim*i, 0, 2*playerDim, 2*playerDim);
            returned[i] = returnedSpriteSheet.crop(2*playerDim*i, 0, 2*playerDim, 2*playerDim);
            quit[i] = quitSpriteSheet.crop((dim+playerDim)*i, 0, dim+playerDim, playerDim*2);
            resume[i] = resumeSpriteSheet.crop((cardHeightDim+playerDim)*i, 0, (cardHeightDim+playerDim), playerDim*2);
            turn[i] = endTurnButtonSheet.crop((3*dim+2*playerDim)*i, 0, (3*dim+2*playerDim), playerDim*2);
            play[i] = playSpriteSheet.crop(i*buttonDim*2, 0, 2*buttonDim, buttonDim);
            mainMenu[i] = mainMenuSpriteSheet.crop(i*dim*3, 0, dim*3, buttonDim);

            /* filling half of the deck */
            deck[i] = specialCards[0];
            deck[i + 3] = specialCards[2];
            if (i < 2) deck[i + 6] = specialCards[1];
        }

        /* filling the other half of the deck */
        for (int i = 8; i <= 12; i++) {
            deck[i] = keys[0];
            deck[i + 5] = keys[1];
            deck[i + 10] = keys[2];
            deck[i + 15] = keys[3];
        }

        cardsBack = animation[0];
    }
}