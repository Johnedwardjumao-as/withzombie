import java.awt.Rectangle;
import java.util.ArrayList;

public class MeleeWeapon {
    GamePanel gp;

    public MeleeWeapon(GamePanel gp) {
        this.gp = gp;
    }

    public void attack(Player player, ArrayList<Zombie> zombies) {
        Rectangle weaponArea = new Rectangle(
            player.worldX + player.solidArea.x,
            player.worldY + player.solidArea.y,
            player.solidArea.width,
            player.solidArea.height
        );

        for (Zombie zombie : zombies) {
            if (weaponArea.intersects(zombie.solidArea)) {
                zombie.decreaseHealth(20); // Adjust damage as needed
            }
        }
    }
}
