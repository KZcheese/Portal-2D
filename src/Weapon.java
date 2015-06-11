import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Weapon extends Entity {

	public Weapon(Rectangle2D bounds, SpriteSheet sprite) {
		super(bounds, sprite);
	}
}
