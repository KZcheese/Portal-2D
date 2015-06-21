import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Stores a game level in the form of a list of entities, a set of bounds, and a
 * point dictating the location of the camera. TODO: Implement camera system.
 * 
 * @author Kevin Zhan
 * @author Benjamin Hetherington
 */
public class Level {
	private List<Entity> entities;
	private Point corner;
	private Rectangle2D levelBounds;
	public static final double BORDER_WIDTH = 100;

	private Queue<Entity> addQueue, removeQueue;

	private BufferedImage backgroundTile;
	private Paint texture;

	private CheckPoint lastCheckPoint;

	private double timeScale;

	public Level() {
		this(new Rectangle2D.Double());
	}

	/**
	 * Bounds is the bounds of the level.
	 * 
	 * @param bounds
	 */
	public Level(Rectangle2D bounds) {
		this.entities = new LinkedList<>();
		addQueue = new LinkedList<>();
		removeQueue = new LinkedList<>();
		corner = new Point((int) bounds.getMinX(), (int) bounds.getMinY());
		this.levelBounds = bounds;
	}

	/**
	 * Sets the checkpoint that the player will respawn at when it dies.
	 * 
	 * @param p
	 */
	public void setLastCheckPoint(CheckPoint p) {
		this.lastCheckPoint = p;
	}

	public CheckPoint getLastCheckPoint() {
		return lastCheckPoint;
	}

	/**
	 * Tiles background repeatedly as the level background.
	 * 
	 * @param background
	 */
	public void setBackground(BufferedImage background) {
		backgroundTile = background;
		texture = new TexturePaint(backgroundTile, new Rectangle2D.Double(0, 0,
				background.getWidth(), background.getHeight()));
	}

	/**
	 * Updates the location of each entity in the level based on collision. Only
	 * checks collision on Entities that are effected by physics.
	 */
	public void update() {
		Entity entity;
		while (!addQueue.isEmpty()) {
			entity = addQueue.poll();
			entity.setLevel(this);
			entities.add(entity);
		}
		while (!removeQueue.isEmpty()) {
			entity = removeQueue.poll();
			entity.setLevel(null);
			entities.remove(entity);
		}

		for (Entity e : entities) {
			e.setTimeScale(timeScale);
			e.updateEntity();
			e.setGrounded(false);
			if (e.physicsEnabled()) {
				for (int i = 0; i < 1; i++) {
					// System.out.println(i);
					for (Entity e2 : entities) {
						if (e2 != e) {
							applyCollision(e, e2);
						}
					}
				}
				applyLevelCollision(e);
			}
		}
	}

	/**
	 * Checks if e1 is intersecting e2, and reacts accordingly be repositioning
	 * e1 until they are no longer overlapping. TODO: Use BigDecimal for more
	 * precise math, because double's errors cause problems with corners. Also
	 * implement penetration collision to keep extremely high speed objects from
	 * passing through eachother.
	 * 
	 * @param e1
	 * @param e2
	 */
	public void applyCollision(Entity e1, Entity e2) {
		Rectangle2D bounds1 = e1.getBounds();
		Rectangle2D bounds2 = e2.getBounds();
		if (bounds1.intersects(bounds2)) {
			// Calculate the vertical and horizontal
			// length between the centers of rectangles

			double hd = Math.abs(bounds1.getCenterX() - bounds2.getCenterX());
			double vd = Math.abs(bounds1.getCenterY() - bounds2.getCenterY());
			double hdc = hd / bounds1.getWidth() / bounds2.getWidth();
			double vdc = vd / bounds1.getHeight() / bounds2.getHeight();
			// System.out.println("1X: " +
			// bounds1.getCenterX());
			// System.out.println("1Y:" + bounds1.getCenterY());
			// System.out.println("2X: " +
			// bounds2.getCenterX());
			// System.out.println("2Y: " +
			// bounds2.getCenterY());

			// Now compare them to know the side of
			// collision

			// System.out.println("hd:" + bounds2.getWidth());
			// System.out.println("vd:" + bounds2.getHeight());
			if (hdc - 0.002095 >= vdc) {
				// System.out.println("hd: " + hd);
				// System.out.println("vd: " + vd);
				// System.out.println("hdc: " + hdc);
				// System.out.println("vdc: " + vdc);
				if (bounds1.getCenterX() < bounds2.getCenterX()) {
					if (e1.getVelX() > 0
					// && bounds1.getMaxX() == bounds2.getMinX()
					) {
						e1.resetMovementAcceleration();
						e2.collideLeft(e1);
						System.out.println("hit on right");
					} else {
						// Fell off
					}
					e1.move(-1
							* Math.abs(bounds1.getMaxX() - bounds2.getMinX()),
							0.0);
				}
				// Collision on right side of player
				else {
					if (e1.getVelX() < 0
					// && bounds1.getMinX() == bounds2.getMaxX()
					) {
						e1.resetMovementAcceleration();
						System.out.println("hit on left");
						e2.collideRight(e1);
					} else {
						// Fell off
					}
					e1.move(Math.abs(bounds1.getMinX() - bounds2.getMaxX()),
							0.0);
				}
				// Collision on left side of player
			} else {
				if (bounds1.getCenterY() < bounds2.getCenterY()) {
					if (e1.getVelY() > 0
					// && bounds1.getMaxY() == bounds2.getMinY()
					) {
						e1.resetGravity();
						e2.collideTop(e1);
						e1.resetJump();
					}
					e1.move(0.0,
							-1
									* Math.abs(bounds1.getMaxY()
											- bounds2.getMinY()));

					// Collision on bottom side of player
				} else {
					if (e1.getVelY() < 0
					// && bounds1.getMinY() == bounds2.getMaxY()
					) {
						e1.resetGravity();
						e2.collideBottom(e1);
					}
					e1.move(0.0,
							Math.abs(bounds1.getMinY() - bounds2.getMaxY()));

					// Collision on top side of player
				}
			}
		}
	}

	/**
	 * Compares the location of e to the bounds of the level, and applies
	 * collision and repositions e if it is out of the level bounds.
	 * 
	 * @param e
	 */
	public void applyLevelCollision(Entity e) {
		double leftBound = levelBounds.getMinX();
		double rightBound = levelBounds.getMaxX();
		double topBound = levelBounds.getMinY();
		double bottomBound = levelBounds.getMaxY();
		Point2D ePos = e.getLocation();
		// System.out.println(ePos);
		double eX = ePos.getX();
		double eY = ePos.getY();
		if (eX + e.getBounds().getWidth() > rightBound) {
			if (e.getVelX() > 0) {
				e.resetMovementAcceleration();
			}
			e.setLocation(rightBound - e.getBounds().getWidth(), eY);
		} else if (eX < leftBound) {
			if (e.getVelX() < 0) {
				e.resetMovementAcceleration();
			}
			e.setLocation(leftBound, eY);
		}
		if (eY < topBound) {
			if (e.getVelY() < 0) {
				e.resetGravity();
			}
			e.setLocation(eX, topBound);
		} else if (eY + e.getBounds().getHeight() > bottomBound) {
			// System.out.println(e.getVelY());
			if (e.getVelY() > 0) {
				e.resetGravity();
				e.setGrounded(true);
			}
			e.setLocation(eX, bottomBound - e.getBounds().getHeight());
			e.resetJump();
		}
	}
/**
 * Draws the background and renders all entities in the level.
 * @param g
 */
	public void render(Graphics g) {
		if (texture != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(texture);
			g2d.fill(levelBounds);
		}
		for (Entity e : entities) {
			e.render(g);
		}
	}

	public Point getCorner() {
		return corner;
	}

	public Rectangle2D getBounds() {
		return levelBounds;
	}

	public void addEntity(Entity e) {
		addQueue.offer(e);
		for (Entity e2 : e.getEntities()) {
			addEntity(e2);
		}
	}

	public void removeEntity(Entity e) {
		removeQueue.offer(e);
		for (Entity e2 : e.getEntities()) {
			removeEntity(e2);
		}
	}

	public List<Entity> getEntities() {
		return entities;
	}
/**
 * Reacts to the player "winning" or beating a level. TODO: Have this method actually do something.
 */
	public void win() {
		System.out.println("You win!");
	}
	/**
	 * Reacts to the player "losing" or getting a game over (likely from loosing too many lives). TODO: Have this method actually do something.
	 */
	public void lose() {
		System.out.println("You lose.");
	}
/**
 * Checks to see a player exists in the level.
 * @return Whether the play exists.
 */
	public boolean hasAnyLivingPlayer() {
		for (Entity e : entities) {
			if (e instanceof Player) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the timeScale, which effects how fast everything moves.
	 * @param timeScale
	 */
	public void setTimeScale(double timeScale) {
		this.timeScale = timeScale;
	}

}