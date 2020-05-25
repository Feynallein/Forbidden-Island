package gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {
    public static final int dim = 96, playerDim = dim/3;
    public static BufferedImage[] turn;
    public static BufferedImage[] board;
    public static BufferedImage[] pawn;
    public static BufferedImage[] player;
    public static BufferedImage[] keys;
    public static BufferedImage[] artifacts;
    public static BufferedImage floodedBg;
    public static BufferedImage menuBg;
    public static Font font20, font45;

    public static BufferedImage temp;

    public static void init(){
        int y;
        font20 = FontLoader.loadFont("Resources/fonts/manaspc.ttf", 20);
        font45 = FontLoader.loadFont("Resources/fonts/manaspc.ttf", 45);

        SpriteSheet endTurnButtonSheet = new SpriteSheet(ImageLoader.loadImage("/textures/end.png"));
        SpriteSheet boardSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Board.png"));
        SpriteSheet pawnSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Pions.png"));
        SpriteSheet playerSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Aventurers.png"));
        SpriteSheet keysSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Artefacts.png"));
        SpriteSheet artifactsSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Artefacts_solo.png"));

        turn = new BufferedImage[3];
        board = new BufferedImage[24];
        pawn = new BufferedImage[6];
        keys = new BufferedImage[4];
        player = new BufferedImage[6];
        artifacts = new BufferedImage[4];

        turn[0] = endTurnButtonSheet.crop(0, 0, 162, 26);
        turn[1] = endTurnButtonSheet.crop(0, 26, 162, 26);
        turn[2] = endTurnButtonSheet.crop(0, 52, 162, 26);

        for(int i = 0; i < board.length; i++){
            board[i] = boardSpriteSheet.crop((i%6)*dim, (i/6)*dim, dim, dim);
        }

        for(int i = 0; i < pawn.length; i++){
            if(i < 3) y = 0;
            else y = 1;
            pawn[i] = pawnSpriteSheet.crop((i%3)*playerDim, y*playerDim, playerDim, playerDim);
            player[i] = playerSpriteSheet.crop(i*dim, 0, dim, dim+dim*2/3);
        }

        for(int i = 0; i < keys.length; i++){
            keys[i] = keysSpriteSheet.crop(i*dim, 0, dim, 160);
            artifacts[i] = artifactsSpriteSheet.crop(i*(dim+32), 0, dim+32, dim+32);
        }

        floodedBg = ImageLoader.loadImage("/textures/Flooded_Bg.png");
        menuBg = ImageLoader.loadImage("/textures/Menu_Bg.png");

        temp = ImageLoader.loadImage("/textures/Untitled_Artwork.png");
    }
}