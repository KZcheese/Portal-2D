import java.awt.geom.Point2D;

public abstract class Entity {
	private Point2D position;
	private SpriteSheet sSheet;
	private double timeScale;

	public void move(double x, double y) {
		position.setLocation(position.getX() + x, position.getY() + y);
	}
}
