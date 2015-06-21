public class Spike extends Block {
/**
 * A block that acts as a set of spikes. Kill the player when hit on the spikey side.
 * TODO: Rotatable spikes. Currently the spikes only face up.
 * @param x
 * @param y
 */
	public Spike(int x, int y) {
		super(x, y);
		SpriteSheet s = new SpriteSheet(Util.readImage("spike.png"), 64, 64, 1);
		s.playAnimation(new SpriteSheet.Animation(0, 0, true));
		setSprite(s);
	}

	/**
	 * Kills the entity if it is a unit and collides on the spikes from the top.
	 */
	public void collideTop(Entity e) {
		if (e instanceof Unit) {
			e.kill();
		}
	}

}
