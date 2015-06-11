import java.awt.geom.Rectangle2D;

public class Unit extends Entity {
	
	public Unit(Rectangle2D bounds) {
		super(bounds);
		SpriteSheet sprite = new SpriteSheet(Util.readImage("sprite_test.png"), 48, 64, 8);
		SpriteSheet.Animation a1 = new SpriteSheet.Animation(0, 5, false);
		SpriteSheet.Animation a2 = new SpriteSheet.Animation(6, 7, true);
		sprite.playAnimation(a1);
		setSprite(sprite);
	}
	
	public Unit() {}
	
	public void jump() {
		super.jump();
	}
}
