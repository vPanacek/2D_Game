package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;




    public class GamePanel extends JPanel implements Runnable {

        ///SETTING OF A SCREEN
        final int originalTileSize = 16; //16x16 tile
        final int scale = 3;
        public final int tileSize = originalTileSize * scale; // 48x48 tile
        public final int maxScreenCol = 16;
        public final int maxScreenRow = 12;
        public final int screenWidth= tileSize * maxScreenCol;  //768 pixels
        public final int screenHeight = tileSize * maxScreenRow; //576 pixels


        //WORLD SETTINGS
        public final int maxWorldCol = 50;
        public final int maxWorldRow = 50;
        public final int WorldWidth = tileSize * maxWorldCol;
        public final int WorldHeight = tileSize * maxScreenRow;



        //FPS
        int FPS = 60;


        TileManager tileM = new TileManager(this);
        KeyHandler keyH = new KeyHandler();
        Thread gameThread;
       public CollisionChecker cChecker = new CollisionChecker(this);
       public AssetSetter aSetter = new AssetSetter(this);
       public Player player = new Player(this,keyH);
       public SuperObject obj[] = new SuperObject[10]; // 10 mean how many objects can be displayed at the same time.




        public GamePanel() {
            this.setPreferredSize(new Dimension(screenWidth, screenHeight));
            this.setBackground(Color.darkGray);
            this.setDoubleBuffered(true);
            this.addKeyListener(keyH);
            this.setFocusable(true);
        }

        public void setupGame() {

            aSetter.setObject();

        }

        public void startGameThread() {
            gameThread = new Thread(this);
            gameThread.start();
        }


        @Override
        public void run() {

            double drawInterval = 1000000000/FPS; // 0.01666 seconds
            double nextDrawTime = System.nanoTime() + drawInterval;



            while (gameThread != null) {
                long currentTime = System.nanoTime();

                update();

                repaint();

                try {
                    double remainingTime = nextDrawTime - System.nanoTime();
                    remainingTime = remainingTime/1000000;

                    if (remainingTime < 0) {
                        remainingTime = 0;
                    }

                    Thread.sleep((long) remainingTime);

                    nextDrawTime += drawInterval;

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }

        public void update() {

            player.update();

        }


        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            //TILE
            tileM.draw(g2);

            //OBJECT
            for(int i = 0; i < obj.length; i ++) {
                if (obj[i] !=null) {
                    obj[i].draw(g2, this);
                }



            }


            //PLAYER
            player.draw(g2);
            g2.dispose();

        }

    }


