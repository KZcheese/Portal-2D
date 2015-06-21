/**
 * Acts as the end of the level. When the player steps on the goal block, it ends the level.
 * TODO: Add some sort of activation animation.
 * @author Kevin Zhan
 * @author Benjamin Hetherington
 */
public class Goal extends Block {
	public Goal(int x, int y) {
		super(x, y);
		SpriteSheet s = new SpriteSheet(Util.readImage("goal_block.png"), 64,
				64, 1);
		s.playAnimation(new SpriteSheet.Animation(0, 0, true));
		setSprite(s);
	}

	public void collideTop(Entity e) {
		super.collideTop(e);
		if (e instanceof Player) {
			((Player) e).win();
		}
	}
}
