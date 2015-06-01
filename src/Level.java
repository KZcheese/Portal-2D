import java.awt.geom.Rectangle2D;
import java.util.List;


public class Level {
	private List<Entity> entities;
	private Rectangle2D bounds;
	
	public Level(Rectangle2D bounds) {
		this.bounds = bounds;
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
	
	public Rectangle2D getBounds() {
		return bounds;
	}
}


