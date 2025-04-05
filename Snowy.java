
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Snowy extends JPanel{
	private BufferedImage snowLayer;
	private Random random;
	public Snowy() {
		snowLayer = new
				BufferedImage(1200, 1200,
				BufferedImage.TYPE_INT_ARGB);
				random = new Random();
				createS();
				}
	private void createS() {
		Graphics g = snowLayer.getGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 1200, 1200);
		for(int i = 0; i <100; i++) {
			int x = random.nextInt(1200);
			int y = random.nextInt(1200);
			g.setColor(Color.WHITE);
			g.fillOval(x, y, 5, 5);
		}
		
		g.dispose();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(snowLayer, 0, 0, null);
		
	}
	public static void main(String[]args) {
		JFrame frame = new JFrame("Blizzard");
		Snowy snowy = new Snowy();
		frame.add(snowy);
		frame.setSize(1200, 1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
