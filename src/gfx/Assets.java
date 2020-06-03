package gfx;

import util.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets {
    public static int dim = 96, playerDim = dim / 3, cardHeightDim = dim * 5 / 3, buttonDim = playerDim * 2;
    /* -> dim = 96, playerDim = 32, cardHeightDim = 160, buttonDim = 64 */
    public static BufferedImage[] turn, board, pawn, player, keys, artifacts, specialCards, deck, animation, gauge, playerDescription, pause, returned,
            go, quit, resume, halo, play, mainMenu, settings, newGame, credits, restart, fullscreen, windowed,
            res800x600, res1280x720, res1600x1200, res1920x1080, musicOn, musicOff, volume0, volume10, volume20, volume30, volume40, volume50, volume60, volume70, volume80, volume90, volume100;

    public static BufferedImage cardHalo, selection, pauseIndicator, cardsBack, floodedBg, selectionBg, menuTitle, menuBg;

    public static Font font20, font45;

    private static final String path = "/textures/";

    private static final String[] paths = new String[]{path + "0.png", path + "10.png",
            path + "20.png", path + "30.png", path + "40.png", path + "50.png", path + "60.png", path + "70.png", path + "80.png", path + "90.png", path + "100.png",};

    public static ArrayList<BufferedImage[]> musicOnOffArray = new ArrayList<>();
    public static ArrayList<BufferedImage[]> volumeIndicator = new ArrayList<>();

    public static void init(Handler handler) {
        int y;
        dim = 96;
        playerDim = dim / 3;
        cardHeightDim = dim * 5 / 3;
        buttonDim = playerDim * 2;

        floodedBg = ImageLoader.loadImage("/textures/Flooded_Bg.png");
        selectionBg = ImageLoader.loadImage("/textures/Menu_Bg.png");
        pauseIndicator = ImageLoader.loadImage("/textures/Pause_2.png");
        selection = ImageLoader.loadImage("/textures/PlayerSelection.png");
        cardHalo = ImageLoader.loadImage("/textures/cardHalo.png");
        menuTitle = ImageLoader.loadImage("/textures/title.png");
        menuBg = ImageLoader.loadImage("/textures/Background.png");

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
        SpriteSheet settingsSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Settings.png"));
        SpriteSheet creditsSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Credits.png"));
        SpriteSheet newGameSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/New_Game.png"));
        SpriteSheet restartSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Restart.png"));
        SpriteSheet fullscreenSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Bordless_Fullscreen.png"));
        SpriteSheet windowedSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Windowed.png"));
        SpriteSheet res800x600SpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/800x600.png"));
        SpriteSheet res1280x720SpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/1280x720.png"));
        SpriteSheet res1600x1200SpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/1600x1200.png"));
        SpriteSheet res1920x1080SpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/1920x1080.png"));
        SpriteSheet musicOnSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Music_On_Logo.png"));
        SpriteSheet musicOffSpriteSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Music_Off_Logo.png"));
        SpriteSheet volume0SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[0]));
        SpriteSheet volume10SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[1]));
        SpriteSheet volume20SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[2]));
        SpriteSheet volume30SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[3]));
        SpriteSheet volume40SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[4]));
        SpriteSheet volume50SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[5]));
        SpriteSheet volume60SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[6]));
        SpriteSheet volume70SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[7]));
        SpriteSheet volume80SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[8]));
        SpriteSheet volume90SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[9]));
        SpriteSheet volume100SpriteSheet = new SpriteSheet(ImageLoader.loadImage(paths[10]));

        deck = new BufferedImage[28];
        board = new BufferedImage[24];
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
        settings = new BufferedImage[3];
        newGame = new BufferedImage[3];
        credits = new BufferedImage[3];
        restart = new BufferedImage[3];
        windowed = new BufferedImage[3];
        fullscreen = new BufferedImage[3];
        res800x600 = new BufferedImage[3];
        res1280x720 = new BufferedImage[3];
        res1600x1200 = new BufferedImage[3];
        res1920x1080 = new BufferedImage[3];
        musicOff = new BufferedImage[3];
        musicOn = new BufferedImage[3];
        volume0 = new BufferedImage[3];
        volume10 = new BufferedImage[3];
        volume20 = new BufferedImage[3];
        volume30 = new BufferedImage[3];
        volume40 = new BufferedImage[3];
        volume50 = new BufferedImage[3];
        volume60 = new BufferedImage[3];
        volume70 = new BufferedImage[3];
        volume80 = new BufferedImage[3];
        volume90 = new BufferedImage[3];
        volume100 = new BufferedImage[3];
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
            playerDescription[i] = descriptionSpriteSheet.crop((i % 3) * 5 * dim, (i / 3) * 4 * dim, 5 * dim, 4 * dim);
        }

        for (int i = 0; i < keys.length; i++) { //length == 4
            keys[i] = keysSpriteSheet.crop(i * dim, 0, dim, 160);
            artifacts[i] = artifactsSpriteSheet.crop(i * (dim + 32), 0, dim + 32, dim + 32);
        }

        for (int i = 0; i < gauge.length; i++) { //length == 10
            gauge[i] = gaugeSpriteSheet.crop(i * 15, 0, 15, cardHeightDim);
        }

        for (int i = 0; i < halo.length; i++) { //length == 2
            halo[i] = haloSpriteSheet.crop(i * playerDim, 0, playerDim, playerDim);
        }

        for (int i = 0; i < specialCards.length; i++) { // length == 3
            specialCards[i] = specialCardsSpriteSheet.crop(i * dim, 0, dim, dim + dim * 2 / 3);
            pause[i] = pauseSpriteSheet.crop(i * playerDim, 0, playerDim, playerDim);
            go[i] = goSpriteSheet.crop(2 * playerDim * i, 0, 2 * playerDim, 2 * playerDim);
            returned[i] = returnedSpriteSheet.crop(2 * playerDim * i, 0, 2 * playerDim, 2 * playerDim);
            quit[i] = quitSpriteSheet.crop((dim + playerDim) * i, 0, dim + playerDim, playerDim * 2);
            resume[i] = resumeSpriteSheet.crop((cardHeightDim + playerDim) * i, 0, (cardHeightDim + playerDim), playerDim * 2);
            turn[i] = endTurnButtonSheet.crop((3 * dim + buttonDim) * i, 0, (3 * dim + 2 * playerDim), playerDim * 2);
            play[i] = playSpriteSheet.crop(i * buttonDim * 2, 0, 2 * buttonDim, buttonDim);
            mainMenu[i] = mainMenuSpriteSheet.crop(i * dim * 3, 0, dim * 3, buttonDim);
            settings[i] = settingsSpriteSheet.crop(i * buttonDim * 4, 0, buttonDim * 4, buttonDim);
            newGame[i] = newGameSpriteSheet.crop(i * buttonDim * 4, 0, buttonDim * 4, buttonDim);
            credits[i] = creditsSpriteSheet.crop((cardHeightDim + playerDim) * i, 0, cardHeightDim + playerDim, buttonDim);
            restart[i] = restartSpriteSheet.crop(i * 7 * playerDim, 0, 7 * playerDim, buttonDim);
            windowed[i] = windowedSpriteSheet.crop(i * 4 * buttonDim, 0, 4 * buttonDim, buttonDim);
            fullscreen[i] = fullscreenSpriteSheet.crop(i * playerDim * 19, 0, 19 * playerDim, buttonDim);
            res800x600[i] = res800x600SpriteSheet.crop(i * dim * 3, 0, dim * 3, buttonDim);
            res1280x720[i] = res1280x720SpriteSheet.crop(i * buttonDim * 5, 0, buttonDim * 5, buttonDim);
            res1600x1200[i] = res1600x1200SpriteSheet.crop(i * 11 * playerDim, 0, 11 * playerDim, buttonDim);
            res1920x1080[i] = res1920x1080SpriteSheet.crop(i * 11 * playerDim, 0, 11 * playerDim, buttonDim);
            musicOn[i] = musicOnSpriteSheet.crop(i * playerDim, 0, playerDim, playerDim);
            musicOff[i] = musicOffSpriteSheet.crop(i * playerDim, 0, playerDim, playerDim);
            volume0[i] = volume0SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume10[i] = volume10SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume20[i] = volume20SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume30[i] = volume30SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume40[i] = volume40SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume50[i] = volume50SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume60[i] = volume60SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume70[i] = volume70SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume80[i] = volume80SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume90[i] = volume90SpriteSheet.crop(i * dim, 0, dim, buttonDim);
            volume100[i] = volume100SpriteSheet.crop(i * (dim + playerDim), 0, dim + playerDim, buttonDim);

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

        int width = Integer.parseInt(handler.getSettings().getProperty("width"));
        int height = Integer.parseInt(handler.getSettings().getProperty("height"));

        if (width == 1600 && height == 1200) dim = buttonDim;

        if (width >= 1920 && height >= 1080) dim = 96;
        else if (width >= 1280 && height >= 720) dim = 96 / 2;
        else if (width >= 800 && height >= 600) dim = 96 / 3;

        playerDim = dim / 3;
        cardHeightDim = dim * 5 / 3;
        buttonDim = dim * 2 / 3;
        font20 = FontLoader.loadFont("Resources/fonts/manaspc.ttf", dim / 5);
        font45 = FontLoader.loadFont("Resources/fonts/manaspc.ttf", dim / 2);

        musicOnOffArray.add(musicOn);
        musicOnOffArray.add(musicOff);

        volumeIndicator.add(volume0);
        volumeIndicator.add(volume10);
        volumeIndicator.add(volume20);
        volumeIndicator.add(volume30);
        volumeIndicator.add(volume40);
        volumeIndicator.add(volume50);
        volumeIndicator.add(volume60);
        volumeIndicator.add(volume70);
        volumeIndicator.add(volume80);
        volumeIndicator.add(volume90);
        volumeIndicator.add(volume100);
    }
}