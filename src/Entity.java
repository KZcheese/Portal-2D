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

	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds;
		bounds.setFrame(location.getX(), location.getY(), bounds.getWidth(),
				bounds.getHeight());
	}

	public Rectangle2D getBounds() {
		return bounds;
	}

	public void addTimer(ActionTimer t) {
		timers.add(t);
	}

	public void removeTimer(ActionTimer t) {
		timers.remove(t);
	}

	public void addEntity(Entity e) {
		entities.add(e);
		if (level != null) {
			level.addEntity(e);
		}
	}

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

		// Movement
		applyFriction(FRICTION);

		move((momentum.dx + movement.dx) * timeScale,
				(momentum.dy + movement.dy) * timeScale);
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
	 * 
	 * @param dx
	 *            X distance to translate
	 * @param dy
	 *            Y distance to translate
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
	 *            X coordinate
	 * @param x
	 *            Y coordinate
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

	public void jump() {
		if (jumps < 1) {
			momentum.dy -= 20;
			jumps++;
		}
		playAnimation(JUMP);
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

	public List<Entity> getEntities() {
		return entities;
	}

	public Point2D getHardPoint(int point) {
		if (sprite == null) {
			return null;
		}
		Point2D hardPoint = sprite.getHardPoint(point);
		return new Point2D.Double(location.getX() + hardPoint.getX(),
				location.getY() + hardPoint.getY());
	}

	public boolean isGrounded() {
		return grounded;
	}

	public void setSpeed(double speed) {
		this.topSpeed = speed;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void rotateTurrets(Point2D point) {
		for (Entity e : entities) {
			if (e instanceof Turret) {
				e.setAngle(0);
			}
		}
	}
}