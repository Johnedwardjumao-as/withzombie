import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.imageio.ImageIO; 
import java.awt.Graphics2D;
import java.awt.image.BufferedImage; 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WaveManager {
    private GamePanel gp;
    private Player player;
    public ArrayList<Zombie> zombies = new ArrayList<>();

    public WaveManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        spawnWave(); // Initial wave spawn
    }

    public void spawnWave() {
        for (int i = 0; i < 5; i++) {
            Zombie zombie = new Zombie(gp);
            zombies.add(zombie);
        }
    }

    public void update() {
        // Zombie-to-zombie collision
        for (int i = 0; i < zombies.size(); i++) {
            for (int j = i + 1; j < zombies.size(); j++) {
                Zombie zombie1 = zombies.get(i);
                Zombie zombie2 = zombies.get(j);
                
                // Check for collision between zombies
                if (zombie1.solidArea.intersects(zombie2.solidArea)) {
                    // Separate zombies
                    if (zombie1.worldX < zombie2.worldX) {
                        zombie1.worldX -= zombie1.speed;
                        zombie2.worldX += zombie2.speed;
                    } else {
                        zombie1.worldX += zombie1.speed;
                        zombie2.worldX -= zombie2.speed;
                    }
                    
                    if (zombie1.worldY < zombie2.worldY) {
                        zombie1.worldY -= zombie1.speed;
                        zombie2.worldY += zombie2.speed;
                    } else {
                        zombie1.worldY += zombie1.speed;
                        zombie2.worldY -= zombie2.speed;
                    }
                }
            }
        }

        // Remove zombies that are far away or dead
        zombies.removeIf(zombie -> 
            Math.abs(zombie.worldX - player.worldX) > gp.screenWidth * 2 ||
            Math.abs(zombie.worldY - player.worldY) > gp.screenHeight * 2 ||
            zombie.health <= 0
        );

        for (Zombie zombie : zombies) {
            zombie.update();

            // Check if zombie hits the player
            if (zombie.solidArea.intersects(player.solidArea)) {
                System.out.println("Zombie hit player!");
                gp.playerStats.decreaseHealth(10);
            }
        }

        // Check if all zombies are defeated
        if (zombies.isEmpty()) {
            System.out.println("All zombies defeated. Spawning next wave.");
            spawnWave();
        }
    }

    // Draw method to render zombies
    public void draw(Graphics2D g2) {
        for (Zombie zombie : zombies) {
            zombie.draw(g2);
        }
    }
}