import java.awt.image.BufferedImage;

public class FrameBlock extends Block {
	public FrameBlock(int x, int y, int variation) {
		super(x, y);
		BufferedImage image = null;
		switch (variation) {
		case 0:
			image = Util.readImage("block_1.png");
			break;
		case 1:
			image = Util.readImage("block_2.png");
		}
		SpriteSheet s = new SpriteSheet(image, 64, 64, 1);
		s.playAnimation(new SpriteSheet.Animation(0, 0, true));
		setSprite(s);
	}

	public FrameBlock(int x, int y) {
		this(x, y, 0);
	}
}