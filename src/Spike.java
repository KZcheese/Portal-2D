
public class Spike extends Block {
	
	public Spike(int x, int y) {
		super(x, y);
	}
	
	public void collideTop(Entity e) {
		if (e instanceof Unit) {
			e.kill();
		}
	}
	
}
