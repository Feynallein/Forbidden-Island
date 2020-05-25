package gfx;

import java.awt.image.BufferedImage;

class SpriteSheet {
    private BufferedImage sheet;

    //constructor
    SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    //crop which part of the buffered image we want
    BufferedImage crop(int x, int y, int width, int height){
        return sheet.getSubimage(x, y, width, height);
    }


    /* GETTERS AND SETTERS */

    int getWidth(){return sheet.getWidth();}
}
