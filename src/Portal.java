public class Portal extends Entity {
	private Portal otherPortal;
	private int side;
	private Block block;

	public static final boolean BLUE = false, ORANGE = true;

	public void collideTop(Entity e) {
		block.moveToSide(e, Block.TOP);
	}
}