package com.epic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.epic.SpriteSheet;

public class Player {

    private final int SCALE = 3;
    private float x = Game.WIDTH * SCALE / 2 - 16 * SCALE;
    private float y = 100;
    private float xVelocity = 0;
    private float yVelocity = 0;

    private double speed = 5;
    private int jump = 10;

    private boolean jumping;

    private double gravity = 0.5;
    private boolean grounded = false;

    SpriteSheet spriteSheet;

    public Player(SpriteSheet ss) {
        this.spriteSheet = ss;
    }

    public void update(boolean[] key) {

        x += xVelocity;
        y += yVelocity;

        // Bounds and physics
        ///////////////////////////////////////
        if (x < -8 * SCALE)
            x = -8 * SCALE;

        if (x >= Game.getWidthOfCanvas() - 24 * SCALE)
            x = Game.getWidthOfCanvas() - 24 * SCALE;

        if (y < 0)
            y = 0;

        if (y >= Game.getHeightOfCanvas() - 42 * SCALE) {
            grounded = true;
            y = Game.getHeightOfCanvas() - 42 * SCALE;
        }

        if (!grounded) {
            yVelocity += gravity;
        }
        if (yVelocity > 10)
            yVelocity = 10;
        //////////////////////////////////////

        if (key[KeyEvent.VK_LEFT])
            xVelocity = (float) -speed;

        if (key[KeyEvent.VK_RIGHT])
            xVelocity = (float) speed;

        if (!key[KeyEvent.VK_LEFT] && !key[KeyEvent.VK_RIGHT]) {
            xVelocity = 0;
        }

        if (key[KeyEvent.VK_SPACE] && !jumping && grounded) {
            jumping = true;
            grounded = false;
            yVelocity = -jump;
        }

        if (!key[KeyEvent.VK_SPACE]) {
            jumping = false;
        }

    }

    public void render(Graphics g) {

        BufferedImage player = spriteSheet.grabImage(1, 1, 32, 32);
        g.drawImage(player, (int) x, (int) y, player.getWidth() * SCALE, player.getHeight() * SCALE, null);
    }
}
