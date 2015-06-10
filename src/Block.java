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
}
