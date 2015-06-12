import java.awt.geom.Rectangle2D;


public class Player extends Unit {
	public Player() {
		super(new Rectangle2D.Double(0, 0, 48, 64));
		SpriteSheet sprite = new SpriteSheet(Util.readImage("sprite_test.png"), 48, 64, 8);
		SpriteSheet.Animation a1 = new SpriteSheet.Animation(0, 5, true);
		SpriteSheet.Animation a2 = new SpriteSheet.Animation(6, 7, true);
		sprite.playAnimation(a2);
		setSprite(sprite);
		setAnimations(new SpriteSheet.Animation[]{
			a1, a2
		});
	}
	
	public void win() {
		getLevel().win();
	}
	
	public void lose() {
		getLevel().lose();
	}
	
	public void kill() {
		super.kill();
		lose();
	}
}
