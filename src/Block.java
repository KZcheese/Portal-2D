import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Block extends Entity {
	public static final int SIZE = 64;
	
	public Block(int x, int y) {
		super(new Rectangle2D.Double(x * SIZE, y * SIZE, SIZE, SIZE));
		enablePhysics(false);
	}
	
	public Block() {
		this(0, 0);
	}
	
	public void moveAbove(Entity e) {
		Rectangle2D bounds1 = getBounds(), bounds2 = e.getBounds();
		e.setLocation(bounds1.getMinX(), bounds1.getMinY() - bounds2.getHeight());
	}
}
