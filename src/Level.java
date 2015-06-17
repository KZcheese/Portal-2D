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

	public Level(Rectangle2D bounds) {
		this.entities = new LinkedList<>();
		addQueue = new LinkedList<>();
		removeQueue = new LinkedList<>();
		corner = new Point((int) bounds.getMinX(), (int) bounds.getMinY());
		this.levelBounds = bounds;
	}

	public void setLastCheckPoint(CheckPoint p) {
		this.lastCheckPoint = p;
	}

	public CheckPoint getLastCheckPoint() {
		return lastCheckPoint;
	}

	public void setBackground(BufferedImage background) {
		backgroundTile = background;
		texture = new TexturePaint(backgroundTile, new Rectangle2D.Double(0, 0,
				background.getWidth(), background.getHeight()));
	}

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
//					System.out.println(i);
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

	public void applyCollision(Entity e1, Entity e2) {
		Rectangle2D bounds1 = e1.getBounds();
		Rectangle2D bounds2 = e2.getBounds();
		if (bounds1.intersects(bounds2)) {
			// Calculate the vertical and horizontal
			// length between the centers of rectangles

			double hd = Math.abs(bounds1.getCenterX() - bounds2.getCenterX());
			double vd = Math.abs(bounds1.getCenterY() - bounds2.getCenterY());
			// System.out.println("1X: " +
			// bounds1.getCenterX());
			// System.out.println("1Y:" + bounds1.getCenterY());
			// System.out.println("2X: " +
			// bounds2.getCenterX());
			// System.out.println("2Y: " +
			// bounds2.getCenterY());
			// System.out.println("hd: " + hd);
			// System.out.println("vd: " + vd);

			// Now compare them to know the side of
			// collision

			// System.out.println("hd:" + bounds2.getWidth());
			// System.out.println("vd:" + bounds2.getHeight());
			if (hd / (bounds2.getWidth() * bounds1.getWidth()) >= vd
					/ (bounds2.getHeight() * bounds1.getHeight())) {
				if (bounds1.getCenterX() < bounds2.getCenterX()) {
					if (e1.getVelX() > 0
//							&& bounds1.getMaxX() == bounds2.getMinX()
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
//							&& bounds1.getMinX() == bounds2.getMaxX()
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
//							&& bounds1.getMaxY() == bounds2.getMinY()
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
//							&& bounds1.getMinY() == bounds2.getMaxY()
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

	public void win() {
		System.out.println("You win!");
	}

	public void lose() {
		System.out.println("You lose.");
	}

	public boolean hasAnyLivingPlayer() {
		for (Entity e : entities) {
			if (e instanceof Player) {
				return true;
			}
		}
		return false;
	}

	public void setTimeScale(double timeScale) {
		this.timeScale = timeScale;
	}

}