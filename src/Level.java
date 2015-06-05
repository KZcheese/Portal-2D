import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class Level {
	private List<Entity> entities;
	private Point corner;
	
	public Level() {
		entities = new LinkedList<>();
		corner = new Point();
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
						Rectangle2D collision = null;
						if (bounds1.intersects(bounds2)) {
							{
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

								if (hd < vd) {
									e.resetMovementAcceleration();
									if (bounds1.getCenterX() < bounds2
											.getCenterX())
										e.move(bounds1.getMaxX()
												- bounds2.getMinX(), 0.0);
									// Collision on right side of player
									else
										e.move(bounds1.getMinX()
												- bounds2.getMaxX(), 0.0);
									// Collision on left side of player
								} else if (vd < hd) {
									e.resetGravity();
									if (bounds1.getCenterY() < bounds2
											.getCenterY())
										e.move(bounds1.getMaxY()
												- bounds2.getMinY(), 0.0);
									// Collision on bottom side of player
									else
										e.move(bounds1.getMinY()
												- bounds2.getMaxY(), 0.0);

									// Collision on top side of player
								}
							}
						}
					}
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
