package util;

import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Utils {
    /* Convert number of the starer to a color */
    public static Color getStarterColors(int spriteNumber) {
        switch (spriteNumber) {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.WHITE;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.RED;
            default:
                return null;
        }
    }

    /* Convert color to a string */
    public static String colorToString(Color c) {
        if (c == Color.BLACK) return "black";
        else if (c == Color.BLUE) return "blue";
        else if (c == Color.RED) return "red";
        else if (c == Color.GREEN) return "green";
        else if (c == Color.WHITE) return "white";
        else if (c == Color.YELLOW) return "yellow";
        else if (c.equals(new Color(0xCC7832))) return "couleur de debugging";
        else {
            System.out.println("aucune couleur correspondante");
            System.exit(-1);
            return null;
        }
    }

    /* Convert a color to the equivalent pawn's buffered image */
    public static BufferedImage colorToPawn(Color c) {
        if (c == Color.BLACK) return Assets.pawn[4];
        else if (c == Color.BLUE) return Assets.pawn[3];
        else if (c == Color.RED) return Assets.pawn[5];
        else if (c == Color.GREEN) return Assets.pawn[1];
        else if (c == Color.WHITE) return Assets.pawn[2];
        else if (c == Color.YELLOW) return Assets.pawn[0];
        else {
            System.out.println("aucune couleur correspondante");
            System.exit(-1);
            return null;
        }
    }

    /* Convert a color to the equivalent card's buffered image */
    public static BufferedImage colorToSprite(Color c) {
        if (c == Color.BLACK) return Assets.player[4];
        else if (c == Color.BLUE) return Assets.player[2];
        else if (c == Color.RED) return Assets.player[3];
        else if (c == Color.GREEN) return Assets.player[0];
        else if (c == Color.WHITE) return Assets.player[5];
        else if (c == Color.YELLOW) return Assets.player[1];
        else {
            System.out.println("aucune couleur correspondante");
            System.exit(-1);
            return null;
        }
    }

    /* Convert the tile's number in the array to true if it's an artifact */
    public static boolean isArtifact(int num) {
        int[] areArtifacts = {16, 17, 18, 19, 20, 21, 22, 23};
        for (int i : areArtifacts) {
            if (num == i) return true;
        }
        return false;
    }

    /* Convert the tile's number in the array to the type of artifact */
    public static int artifactToValue(int num) {
        if (num == 16 || num == 17) return 1;
        else if (num == 18 || num == 19) return 0;
        else if (num == 20 || num == 21) return 3;
        else if (num == 22 || num == 23) return 2;
        else return -1;
    }

    /* Convert an artifact type to an X location */
    public static int numToX(int num) {
        switch (num) {
            case 0:
            case 1:
                return 0;
            case 2:
            case 3:
                return 5;
            default:
                System.exit(-1);
                return -1;
        }
    }

    /* Convert an artifact type to an Y location */
    public static int numToY(int num) {
        switch (num) {
            case 0:
            case 2:
                return 1;
            case 1:
            case 3:
                return 4;
            default:
                System.exit(-1);
                return -1;
        }
    }

    /* Return true if an array is filled with the same value */
    public static boolean allEquals(int[] array, int value) {
        for (int i : array) {
            if (i != value) return false;
        }
        return true;
    }

    /* Convert the artifact type to a string of the same type */
    public static String artifactValueToString(int i) {
        switch (i) {
            case 0:
                return "gardens";
            case 1:
                return "palaces";
            case 2:
                return "caves";
            case 3:
                return "temples";
            default:
                System.exit(-1);
                return null;
        }
    }

    /* Convert a value in the deck array to a type */
    public static String valueToString(int value) {
        if (value >= 0 && value <= 2) return "helicopter";
        else if (value >= 3 && value <= 5) return "flooded";
        else if (value >= 6 && value <= 7) return "sandbag";
        else if (value >= 8 && value <= 12) return "18";
        else if (value >= 13 && value <= 17) return "16";
        else if (value >= 18 && value <= 22) return "22";
        else if (value >= 23 && value <= 28) return "20";
        else return "";
    }

    /* Return a value from the inventory to a deck type */
    public static String invValueToString(int value) {
        if (value == 0) return "18";
        else if (value == 1) return "16";
        else if (value == 2) return "22";
        else if (value == 3) return "20";
        else if (value == 4) return "helicopter";
        else if (value == 5) return "sandbag";
        else return "";
    }

    /* Concert a color to a description buffered image */
    public static BufferedImage colorToDesc(Color c) {
        if (c == Color.BLACK) return Assets.playerDescription[4];
        else if (c == Color.BLUE) return Assets.playerDescription[2];
        else if (c == Color.RED) return Assets.playerDescription[3];
        else if (c == Color.GREEN) return Assets.playerDescription[0];
        else if (c == Color.WHITE) return Assets.playerDescription[5];
        else if (c == Color.YELLOW) return Assets.playerDescription[1];
        else {
            System.out.println("aucune couleur correspondante");
            System.exit(-1);
            return null;
        }
    }

    /* Terminate the program and saving the settings */
    public static void terminate(Handler handler) {
        try {
            handler.getSettings().store(new FileWriter("Resources/settings.properties"), "Settings File");
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.getDisplay().dispose();
        System.exit(0);
    }

    /* Save a properties file */
    public static void saveProperties(Handler handler){
        try {
            handler.getSettings().store(new FileWriter("Resources/settings.properties"), "Settings File");
        } catch (Exception ignored) {
        }
    }

    /* Return which size to display */
    public static int sizeToNumber(int width, int height){
        if(width == 800 && height == 600) return 0;
        else if(width == 1280 && height == 720) return 1;
        else if(width == 1600 && height == 1200) return 2;
        else return 3;
    }

    /* Return a size from a number */
    public static int[] numberToSize(int i){
        if(i == 0) return new int[]{800, 600};
        else if(i == 1) return new int[]{1280, 720};
        else if(i == 2) return new int[]{1600, 1200};
        else return new int[]{1920, 1080};
    }

    /* Turn on/off the music */
    public static void musicOnOff(Handler handler){
        if (handler.getSettings().getProperty("music").equals("on")) {
            handler.getSettings().setProperty("music", "off");
        } else {
            handler.getSettings().setProperty("music", "on");
        }
    }

    /* Return the volume between 0 & 10 */
    public static int getVolume(Handler handler){
        return Integer.parseInt(handler.getSettings().getProperty("volume"))/10;
    }
}