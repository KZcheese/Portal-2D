/**
 * Two connected portals allow objects that enter on Portal to come out the
 * other. Momentum is retained through portals, so
 * "fast thing goes in fast thing goes out"(GLaDOS).
 * TODO: Everything
 * @author Kevin Zhan
 * @author Benjamin Hetherington
 */
public class Portal extends Entity {
	private Portal otherPortal;
	private int side;
	private Block block;

	public static final boolean BLUE = false, ORANGE = true;

	public void collideTop(Entity e) {
		block.moveToSide(e, Block.TOP);
	}
}