import java.awt.geom.Point2D;
import java.util.List;

public abstract class Entity {
	public static final double AIR_RESISTANCE = 1, GRAVITY = 1,
			MOVEMENT_INCREMENT = 0.1;

	private Point2D position;
	private double timeScale, speed, maxSpeed, acceleration, angle;
	private Bounds bounds;
	private Level level;

	public Point2D getPosition() {
		return position;
	}

	public void update() {
		double dx = speed * Math.cos(angle) * timeScale, dy = speed
				* Math.sin(angle) * timeScale;
		position.setLocation(position.getX() + dx, position.getY() + dy);

		speed += acceleration;
		acceleration -= AIR_RESISTANCE;

	}

	public void move(double dist, double angle) {
		double dx = Math.cos(angle) * dist, dy = Math.sin(angle) * dist;
	}

	public void fix(Point2D p2) {
		Point2D p1 = position;
		List<Entity> entities = level.getEntities();
	}
}
