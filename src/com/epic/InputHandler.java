package com.epic;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {

    public boolean[] key = new boolean[68836];

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key > 0 && key < this.key.length) {
            this.key[key] = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key > 0 && key < this.key.length) {
            this.key[key] = false;
        }
    }
}
