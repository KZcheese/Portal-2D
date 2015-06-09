import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
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
		corner = new Point();
		this.levelBounds = bounds;
	}
	
	public Level(Rectangle2D bounds) {
		entities = new LinkedList<>();
		corner = new Point();
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
							// length between the centres of rectangles

							double hd = Math
									.abs((bounds1.getCenterX() * bounds1
											.getCenterX())
											+ (bounds2.getCenterX() * bounds2
													.getCenterX()));
							double vd = Math
									.abs((bounds1.getCenterY() * bounds1
											.getCenterY())
											+ (bounds2.getCenterY() * bounds2
													.getCenterY()));

							// Now compare them to know the side of
							// collision

<<<<<<< HEAD
//							 if (hd < vd) {
//							 e.resetMovementAcceleration();
//							 if (bounds1.getCenterX() < bounds2.getCenterX())
//							 {
//							 e.move(bounds2.getMinX()
//							 - bounds1.getMaxX(), 0.0);
//							 e2.collideLeft(e);
//							 }
//							 // Collision on right side of player
//							 else {
//							 e.move(bounds1.getMinX()
//							 - bounds2.getMaxX(), 0.0);
//							 e2.collideRight(e);
//							 }
//							 // Collision on left side of player
//							 } else
							if (vd < hd) {
								e.resetGravity();
								if (bounds1.getCenterY() < bounds2.getCenterY()) {
									e.move(0.0,
											bounds2.getMinY()
													- bounds1.getMaxY());
=======
							if (hd < vd) {
								e.resetMovementAcceleration();
								if (bounds1.getCenterX() < bounds2.getCenterX()) {
									e.move(bounds1.getMaxX()
											- bounds2.getMinX(),
											bounds1.getCenterY());
									e2.collideLeft(e);
								}
								// Collision on right side of player
								else {
									e.move(bounds1.getMinX()
											- bounds2.getMaxX(),
											bounds1.getCenterY());
									e2.collideRight(e);
								}
								// Collision on left side of player
							} else if (vd < hd) {
								e.resetGravity();
								if (bounds1.getCenterY() < bounds2.getCenterY()) {
									e.move(bounds1.getCenterX(),
											bounds1.getMaxY()
													- bounds2.getMinY());
>>>>>>> origin/master
									e2.collideTop(e);
									// Collision on bottom side of player
								} else {
									e.move(bounds1.getCenterX(),
											bounds1.getMinY()
													- bounds2.getMaxY());
									e2.collideBottom(e);
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
				double eX = ePos.getX();
				double eY = ePos.getY();
				if (eX > rightBound) {
					e.resetMovementAcceleration();
<<<<<<< HEAD
					e.setLocation(rightBound - e.getBounds().getWidth(), eY);
				} else if (eX < leftBound) {
					e.resetMovementAcceleration();
					e.setLocation(leftBound, eY);
				} else if (eY < topBound) {
=======
					e.move(leftBound + e.getBounds().getWidth() / 2, eY);
				} else if (eX < leftBound) {
					e.resetMovementAcceleration();
					e.move(rightBound - e.getBounds().getWidth() / 2, eY);
				} else if (eY > topBound) {
>>>>>>> origin/master
					e.resetGravity();
					e.move(eX, topBound + e.getBounds().getHeight() / 2);
				} else {
					e.resetGravity();
					e.move(eX, bottomBound - e.getBounds().getHeight() / 2);
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
