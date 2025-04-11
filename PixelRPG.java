import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PixelRPG extends JPanel implements KeyListener, Runnable {

    // Game Constants
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREEN_WIDTH = 1500;
    private static final int SCREEN_HEIGHT = 1500;
    private static final int TILE_SIZE = 16;
    private static final int FPS = 30;

    // Game State
    private int playerX = 5 * TILE_SIZE;
    private int playerY = 5 * TILE_SIZE;
    private BufferedImage playerSprite;
    private int playerHealth = 100;

    private int enemyX = 15 * TILE_SIZE;
    private int enemyY = 10 * TILE_SIZE;
    private BufferedImage enemySprite;
    private int enemyHealth = 50;

    private boolean isFighting = false;
    private String combatLog = "";

    private Random random = new Random();
    private boolean canTransition = true; // Cooldown for area transitions

    // Current map
    private int[][] currentMap = GameMaps.worldMap;

    public PixelRPG() throws IOException {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        loadSprites();

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    private void loadSprites() throws IOException {
        File pngFile = new File("Mage.png");
        File pngFile2 = new File("Skelly2.png");
        playerSprite = createPixelSprite(pngFile);
        enemySprite = createPixelSprite2(pngFile2);
    }

    private BufferedImage createPixelSprite(File pngFile) throws IOException {
        return ImageIO.read(pngFile);
    }

    private BufferedImage createPixelSprite2(File pngFile) throws IOException {
        return ImageIO.read(pngFile);
    }

    private void update() {
        if (isFighting) {
            // Handle combat logic here
        } else {
            // Handle movement and world interactions
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the world
        for (int y = 0; y < currentMap.length; y++) {
            for (int x = 0; x < currentMap[0].length; x++) {
                if (currentMap[y][x] == 1) { // 1 represents a wall
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Draw the player
        g2d.drawImage(playerSprite, playerX, playerY, null);

        // Draw the enemy if not in combat
        if (!isFighting) {
            g2d.drawImage(enemySprite, enemyX, enemyY, null);
        }

        // Draw UI
        g2d.setColor(Color.BLACK);
        g2d.drawString("Health: " + playerHealth, 10, 20);

        if (isFighting) {
            g2d.drawString("Combat!", 10, 40);
            g2d.drawString(combatLog, 10, 60);
        }
    }

    public void SceneChange(int[][] newMap) {
        currentMap = newMap;
        playerX = 3 * TILE_SIZE; // Reset player position
        playerY = 3 * TILE_SIZE;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (!isFighting) {
            int key = e.getKeyCode();
            int newX = playerX;
            int newY = playerY;

            if (key == KeyEvent.VK_UP) {
                newY -= TILE_SIZE;
            } else if (key == KeyEvent.VK_DOWN) {
                newY += TILE_SIZE;
            } else if (key == KeyEvent.VK_LEFT) {
                newX -= TILE_SIZE;
            } else if (key == KeyEvent.VK_RIGHT) {
                newX += TILE_SIZE;
            }

            // Boundary and collision detection
            if (newX >= 0 && newX < SCREEN_WIDTH && newY >= 0 && newY < SCREEN_HEIGHT) {
                int tileType = currentMap[newY / TILE_SIZE][newX / TILE_SIZE];

                if (tileType == 0) { // 0 represents walkable tiles
                    playerX = newX;
                    playerY = newY;

                    // Simple encounter check
                    if (playerX == enemyX && playerY == enemyY) {
                        startCombat();
                    }
                } else if (tileType == 2 && canTransition) { // 2 represents an area transition
                    canTransition = false; // Disable further transitions temporarily
                    SceneChange(GameMaps.AREA2); // Change to a new map

                    // Re-enable transitions after a short delay
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            canTransition = true;
                        }
                    }, 500); // 500ms delay
                }
            }
        } else {
            // Handle combat input
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                playerAttack();
            }
        }
        repaint();
    }

    private void startCombat() {
        isFighting = true;
        combatLog = "A wild enemy appeared!";
    }

    private void playerAttack() {
        if (enemyHealth > 0) {
            int damage = random.nextInt(10) + 5;
            enemyHealth -= damage;
            combatLog = "You attacked for " + damage + " damage!";
            enemyTurn();
        } else {
            isFighting = false;
            combatLog = "Enemy defeated!";
            // Reset enemy position for simplicity
            enemyX = 15 * TILE_SIZE;
            enemyY = 10 * TILE_SIZE;
        }
    }

    private void enemyTurn() {
        if (enemyHealth > 0) {
            int damage = random.nextInt(8) + 3;
            playerHealth -= damage;
            combatLog += "\nEnemy attacked for " + damage + " damage!";
            if (playerHealth <= 0) {
                combatLog += "\nYou have been defeated!";
                // Game over logic
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = FPS;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            repaint();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Pixel RPG");
        PixelRPG game = new PixelRPG();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}