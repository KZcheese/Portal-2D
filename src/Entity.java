import java.awt.geom.Point2D;

public abstract class Entity {
	Point2D position;
	SpriteSheet sSheet;

	public void move(double x, double y) {
		position.setLocation(position.getX() + x, position.getY() + y);
	}
}
