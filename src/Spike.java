public class Spike extends Block {

	public Spike(int x, int y) {
		super(x, y);
		SpriteSheet s = new SpriteSheet(Util.readImage("spike.png"), 64, 64, 1);
		s.playAnimation(new SpriteSheet.Animation(0, 0, true));
		setSprite(s);
	}

	public void collideTop(Entity e) {
		if (e instanceof Unit) {
			e.kill();
		}
	}

}
