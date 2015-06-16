import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Entity {
	private Point2D location;
	private Rectangle2D bounds;
	private Level level;
	private boolean usePhysics, grounded;
	private int jumps;
	private SpriteSheet sprite;

	private double topSpeed, timeScale;
	private Vector momentum, movement;

	public static final double GRAVITY = 0.1, FRICTION = 0.4, JUMP_FORCE = 18;
	
	private SpriteSheet.Animation[] anims;
	
	public static final int
		STAND = 0,
		WALK = 2,
		JUMP = 1;

	public Entity(Rectangle2D bounds) {
		this(bounds, null);
	}
	
	public Entity(Rectangle2D bounds, SpriteSheet sprite) {
		this.bounds = bounds;
		location = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
		topSpeed = 8;
		momentum = new Vector();
		movement = new Vector();
		usePhysics = true;
		timeScale = 1;
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

	/**
	 * Updates the entity only if the level is not null.
	 */
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

	/**
	 * Updates the location of the entity according to its X and Y
	 * velocities, and updates its velocities according to external
	 * forces such as gravity and air friction.
	 */
	public void updateLocation() {
		// Gravity
		if (usePhysics) {
			momentum.dy += GRAVITY * timeScale;
		}

		// Movement
		applyFriction(FRICTION);
		
		move(momentum.dx + movement.dx, momentum.dy + movement.dy);
	}
	
	public void applyFriction(double friction) {
		double temp1 = momentum.dx;
		momentum.dx -= Math.signum(momentum.dx) * friction * timeScale;
		if (Math.signum(temp1) != Math.signum(momentum.dx)) {
			momentum.dx = 0;
		}
		double temp2 = movement.dx;
		movement.dx -= Math.signum(movement.dx) * friction * timeScale;
		if (Math.signum(temp2) != Math.signum(movement.dx)) {
			movement.dx = 0;
		}
	}

	/**
	 * Translates the entity a specified X and Y distance.
	 * @param dx X distance to translate
	 * @param dy Y distance to translate
	 */
	public void move(double dx, double dy) {
		location.setLocation(location.getX() + dx, location.getY() + dy);
		bounds.setFrame(location.getX(), location.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	/**
	 * Sets the X and Y coordinates of the entity to the specified
	 * coordinates.
	 * @param x X coordinate
	 * @param x Y coordinate
	 */
	public void setLocation(double x, double y) {
		move(x - location.getX(), y - location.getY());
	}

	public void resetGravity() {
		momentum.dy = 0;
		movement.dy = 0;
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
			momentum.dy -= 21 * timeScale;
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
		movement.dx = 0;
		momentum.dx = 0;
	}

	public void collideLeft(Entity e) {
	}

	public void collideRight(Entity e) {
	}

	public void collideTop(Entity e) {
		double signum = Math.signum(e.momentum.dx);
		double friction = signum * 1;
		e.momentum.dx -= friction * timeScale;
		if (Math.signum(e.momentum.dx) != signum) {
			e.momentum.dx = 0;
		}
		e.setGrounded(true);
	}

	public void collideBottom(Entity e) {
	}

	public void kill() {
		level.removeEntity(this);
	}

	public double getVelX() {
		return momentum.dx + movement.dx;
	}

	public double getVelY() {
		return momentum.dy;
	}

	public Level getLevel() {
		return level;
	}

	public void applyForce(Vector v) {
		momentum.add(v);
	}
	
	public void applyMovement(Vector v) {
		if (grounded) {
			movement.add(v);
			double speed = topSpeed * timeScale;
			if (speed < Math.abs(movement.dx)) {
				movement.dx = Math.signum(movement.dx) * speed;
			}
		}
	}
	
	public void applyForce(double angle, double magnitude) {
		momentum.apply(angle, magnitude);
	}
	
	public void applyMovement(double angle, double magnitude) {
		if (grounded) {
			movement.apply(angle, magnitude);
			double speed = topSpeed * timeScale;
			if (speed < Math.abs(movement.dx)) {
				movement.dx = Math.signum(movement.dx) * speed;
			}
		} else {
			movement.apply(angle, magnitude);
			double speed = topSpeed * timeScale;
			if (speed < Math.abs(movement.dx)) {
				movement.dx = Math.signum(movement.dx) * speed;
			}
		}
	}
	
	public double getAngle() {
		return momentum.getAngle();
	}
	
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}
	
	public void setAnimations(SpriteSheet.Animation[] anims) {
		this.anims = anims;
	}
	
	public void playAnimation(int anim) {
		if (sprite != null) {
			if (-1 < anim && anim < anims.length) {
				sprite.playAnimation(anims[anim]);
			}
		}
	}
	
	public Vector getMomentum() {
		return momentum;
	}
	
	public Vector getNetMomentum() {
		Vector v = new Vector(momentum.dx, momentum.dy);
		v.add(movement);
		return v;
	}
}
