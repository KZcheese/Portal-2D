public class CheckPoint extends Block {
	public CheckPoint(int x, int y) {
		super(x, y);
		SpriteSheet s = new SpriteSheet(Util.readImage("checkpoint.png"), 64, 64, 1);
		s.playAnimation(new SpriteSheet.Animation(0, 0, true));
		setSprite(s);
	}
	public void collideTop(Entity e) {
		super.collideTop(e);
		if (e instanceof Player) {
			getLevel().setLastCheckPoint(this);
		}
	}
}
