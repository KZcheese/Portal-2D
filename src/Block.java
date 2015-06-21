import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
/**
 * Creates a solid, generic block of size 64x64.
 * @author Kevin Zhan
 * @author Benjamin Hetherington
 */
public class Block extends Entity {
	public static final int SIZE = 64;
	public static final int BOTTOM = 0, TOP = 1, LEFT = 2, RIGHT = 3;

	public Block(int x, int y) {
		super(new Rectangle2D.Double(x * SIZE, y * SIZE, SIZE, SIZE));
		enablePhysics(false);
	}

	public Block() {
		this(0, 0);
	}

	/**
	 * Teleports e to a specified side of the block.
	 * @param e
	 * @param side
	 */
	public void moveToSide(Entity e, int side) {
		Rectangle2D bounds1 = getBounds(), bounds2 = e.getBounds();
		switch (side) {
		case TOP:
			e.setLocation(bounds1.getCenterX() - bounds2.getWidth() / 2,
					bounds1.getMinY() - bounds2.getHeight());
			break;
		case BOTTOM:
			e.setLocation(bounds1.getCenterX() - bounds2.getWidth() / 2,
					bounds1.getMaxY());
			break;
		case LEFT:
			e.setLocation(bounds1.getMinX() - bounds2.getWidth(),
					bounds1.getCenterY() - bounds2.getHeight() / 2);
			break;
		case RIGHT:
			e.setLocation(bounds1.getMaxX(),
					bounds1.getCenterY() - bounds2.getHeight() / 2);
		}
	}
}
