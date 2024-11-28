import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

   public class GamePanel extends JPanel implements Runnable   {

      //SCREEN SIZE MF
      int originalTileSize = 16; //16x16 tile
      int scale = 3;
      
      public int tileSize = originalTileSize * scale; // 48x48 tiles
      public int maxScreenCol = 16;
      public int maxScreenRow = 12;
      public int screenWidth = tileSize * maxScreenCol; // 768 pix
      public int screenHeight = tileSize * maxScreenRow; ///576
      
      //for wold setting   
      public final int maxWorldCol = 70;
      public final int maxWorldRow = 30;
      public final int worldWidth = tileSize * maxWorldCol;
      public final int worldHeight = tileSize * maxWorldRow;
      
      //for Fps
      
      int FPS = 60; 
      
      TileManager tileM = new TileManager(this);
      KeyHandler keyH = new KeyHandler();
      Thread gameThread;
      public CollisionChecker cChecker = new CollisionChecker(this);
      
      public Player player = new Player(this,keyH);
      public WaveManager waveManager;
      public PlayerStats playerStats;
      public MeleeWeapon meleeWeapon;
           
      
      public GamePanel()   {
         
         this.setPreferredSize(new Dimension(screenWidth, screenHeight));
         this.setBackground(Color.black);
         this.setDoubleBuffered(true);
         this.addKeyListener(keyH);
         this.setFocusable(true);
         
         playerStats = new PlayerStats();
         waveManager = new WaveManager(this,player);
         meleeWeapon = new MeleeWeapon(this);
      }
      
      public void startGameThread() {
         
         gameThread = new Thread(this);
         gameThread.start();
      }
      
      @Override
//      public void run() {
//      
//         while(gameThread != null) {   
//         
//         double drawInterval = 1000000000/FPS;
//         double nextDrawTime = System.nanoTime() + drawInterval;
//         
//         update();
//         
//         
//         repaint();
//         
//            
//            try {
//               double remainingTime = nextDrawTime - System.nanoTime();
//               remainingTime = remainingTime/1000000;
//               
//               if(remainingTime < 0)  {
//                     remainingTime = 0;
//               }
//               
//               Thread.sleep((long) remainingTime);
//               
//               nextDrawTime += drawInterval;
//               
//            }  catch(InterruptedException e)    {
//               e.printStackTrace();
//            }
//            
//         }
//      
//      }
      public void run() {
      
         double drawInterval = 1000000000/FPS;
         double delta = 0;
         long lastTime = System.nanoTime();
         long currentTime;
         long timer = 0;
         long drawCount = 0;
         
         while(gameThread != null)  {
         
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime)  / drawInterval;
            timer += (currentTime - lastTime);
            
            lastTime = currentTime;
            
            if(delta >= 1) {
               update();
               repaint();
               delta--;
               drawCount++;
            }
            
            if(timer >= 1000000000)  {
               System.out.println("FPS:" + drawCount);  
               drawCount = 0;
               timer = 0; 
            }  
         
                
         }
      
      }
      
      public void update() {
      
         player.update();
         waveManager.update();
      
      }
      public void paintComponent(Graphics g)  {
      
         super.paintComponent(g);
         
         Graphics2D g2 = (Graphics2D) g;
         
         tileM.draw(g2);
         
         player.draw(g2);
         
         waveManager.draw(g2);
         
         g2.dispose();
      }   
}
