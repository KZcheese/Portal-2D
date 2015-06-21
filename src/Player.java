import java.awt.geom.Rectangle2D;

/**
 * This unit acts as the playable character.
 * 
 * @author Benjamin Hetherington
 *
 */
public class Player extends Unit {
	/**
	 * Constructs a player.
	 */
	public Player() {
		super(new Rectangle2D.Double(0, 0, 80, 120));
		SpriteSheet sprite = new SpriteSheet(Util.readImage("mario.png"), 80,
				120, 28);
		SpriteSheet.Animation a0 = new SpriteSheet.Animation(4, 4, false), a1 = new SpriteSheet.Animation(
				1, 4, true), a2 = new SpriteSheet.Animation(5, 6, false, 0.5), a3 = new SpriteSheet.Animation(
				13, 13, false, 0.7);
		sprite.playAnimation(a1);
		setSprite(sprite);
		setAnimations(new SpriteSheet.Animation[] { a0, a1, a2, a3 });
		setSpeed(23);
	}

	/**
	 * Wins the current level.
	 */
	public void win() {
		getLevel().win();
	}

	/**
	 * Loses the current level.
	 */
	public void lose() {
		getLevel().lose();
	}

	/**
	 * Kills the player. Rather than being removed, the player is 
	 * teleported to their last checkpoint.
	 */
	public void kill() {
		CheckPoint cp = getLevel().getLastCheckPoint();
		if (cp == null) {
			lose();
			super.kill();
		} else {
			cp.moveToSide(this, Block.TOP);
			resetGravity();
			resetMovementAcceleration();
		}
	}
}