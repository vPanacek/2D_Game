package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


    public class Player extends Entity{


        GamePanel gp;
        KeyHandler keyH;

        public final int screenX;
        public final int screenY;


        public Player(GamePanel gp, KeyHandler keyH) {

            this.gp = gp;
            this.keyH = keyH;

            screenX = gp.screenWidth/2 - (gp.tileSize/2);
            screenY = gp.screenHeight/2 - (gp.tileSize/2);

            solidArea = new Rectangle();
            solidArea.x = 3;
            solidArea.y = 7;
            solidArea.width = 9;
            solidArea.height = 9;

            setDefaultValues();
            getPlayerImage();
        }

        public void setDefaultValues() {
            worldX = gp.tileSize * 23;
            worldY = gp.tileSize * 21;
            speed = 3;
            direction ="down";
        }

        public void getPlayerImage() {
            try {



                up1 = ImageIO.read(getClass().getResourceAsStream("/player/greenBoy_back2.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/player/greenBoy_back3.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/player/greenBoy_front2.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/player/greenBoy_front3.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/player/greenBoy_bok_leva1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/player/greenBoy_front3.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/player/greenBoy_bok_prava1.png"));
                right2=ImageIO.read(getClass().getResourceAsStream("/player/greenBoy_front3.png"));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        public void update() {

            if (keyH.upPressed ==true || keyH.downPressed == true || keyH.leftPressed== true || keyH.rightPressed== true)  {

                if(keyH.upPressed == true) {
                    direction = "up";
                }
                else  if (keyH.downPressed == true) {
                    direction = "down";
                } else if (keyH.rightPressed == true) {
                    direction = "right";
                } else if (keyH.leftPressed == true) {
                    direction = "left";


                }
//Check Tile Collision
                collisionOn = false;
                gp.cChecker.CheckTile(this);
                // If Collision Is False, Player Can Move
                if(collisionOn == false){
                    switch (direction) {
                        case "up": worldY -= speed;
                        break;
                        case "down": worldY += speed;
                            break;
                        case "left": worldX -= speed;
                            break;
                        case "right": worldX += speed;
                            break;
                    }
                }

                spriteCounter ++;
                if (spriteCounter > 12) {
                    if (spriteNum==1) {
                        spriteNum =2;
                    }
                    else if (spriteNum ==2) {
                        spriteNum =1;
                    }
                    spriteCounter =0;
                }


            }


        }
        public void draw(Graphics2D g2) {


            BufferedImage image = null;
            switch (direction){
                case "up":
                    if(spriteNum==1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image =up2;
                    }
                    break;
                case "down":
                    if (spriteNum==1) {
                        image = down1;
                    }
                        if (spriteNum == 2) {
                            image =down2;
                        }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image =right1;
                    }
                    if (spriteNum == 2) {
                        image =right2;
                    }

                    break;
                case "left":
                    if (spriteNum == 1) {
                        image =left1;
                    }
                    if (spriteNum == 2) {
                    image =left2;
                }
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }





    }


