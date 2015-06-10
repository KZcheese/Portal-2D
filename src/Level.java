import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class Level {
	private List<Entity> entities;
	private Point corner;
	private Rectangle2D levelBounds;
	public static final double BORDER_WIDTH = 100;

	public Level() {
		this(new LinkedList<Entity>(), new Rectangle2D.Double());
	}

	public Level(LinkedList<Entity> entities, Rectangle2D bounds) {
		this.entities = entities;
		corner = new Point((int) bounds.getMinX(), (int) bounds.getMinY());
		this.levelBounds = bounds;
	}

	public Level(Rectangle2D bounds) {
		entities = new LinkedList<>();
		corner = new Point((int) bounds.getMinX(), (int) bounds.getMinY());
		levelBounds = bounds;
	}

	public void update() {
		for (Entity e : entities) {
			Point2D previous = e.getLocation();
			e.updateEntity();
			Point2D location = e.getLocation();
			if (e.physicsEnabled()) {
				Rectangle2D bounds1 = e.getBounds();
				for (Entity e2 : entities) {
					if (e2 != e) {
						Rectangle2D bounds2 = e2.getBounds();
						if (bounds1.intersects(bounds2)) {
							// Calculate the vertical and horizontal
							// length between the centers of rectangles

							double hd = Math.abs(bounds1.getCenterX()
									- bounds2.getCenterX());
							double vd = Math.abs(bounds1.getCenterY()
									- bounds2.getCenterY());
//							System.out.println("hd: " + hd);
//							System.out.println("vd: " + vd);

							// Now compare them to know the side of
							// collision

							if (hd / bounds2.getWidth() >= vd
									/ bounds2.getHeight()) {
								if (bounds1.getCenterX() < bounds2.getCenterX()) {
									e.move(-1
											* Math.abs(bounds1.getMaxX()
													- bounds2.getMinX()), 0.0);
									e2.collideLeft(e);
									if (Math.abs(e.getAngle()) < Math.PI / 2) {
										e.resetMovementAcceleration();
									} else {
										// Fell off
									}
								}
								// Collision on right side of player
								else {
									e.move(Math.abs(bounds1.getMinX()
											- bounds2.getMaxX()), 0.0);
									e2.collideRight(e);
									if (Math.abs(e.getAngle()) >= Math.PI / 2) {
										e.resetMovementAcceleration();
									} else {
										// Fell off
									}
								}
								// Collision on left side of player
							} else {
								if (bounds1.getCenterY() < bounds2.getCenterY()) {
									e.move(0.0,
											-1
													* Math.abs(bounds1
															.getMaxY()
															- bounds2.getMinY()));
									e2.collideTop(e);
									e.resetJump();
									if (Math.abs(e.getTotalAngle()) > Math.PI) {
										e.resetGravity();
									}
									// Collision on bottom side of player
								} else {
									e.move(0.0,
											Math.abs(bounds1.getMinY()
													- bounds2.getMaxY()));
									e2.collideBottom(e);
									if (Math.abs(e.getTotalAngle()) <= Math.PI) {
										e.resetGravity();
									}
									// Collision on top side of player
								}
							}
						}
					}
				}
				double leftBound = levelBounds.getMinX();
				double rightBound = levelBounds.getMaxX();
				double topBound = levelBounds.getMinY();
				double bottomBound = levelBounds.getMaxY();
				Point2D ePos = e.getLocation();
				// System.out.println(ePos);
				double eX = ePos.getX();
				double eY = ePos.getY();
				if (eX + e.getBounds().getWidth() > rightBound) {
					e.resetMovementAcceleration();
					e.setLocation(rightBound - e.getBounds().getWidth(), eY);
				} else if (eX < leftBound) {
					e.resetMovementAcceleration();
					e.setLocation(leftBound, eY);
				}
				if (eY < topBound) {
					e.resetGravity();
					e.setLocation(eX, topBound);
				} else if (eY + e.getBounds().getHeight() > bottomBound) {
					e.resetGravity();
					e.setLocation(eX, bottomBound - e.getBounds().getHeight());
					e.resetJump();
				}
			}
		}
	}

	public void render(Graphics g) {
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
		entities.add(e);
		e.setLevel(this);
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
		e.setLevel(null);
	}

	public List<Entity> getEntities() {
		return entities;
	}

}
