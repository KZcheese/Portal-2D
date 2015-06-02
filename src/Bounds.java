import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Bounds {
	Rectangle2D hitBox;

	public Bounds(Point2D p, double width, double height) {
		hitBox = new Rectangle2D.Double(p.getX(), p.getY(), width, height);
	}

	public Bounds(Rectangle2D hitBox) {
		this.hitBox = hitBox;
	}

}
