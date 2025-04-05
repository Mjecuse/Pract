import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Skelly extends JPanel{
	// Extending JPanel creates the paint function of java(acts like html)
	public static void main(String[]args) {
		JFrame frame = new JFrame("Idea for spooky sprite");
		Skelly skelly = new Skelly();
		frame.add(skelly);
		frame.setSize(400, 400);
// This gives an average size for the monster. Depending on the level it may be bigger or smaller
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.lightGray);
		float lineWidth =10.f;
		//This helps increase the line thickness of the monster
		g2d.setStroke(new BasicStroke(lineWidth));
		g2d.fillOval(180, 50, 40, 40);
		g2d.drawLine(200, 90, 200, 200);
		g2d.drawLine(200, 110,150,150);
		g2d.drawLine(200, 110, 250, 150);
		g2d.drawLine(200, 200, 150, 300);
		//left foot
		g2d.drawLine(200, 200, 250, 300);
		//right foot
	}
}
