import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * Displays the game.
 * 
 * @author Kevin Zhan
 * @author Benjamin Hetherington
 *
 */
@SuppressWarnings({ "serial" })
public class Renderer extends JComponent {
	public Level level;

	public Renderer(Level level) {
		this.level = level;
	}

	/**
	 * Renders the game onto a BufferedImage and scales it to window size while
	 * preserving aspect ratio. Displays the scaled image.
	 */
	public void paintComponent(Graphics g) {
		Rectangle2D bounds = level.getBounds();
		BufferedImage canvas = new BufferedImage((int) bounds.getWidth(),
				(int) bounds.getHeight(), BufferedImage.TYPE_INT_RGB);
		level.render(canvas.getGraphics());
		// level.render(g);
		//
		double scaleFactor;
		double canvasAspectRatio = ((double) canvas.getWidth() / canvas
				.getHeight());
		double windowAspectRatio = ((double) this.getWidth() / this.getHeight());
		if (canvasAspectRatio > windowAspectRatio)
			scaleFactor = (double) getWidth() / canvas.getWidth();
		else
			scaleFactor = (double) getHeight() / canvas.getHeight();
		Image scaled = canvas.getScaledInstance(
				(int) ((double) canvas.getWidth() * scaleFactor),
				(int) ((double) canvas.getHeight() * scaleFactor),
				BufferedImage.SCALE_FAST);
		g.drawImage(scaled, this.getWidth() / 2 - scaled.getWidth(null) / 2,
				this.getHeight() / 2 - scaled.getHeight(null) / 2, this);

		// Experimental High Quality Scaling. Broken and probably extremely
		// inefficient.
		// AffineTransform scaler = new AffineTransform();
		//
		// scaler.scale((int) scaleFactor, (int) scaleFactor);
		// AffineTransformOp scalerMult = new AffineTransformOp(scaler,
		// AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		// BufferedImage multiplied = new BufferedImage(
		// (int) (canvas.getWidth() * (int) scaleFactor),
		// (int) (canvas.getHeight() * (int) scaleFactor),
		// BufferedImage.TYPE_INT_RGB);
		// scalerMult.filter(canvas, multiplied);
		//
		// scaler.scale((int) (canvas.getWidth() * (scaleFactor) / multiplied
		// .getWidth()),
		// (int) (canvas.getHeight() * scaleFactor / multiplied
		// .getHeight()));
		// AffineTransformOp scalerLine = new AffineTransformOp(scaler,
		// AffineTransformOp.TYPE_BICUBIC);
		// BufferedImage scaled = new BufferedImage(
		// (int) (canvas.getWidth() * scaleFactor),
		// (int) (canvas.getHeight() * scaleFactor),
		// BufferedImage.TYPE_INT_RGB);
		// scalerLine.filter(multiplied, scaled);
		//
		// g.drawImage(scaled, 0, 0, this);
	}

	public void setLevel(Level level) {
		this.level = level;
	}
}