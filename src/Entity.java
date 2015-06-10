import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Entity {
	private Point2D location;
	private Rectangle2D bounds;
	private Level level;
	private boolean usePhysics, hasJumped;
	private int jumps;

	private double moveAccel, gravityAccel, speed, speedCurrent, angle,
			timeScale, totalAngle;

	public static final double GRAVITY = 1.0, FRICTION = 0.8;

	public Entity(Rectangle2D bounds) {
		this.bounds = bounds;
		location = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
		speed = 15;
		usePhysics = true;
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
		if (getLevel() != null) {
			update();
		}
	}

	public void update() {
		updateLocation();
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = (angle % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);
	}

	public void updateLocation() {
		double dx = 0, dy = 0;

		// Gravity
		if (usePhysics) {
			dy -= gravityAccel;
			gravityAccel -= GRAVITY;
		}

		// Movement
		speedCurrent -= FRICTION;
		if (speedCurrent < 0) {
			speedCurrent = 0;
		}
		dx += Math.cos(angle) * speedCurrent;
		dy += Math.sin(angle) * speedCurrent;

		move(dx, dy);
		totalAngle = Math.atan2(dy, dx);
	}

	public void move(double dx, double dy) {
		location.setLocation(location.getX() + dx, location.getY() + dy);
		bounds.setFrame(location.getX(), location.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public void setLocation(double x, double y) {
		move(x - location.getX(), y - location.getY());
	}

	public void resetGravity() {
		gravityAccel = 0;
	}

	public static void movePoint(Point2D p, double dist, double angle) {
		double dx = Math.cos(angle) * dist, dy = Math.sin(angle) * dist;
		p.setLocation(p.getX() + dx, p.getY() + dy);
	}

	public void render(Graphics g) {
		Point corner = getLevel().getCorner();
		int dx = (int) (location.getX() - corner.x), dy = (int) (location
				.getY() - corner.y), w = (int) bounds.getWidth(), h = (int) bounds
				.getHeight();
		g.setColor(new Color(35, 35, 35));
		g.fillRect(dx, dy, w, h);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(dx, dy, w, h);
	}

	public void pushForward() {
		speedCurrent += 2;
	}

	public void jump() {
		if (jumps < 1) {
			gravityAccel += 22.5;
			jumps++;
		}
	}

	public void resetJump() {
		jumps = 0;
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

	public void resetMovementAcceleration() {
		speedCurrent = 0;
	}

	public void collideLeft(Entity e) {
	}

	public void collideRight(Entity e) {
	}

	public void collideTop(Entity e) {
	}

	public void collideBottom(Entity e) {
	}

	public void applyForce(double angle, double magnitude) {
		double a = speedCurrent, b = magnitude, innerAngle = Math.PI - angle
				+ this.angle, c = Math.sqrt(a * a + b * b - 2 * a * b
				* Math.cos(Math.abs(this.angle - innerAngle)));
		double otherInnerAngle = Math.asin(c
				* Math.sin(Math.abs(angle - innerAngle)));
		if (Math.abs(angle) > Math.PI)
			setAngle(this.angle - otherInnerAngle);
		else
			setAngle(this.angle + otherInnerAngle);
		speedCurrent = c;
		if (speedCurrent > speed) {
			speedCurrent = speed;
		}
	}

	public void moveAtAngle(double angle, double speed) {
		speedCurrent += speed;
		if (speedCurrent > this.speed) {
			speedCurrent = this.speed;
		}
		if (speedCurrent < 0) {
			speedCurrent = 0;
		}
		setAngle(angle);
	}

	public double getTotalAngle() {
		double a = speedCurrent, b = gravityAccel, innerAngle = Math.PI
				- Math.PI / 2 + this.angle, c = Math.sqrt(a * a + b * b - 2 * a
				* b * Math.cos(innerAngle));
		double angle = Math.asin(b * Math.sin(innerAngle) / c) + this.angle;
		// System.out.println(angle);
		return Util.normalizeAngle(angle);
	}

	public Level getLevel() {
		return level;
	}

}
