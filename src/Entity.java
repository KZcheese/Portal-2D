import java.awt.geom.Point2D;

public abstract class Entity {
	public static final double AIR_RESISTANCE = 1, GRAVITY = 1;

	private Point2D position;
	private SpriteSheet sSheet;
	private double timeScale, speed, maxSpeed, acceleration, angle;

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

}
