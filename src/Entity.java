import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Entity {
	private Point2D location;
	private Rectangle2D bounds;
	private Level level;
	private boolean usePhysics;

	private double moveAccel, gravityAccel, speed, speedCurrent, angle, timeScale;

	public Entity(Rectangle2D bounds) {
		this.bounds = bounds;
		location = new Point2D.Double();
		speed = 1;
	}

	public Entity() {
		this(null);
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLevel(Level l) {
		level = l;
	}

	public boolean intersects(Entity e) {
		return bounds.intersects(e.bounds);
	}

	public Rectangle2D getBounds() {
		return bounds;
	}

	public void updateEntity() {
		if (level != null) {
			update();
		}
	}

	public void update() {
		updateLocation();
	}

	public void updateLocation() {
		double dx = 0, dy = 0;

		// Gravity
		if (usePhysics) {
			dy -= gravityAccel;
			gravityAccel += 0.1;
		}

		// Movement
		speedCurrent += moveAccel;
		if (speedCurrent > speed) {
			speedCurrent = speed;
		}
		if (speedCurrent < 0) {
			speedCurrent = 0;
		}
		moveAccel -= 0.1;

		dx += Math.cos(angle) * speedCurrent;
		dy += Math.sin(angle) * speedCurrent;

		move(dx, dy);
	}

	private void move(double dx, double dy) {
		location.setLocation(location.getX() + dx, location.getY() + dy);
		bounds.setFrame(location.getX(), location.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public void resetGravity() {
		gravityAccel = 0;
	}

	public static void movePoint(Point2D p, double dist, double angle) {
		double dx = Math.cos(angle) * dist, dy = Math.sin(angle) * dist;
		p.setLocation(p.getX() + dx, p.getY() + dy);
	}

	public void render(Graphics g) {
		Point corner = level.getCorner();
		int dx = (int) (location.getX() - corner.x), dy = (int) (location
				.getY() - corner.y);
		g.translate(dx, dy);
		((Graphics2D) g).fill(bounds);
		g.translate(-dx, -dy);
	}

	public void pushForward() {
		moveAccel += 1;
	}

	public void pushUp() {
		gravityAccel -= 10;
	}

	public boolean physicsEnabled() {
		return usePhysics;
	}

	public void enablePhysics(boolean physics) {
		usePhysics = physics;
	}
	
	public void setTimeScale(double timeScale) {
		this.timeScale = timeScale;
	}
	
	public double getTimeScale() {
		return timeScale;
	}
}
