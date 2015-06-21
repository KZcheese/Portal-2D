import java.awt.image.BufferedImage;

/**
 * A solid, dark block that cannot host portals.
 * 
 * @author Benjamin Hetherington
 *
 */
public class FrameBlock extends Block {
	/**
	 * Constructs a frame block with the specified X and Y coordinates,
	 * and the specified variation.
	 * 
	 * @param x
	 * @param y
	 * @param variation
	 */
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

	/**
	 * Constructs a frame block with the specified X and Y coordinates.
	 * 
	 * @param x
	 * @param y
	 */
	public FrameBlock(int x, int y) {
		this(x, y, 0);
	}
}