import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Level {
	private List<Entity> entities;

	public void update() {
		for (Entity e : entities) {
			Point2D previous = e.getLocation();
			e.updateEntity();
			Point2D location = e.getLocation();
			if (e.physicsEnabled()) {
				Rectangle2D bounds1 = e.getBounds();
				Rectangle2D bounds2 = e.getBounds();
			}
		}
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
