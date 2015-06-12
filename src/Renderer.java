import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;


public class Renderer extends JComponent {
	public Level level;

	public Renderer(Level level) {
		this.level = level;
	}

	public void paintComponent(Graphics g) {
		Rectangle2D bounds = level.getBounds();
		BufferedImage canvas = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
		level.render(canvas.getGraphics());
		double scaleFactor = (double) getHeight() / canvas.getHeight();
//		Image scaled = canvas.getScaledInstance((int) (canvas.getWidth() * scaleFactor), (int) (canvas.getHeight() * scaleFactor), BufferedImage.SCALE_FAST);
		g.drawImage(canvas, 0, 0, this);
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
}