package util;

import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Utils {
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

    public static String colorToString(Color c) {
        if (c == Color.BLACK) return "black";
        else if (c == Color.BLUE) return "blue";
        else if (c == Color.RED) return "red";
        else if (c == Color.GREEN) return "green";
        else if (c == Color.WHITE) return "white";
        else if (c == Color.YELLOW) return "yellow";
        else {
            System.out.println("aucune couleur correspondante");
            System.exit(-1);
            return null;
        }
    }

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

    public static boolean isArtifact(int num) {
        int[] areArtifacts = {16, 17, 18, 19, 20, 21, 22, 23};
        for (int i : areArtifacts) {
            if (num == i) return true;
        }
        return false;
    }

    public static int artifactToValue(int num) {
        if (num == 16 || num == 17) return 1;
        else if (num == 18 || num == 19) return 0;
        else if (num == 20 || num == 21) return 3;
        else if (num == 22 || num == 23) return 2;
        else return -1;
    }

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

    public static boolean allEquals(int[] array, int value) {
        for (int i : array) {
            if (i != value) return false;
        }
        return true;
    }

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

    public static String invValueToString(int value){
        if (value == 0) return "18";
        else if (value == 1) return "16";
        else if (value == 2) return "22";
        else if (value == 3) return "20";
        else if (value == 4) return "helicopter";
        else if (value == 5) return "sandbag";
        else return "";
    }
}
