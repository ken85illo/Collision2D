package com.epic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.epic.SpriteSheet;

public class Ground {

    SpriteSheet spriteSheet;

    public Ground(SpriteSheet ss) {
        this.spriteSheet = ss;
    }

    public void render(Graphics g) {
        BufferedImage ground = spriteSheet.grabImage(2, 1, 32, 32);
        for (int i = 0; i < Game.getWidthOfCanvas(); i += 32) {
            g.drawImage(ground, i, Game.getHeightOfCanvas() - 32, null);
        }
    }
}
