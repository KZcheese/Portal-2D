import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Entity {
	private Point2D location;
	private Rectangle2D bounds;
	private Level level;
	private boolean usePhysics, grounded;
	private int jumps;
	private SpriteSheet sprite;

	private double topSpeed, velX, timeScale, velY;
	private Vector velocity, movement;

	public static final double GRAVITY = 1.0, FRICTION = 0.4;

	public Entity(Rectangle2D bounds) {
		this.bounds = bounds;
		location = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
		topSpeed = 10;
		velocity = new Vector();
		usePhysics = true;
	}
	
	public Entity(Rectangle2D bounds, SpriteSheet sprite) {
		this.bounds = bounds;
		location = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
		topSpeed = 10;
		velocity = new Vector();
		usePhysics = true;
		this.sprite = sprite;
	}

	public SpriteSheet getSprite() {
		return sprite;
	}
	
	public void setSprite(SpriteSheet sprite) {
		this.sprite = sprite;
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
		if (sprite != null) {
			sprite.update();
		}
	}

	public void updateLocation() {
		// Gravity
		if (usePhysics) {
			velocity.dy += GRAVITY;
		}

		// Movement
		if (velocity.dx > 0) {
			velocity.dx -= FRICTION;
			if (velocity.dx < 0) {
				velocity.dx = 0;
			}
		} else {
			velocity.dx += FRICTION;
			if (velocity.dx > 0) {
				velocity.dx = 0;
			}
		}
		move(velocity.dx, velocity.dy);
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
		velocity.dy = 0;
	}

	public void render(Graphics g) {
		Point corner = getLevel().getCorner();
		int dx = (int) (location.getX() - corner.x), dy = (int) (location
				.getY() - corner.y), w = (int) bounds.getWidth(), h = (int) bounds
				.getHeight();
		if (sprite == null) {
			g.setColor(new Color(35, 35, 35));
			g.fillRect(dx, dy, w, h);
			g.setColor(new Color(0, 0, 0));
			g.drawRect(dx, dy, w, h);
		} else {
			g.drawImage(sprite.getCurrentFrame(), dx, dy, null);
		}
	}

	public void jump() {
		if (jumps < 1) {
			velocity.dy -= 20;
			jumps++;
		}
		// System.out.println(jumps);
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
		velocity.dx = 0;
	}

	public void collideLeft(Entity e) {
	}

	public void collideRight(Entity e) {
	}

	public void collideTop(Entity e) {
		double signum = Math.signum(e.velocity.dx);
		double friction = signum * 1;
		e.velocity.dx -= friction;
		if (Math.signum(e.velocity.dx) != signum) {
			e.velocity.dx = 0;
		}
		e.setGrounded(true);
	}

	public void collideBottom(Entity e) {
	}

	public void kill() {
		level.removeEntity(this);
	}

	public double getVelX() {
		return velX;
	}

	public double getVelY() {
		return velocity.dy;
	}

	public Level getLevel() {
		return level;
	}

	public void applyForce(Vector v) {
		velocity.add(v);
		if (topSpeed < Math.abs(velocity.dx)) {
			velocity.dx = Math.signum(velocity.dx) * topSpeed;
		}
	}
	
	public void applyMovement(Vector v) {
		if (grounded) {
			applyForce(v);
		}
	}
	
	public void applyForce(double angle, double magnitude) {
		velocity.apply(angle, magnitude);
		if (topSpeed < Math.abs(velocity.dx)) {
			velocity.dx = Math.signum(velocity.dx) * topSpeed;
		}
	}
	
	public void applyMovement(double angle, double magnitude) {
		if (grounded) {
			applyForce(angle, magnitude);
		} else {
			applyForce(angle, magnitude / 1);
		}
	}
	
	public double getAngle() {
		return velocity.getAngle();
	}
	
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}
}
