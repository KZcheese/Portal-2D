import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Entity {
	private Point2D location;
	private Rectangle2D bounds;
	private Level level;
	private boolean usePhysics;
	private int jumps;
	private SpriteSheet sprite;

	private double topSpeed, velX, timeScale, velY;

	public static final double GRAVITY = 1.0, FRICTION = 0.1;

	public Entity(Rectangle2D bounds) {
		this.bounds = bounds;
		location = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
		topSpeed = 10;
		velX = 0;
		velY = 0;
		usePhysics = true;
	}
	
	public Entity(Rectangle2D bounds, SpriteSheet sprite) {
		this.bounds = bounds;
		location = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
		topSpeed = 10;
		velX = 0;
		velY = 0;
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
	}

	public void updateLocation() {

		// Gravity
		if (usePhysics) {
			velY += GRAVITY;
		}

		// Movement
		if (velX > 0) {
			velX -= FRICTION;
			if (velX < 0)
				velX = 0;
		} else {
			velX += FRICTION;
			if (velX > 0)
				velX = 0;
		}
		move(velX, velY);
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
		velY = 0;
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
			velY -= 20;
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
		velX = 0;
	}

	public void collideLeft(Entity e) {
	}

	public void collideRight(Entity e) {
	}

	public void collideTop(Entity e) {
		double signum = Math.signum(e.velX);
		double friction = signum * 1;
		e.velX -= friction;
		if (Math.signum(e.velX) != signum) {
			e.velX = 0;
		}
		
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
		return velY;
	}

	public Level getLevel() {
		return level;
	}

	public void applyForce(double x, double y) {
		velY += y;
		velX += x;
		if (topSpeed < Math.abs(velX)) {
			velX = Math.signum(velX) * topSpeed;
		}
	}
	
	public double getAngle() {
		return Util.normalizeAngle(Math.atan2(velY, velX));
	}
}
