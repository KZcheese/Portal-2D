import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Level {
	private List<Entity> entities;
	private Point corner;

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
									if (bounds1.getCenterX() < bounds2
											.getCenterX())
										;
									// Collision on right side of player
									else
										;
									// Collision on left side of player
								} else if (vd < hd) {
									if (bounds1.getCenterY() < bounds2
											.getCenterY())
										;
									// Collision on bottom side of player
									else
										;
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
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public List<Entity> getEntities() {
		return entities;
	}

}
