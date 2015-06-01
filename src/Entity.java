import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public abstract class Entity {
	public static final double 
		AIR_RESISTANCE = 1,
		GRAVITY = 1,
		MOVEMENT_INCREMENT = 0.1;
	
	private Point2D position;
	private SpriteSheet sSheet;
	private double timeScale, speed, maxSpeed, acceleration, angle, moveAngle;
	private Rectangle2D bounds;
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
	
	public void updatePosition() {
		
	}
	
	public void move(double dist, double angle) {
		double dx = Math.cos(angle) * dist, dy = Math.sin(angle) * dist;
	}
	
	public void fix(Point2D p2) {
		Point2D p1 = position;
		List<Entity> entities = level.getEntities();
		boolean collided = false;
		double dist = 0, toMove = position.distance(p2);
		do {
			for (Entity e : entities) {
				if (bounds.intersects(e.bounds)) {
					collided = true;
				} else {
					double distMoved = MOVEMENT_INCREMENT * timeScale;
					move(distMoved, angle);
				}
			}
		} while (dist  !collided);
	}
}
