

public class PlayerStats {
    private int health = 100;
    private int maxHealth = 100;

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int amount) {
        health = Math.max(0, health - amount);
        System.out.println("Player hit! Health reduced to: " + health);
        
        if (health <= 0) {
            System.out.println("Game Over - Player died!");
            // You might want to add game over logic here
            // For example, reset game or show game over screen
        }
    }

    public void increaseHealth(int amount) {
        health = Math.min(health + amount, maxHealth);
        System.out.println("Health increased. Current health: " + health);
    }
}

