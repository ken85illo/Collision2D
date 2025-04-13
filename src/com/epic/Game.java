package com.epic;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.epic.BufferedImageLoader;
import com.epic.SpriteSheet;
import com.epic.InputHandler;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 300;
    public static final int HEIGHT = WIDTH / 16 * 9;
    private static final int SCALE = 3;
    private static final String TITLE = "Game title goes here!";

    private boolean running = false;
    private Thread thread;

    private SpriteSheet spriteSheet;
    private Player player;
    private Ground ground;
    private BufferedImage background;
    private InputHandler input;

    private void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void init() {
        input = new InputHandler();
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            spriteSheet = new SpriteSheet(loader.loadImage("/textures/sprite_sheet.png"));
            background = loader.loadImage("/textures/background.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        addKeyListener(input);

        player = new Player(spriteSheet);
        ground = new Ground(spriteSheet);
    }

    private void update() {
        player.update(input.key);
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        {
            // NOTE: execute all render stuffs here :)
            g.drawImage(background, 0, 0, null);

            player.render(g);
            ground.render(g);
        }
        g.dispose();
        bs.show();
    }

    @Override
    public void run() {
        requestFocus();
        init();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double amountOfTicks = 60.0;
        double ns = 1000000000.0 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        int updates = 0;

        // game loop
        while (running) {
            long initialTime = System.nanoTime();
            delta += (initialTime - lastTime) / ns;
            lastTime = initialTime;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.println(frames + " fps, " + updates + "ups");
                frames = 0;
                updates = 0;
                timer += 1000;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame window = new JFrame(TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.start();
    }

    public static int getWidthOfCanvas() {
        return WIDTH * SCALE;
    }

    public static int getHeightOfCanvas() {
        return HEIGHT * SCALE;
    }

}
