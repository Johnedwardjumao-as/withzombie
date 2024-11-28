import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Zombie extends Entity {
    GamePanel gp; // Reference to GamePanel
    int health = 100;
    int maxHealth = 100;
    BufferedImage zombie1; // Zombie sprite
    int speed = 1; // Speed of the zombie

    public Zombie(GamePanel gp) {
        this.gp = gp;
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);

        // Load zombie sprite
        try {
            zombie1 = ImageIO.read(getClass().getResourceAsStream("/src/mc sprites/zombie.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawHealthBar(Graphics2D g2, int screenX, int screenY) {
        if (gp == null) return; // Safety check
        
        // Health bar background
        g2.setColor(Color.RED);
        g2.fillRect(screenX, screenY - 10, gp.tileSize, 5);
        
        // Current health
        g2.setColor(Color.GREEN);
        int currentHealthWidth = (int)((double) health / maxHealth * gp.tileSize);
        g2.fillRect(screenX, screenY - 10, currentHealthWidth, 5);
    }

    public void draw(Graphics2D g2) {
        if (zombie1 == null) return; // Safety check

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(zombie1, screenX, screenY, gp.tileSize, gp.tileSize, null);
            drawHealthBar(g2, screenX, screenY);
        }
    }

    public void decreaseHealth(int damage) {
        health = Math.max(0, health - damage);
    }

    public void update() {
        // Move zombie toward the player
        if (gp.player.worldX > this.worldX) {
            worldX += speed;
        } else if (gp.player.worldX < this.worldX) {
            worldX -= speed;
        }

        if (gp.player.worldY > this.worldY) {
            worldY += speed;
        } else if (gp.player.worldY < this.worldY) {
            worldY -= speed;
        }

        // Update solid area position
        solidArea.x = worldX;
        solidArea.y = worldY;
    }
}
