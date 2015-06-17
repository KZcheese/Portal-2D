
public class SolidBlock extends Block {
	public SolidBlock(int x, int y) {
		super(x, y);
		SpriteSheet s = new SpriteSheet(Util.readImage("solid_block_2.png"), 64,
				64, 1);
		s.playAnimation(new SpriteSheet.Animation(0, 0, true));
		setSprite(s);
	}
}
