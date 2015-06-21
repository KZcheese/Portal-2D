import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Entity {
	private Point2D location;
	private Rectangle2D bounds;
	private Level level;
	private boolean usePhysics, grounded, facingRight;
	private int jumps;
	private SpriteSheet sprite;

	private double topSpeed, timeScale, angle;
	private Vector momentum, movement;

	public static final double GRAVITY = 0.8, FRICTION = 0.3, JUMP_FORCE = 18;

	private SpriteSheet.Animation[] anims;

	private List<ActionTimer> timers;
	private List<Entity> entities;

	public static final int STAND = 0, WALK = 1, JUMP = 2, TURN = 3;

	public Entity(Rectangle2D bounds) {
		this(bounds, null);
	}

	/**
	 * Constructs an entity with the specified bounds and sprite sheet.
	 * 
	 * @param bounds
	 * @param sprite
	 */
	public Entity(Rectangle2D bounds, SpriteSheet sprite) {
		this.bounds = bounds;
		location = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
		momentum = new Vector();
		movement = new Vector();
		usePhysics = true;
		facingRight = true;
		timeScale = 1;
		this.sprite = sprite;
		timers = new LinkedList<>();
		entities = new LinkedList<>();
		ActionTimer spriteTimer = new ActionTimer(2, new Action() {
			public void perform() {
				if (Entity.this.sprite != null) {
					Entity.this.sprite.update();
				}
			}
		});
		addTimer(spriteTimer);
		spriteTimer.start();
	}

	/**
	 * Returns the entity's sprite sheet.
	 * 
	 * @return
	 */
	public SpriteSheet getSprite() {
		return sprite;
	}

	/**
	 * Sets the entity's sprite sheet to the specified sprite.
	 * 
	 * @param sprite
	 */
	public void setSprite(SpriteSheet sprite) {
		this.sprite = sprite;
	}

	/**
	 * Default constructor. The entity has a null bounds, and null 
	 * sprite sheet.
	 */
	public Entity() {
		this(null);
	}

	/**
	 * Sets the location of the entity to the specified location.
	 * 
	 * @param location
	 */
	public void setLocation(Point2D location) {
		this.location = location;
	}

	/**
	 * Returns the entity's location.
	 * 
	 * @return
	 */
	public Point2D getLocation() {
		return location;
	}

	/**
	 * Sets the entity's level to the specified level.
	 * 
	 * @param l
	 */
	public void setLevel(Level l) {
		level = l;
	}

	/**
	 * Returns true if the entity's bounds intersects the specified 
	 * entity's bounds.
	 * 
	 * @param e
	 * @return
	 */
	public boolean intersects(Entity e) {
		return bounds.intersects(e.bounds);
	}

	/**
	 * Sets the entity's bounds to the specified rectangle.
	 * 
	 * @param bounds
	 */
	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds;
		bounds.setFrame(location.getX(), location.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	/**
	 * Returns the entity's bounds.
	 * 
	 * @return
	 */
	public Rectangle2D getBounds() {
		return bounds;
	}

	/**
	 * Adds a timer to the entity.
	 * 
	 * @param t
	 */
	public void addTimer(ActionTimer t) {
		timers.add(t);
	}

	/**
	 * Removes a timer from the entity.
	 * 
	 * @param t
	 */
	public void removeTimer(ActionTimer t) {
		timers.remove(t);
	}

	/**
	 * Adds the specified entity to the entity.
	 * 
	 * @param e
	 */
	public void addEntity(Entity e) {
		entities.add(e);
		if (level != null) {
			level.addEntity(e);
		}
	}

	/**
	 * Removes the specified entity from the entity.
	 * 
	 * @param e
	 */
	public void removeEntity(Entity e) {
		entities.remove(e);
		if (level != null) {
			level.removeEntity(e);
		}
	}

	/**
	 * Updates the entity only if the level is not null.
	 */
	public void updateEntity() {
		if (getLevel() != null) {
			update();
		}
	}

	/**
	 * Updates the entity.
	 */
	public void update() {
		updateLocation();
		for (ActionTimer t : timers) {
			t.setTimeScale(timeScale);
			t.update();
		}
		for (Entity e : entities) {
			e.update();
		}

		if (movement.dx == 0 && grounded) {
			playAnimation(STAND);
		} else if (movement.dx != 0 && grounded && sprite.isDone()) {
			playAnimation(WALK);
		}
		if (movement.dx > 0) {
			if (!facingRight) {
				facingRight = true;
			}
		}
		if (movement.dx < 0) {
			if (facingRight) {
				facingRight = false;
			}
		}
	}

	/**
	 * Updates the location of the entity according to its X and Y velocities,
	 * and updates its velocities according to external forces such as gravity
	 * and air friction.
	 */
	public void updateLocation() {
		// Gravity
		if (usePhysics) {
			momentum.dy += GRAVITY * timeScale;
		}

		// Friction
		applyFriction(FRICTION);

		// Movement
		move((momentum.dx + movement.dx) * timeScale,
				(momentum.dy + movement.dy) * timeScale);
	}

	/**
	 * Reduces an entity's momentum by a specified amount.
	 * 
	 * @param friction
	 */
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
	 * 
	 * @param dx
	 * @param dy
	 */
	public void move(double dx, double dy) {
		location.setLocation(location.getX() + dx, location.getY() + dy);
		bounds.setFrame(location.getX(), location.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	/**
	 * Sets the X and Y coordinates of the entity to the specified coordinates.
	 * 
	 * @param x
	 * @param x
	 */
	public void setLocation(double x, double y) {
		move(x - location.getX(), y - location.getY());
	}

	/**
	 * Resets the vertical velocity component of the entity to zero.
	 */
	public void resetGravity() {
		momentum.dy = 0;
		movement.dy = 0;
	}

	/**
	 * Renders the entity.
	 * 
	 * @param g
	 */
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
			if (facingRight) {
				g.drawImage(sprite.getCurrentFrame(), dx, dy, null);
			} else {
				BufferedImage image = Util.toBufferedImage(sprite
						.getCurrentFrame());
				AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
				at.translate(-image.getWidth(), 0);
				AffineTransformOp op = new AffineTransformOp(at, null);
				g.drawImage(op.filter(image, null), dx, dy, null);
			}
		}
	}

	/**
	 * Applies a small vertical force to the entity.
	 */
	public void jump() {
		if (jumps < 1) {
			momentum.dy -= 20;
			jumps++;
		}
//		else momentum.dy = 20;
		playAnimation(JUMP);
	}

	/**
	 * Resets the number of jumps performed to zero.
	 */
	public void resetJump() {
		jumps = 0;
	}

	/**
	 * Returns whether or not the entity's physics mode is enabled.
	 * 
	 * @return
	 */
	public boolean physicsEnabled() {
		return usePhysics;
	}

	/**
	 * Enables or disables physics for the entity.
	 * 
	 * @param physics
	 */
	public void enablePhysics(boolean physics) {
		usePhysics = physics;
	}

	/**
	 * Sets the entity's time scale.
	 * 
	 * @param timeScale
	 */
	public void setTimeScale(double timeScale) {
		this.timeScale = timeScale;
	}

	/**
	 * Returns the entity's time scale.
	 * 
	 * @return
	 */
	public double getTimeScale() {
		return timeScale;
	}

	/**
	 * Resets the horizontal velocity component of the entity to zero.
	 */
	public void resetMovementAcceleration() {
		movement.dx = 0;
		momentum.dx = 0;
	}

	/**
	 * This method is performed when an entity collides with its left 
	 * side.
	 * 
	 * @param e
	 */
	public void collideLeft(Entity e) {
	}

	/**
	 * This method is performed when an entity collides with its right 
	 * side.
	 * 
	 * @param e
	 */
	public void collideRight(Entity e) {
	}

	/**
	 * This method is performed when an entity collides with its top 
	 * side.
	 * 
	 * @param e
	 */
	public void collideTop(Entity e) {
		double signum = Math.signum(e.momentum.dx);
		double friction = signum * 1;
		e.momentum.dx -= friction * timeScale;
		if (Math.signum(e.momentum.dx) != signum) {
			e.momentum.dx = 0;
		}
		e.setGrounded(true);
	}

	/**
	 * This method is performed when an entity collides with its bottom 
	 * side.
	 * 
	 * @param e
	 */
	public void collideBottom(Entity e) {
	}

	/**
	 * Kills the entity.
	 */
	public void kill() {
		level.removeEntity(this);
	}

	/**
	 * Returns the horizontal component of the velocity.
	 * 
	 * @return
	 */
	public double getVelX() {
		return momentum.dx + movement.dx;
	}

	/**
	 * Returns the vertical component of the velocity.
	 * 
	 * @return
	 */
	public double getVelY() {
		return momentum.dy + movement.dy;
	}

	/**
	 * Returns the entity's level.
	 * 
	 * @return
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Applies a force to the entity's momentum velocity.
	 * 
	 * @param v
	 */
	public void applyForce(Vector v) {
		momentum.add(v);
	}

	/**
	 * Applies a force to the entity's movement velocity.
	 * @param v
	 */
	public void applyMovement(Vector v) {
		if (grounded) {
			movement.add(v);
			double speed = topSpeed * timeScale;
			if (speed < Math.abs(movement.dx)) {
				movement.dx = Math.signum(movement.dx) * speed;
			}
		}
	}

	/**
	 * Applies a force to the entity's momentum velocity.
	 * 
	 * @param angle
	 * @param magnitude
	 */
	public void applyForce(double angle, double magnitude) {
		momentum.apply(angle, magnitude);
	}

	/**
	 * Applies a force to the entity's movement velocity.
	 * 
	 * @param angle
	 * @param magnitude
	 */
	public void applyMovement(double angle, double magnitude) {
		if (grounded) {
			movement.apply(angle, magnitude * timeScale);
			double speed = topSpeed * timeScale;
			if (speed < Math.abs(movement.dx)) {
				movement.dx = Math.signum(movement.dx) * speed;
			}
		} else {
			movement.apply(angle, magnitude * timeScale);
			double speed = topSpeed * timeScale;
			if (speed < Math.abs(movement.dx)) {
				movement.dx = Math.signum(movement.dx) * speed;
			}
		}
	}

	/**
	 * Returns the angle of the entity's momentum.
	 * @return
	 */
	public double getAngle() {
		return momentum.getAngle();
	}

	/**
	 * Sets the grounded state of the entity.
	 * 
	 * @param grounded
	 */
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	/**
	 * Sets the animation set of the entity.
	 * 
	 * @param anims
	 */
	public void setAnimations(SpriteSheet.Animation[] anims) {
		this.anims = anims;
	}

	/**
	 * Forces the entity's sprite to play the specified animation.
	 * 
	 * @param anim
	 */
	public void playAnimation(int anim) {
		if (sprite != null) {
			if (-1 < anim && anim < anims.length) {
				sprite.playAnimation(anims[anim]);
			}
		}
	}

	/**
	 * Returns the entity's momentum vector.
	 * 
	 * @return
	 */
	public Vector getMomentum() {
		return momentum;
	}

	/**
	 * Returns the entity's net momentum vector.
	 * 
	 * @return
	 */
	public Vector getNetMomentum() {
		Vector v = new Vector(momentum.dx, momentum.dy);
		v.add(movement);
		return v;
	}

	/**
	 * Returns the list of entity's attached to the entity.
	 * 
	 * @return
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * Returns the specified hardpoint relative to the entity.
	 * 
	 * @param point
	 * @return
	 */
	public Point2D getHardPoint(int point) {
		if (sprite == null) {
			return null;
		}
		Point2D hardPoint = sprite.getHardPoint(point);
		return new Point2D.Double(location.getX() + hardPoint.getX(),
				location.getY() + hardPoint.getY());
	}

	/**
	 * Returns the grounded state of the entity.
	 * 
	 * @return
	 */
	public boolean isGrounded() {
		return grounded;
	}

	/**
	 * Sets the entity's top speed to the specified value.
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed) {
		this.topSpeed = speed;
	}

	/**
	 * Sets the entity's angle to the specified value.
	 * 
	 * @param angle
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * Rotates all attached turrets to face the specified point.
	 * 
	 * @param point
	 */
	public void rotateTurrets(Point2D point) {
		for (Entity e : entities) {
			if (e instanceof Turret) {
				e.setAngle(0);
			}
		}
	}
}