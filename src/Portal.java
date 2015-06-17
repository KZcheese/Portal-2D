public class Portal extends Entity {
	private Portal otherPortal;
	private int side;
	private Block block;

	public void collideTop(Entity e) {
		block.moveToSide(e, Block.TOP);
	}
}
