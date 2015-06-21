/**
 * A solid block that acts as a checkpoint. When the dies, they are respawned
 * back at the last checkpoint they touched. TODO: Add in an activation
 * animation and possibly special effects on the player such as a heal or a
 * portal reset.
 * TODO: Add an activation animation.
 * @author Kevin Zhan
 * @author Benjamin Hethington
 */
public class CheckPoint extends Block {
	public CheckPoint(int x, int y) {
		super(x, y);
		SpriteSheet s = new SpriteSheet(Util.readImage("checkpoint.png"), 64,
				64, 1);
		s.playAnimation(new SpriteSheet.Animation(0, 0, true));
		setSprite(s);
	}
/**
 * Sets itself as the last checkpoint when collided from above by an entity.
 */
	public void collideTop(Entity e) {
		super.collideTop(e);
		if (e instanceof Player) {
			getLevel().setLastCheckPoint(this);
		}
	}
}
