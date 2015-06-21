import java.awt.geom.Rectangle2D;
/**
 * A generic unit that can jump. Supposed to designate entities that aren't blocks, such as enemies and the player.
 * TODO: Add functionality that will differentiate this from Entity. Possibly things like damage and health.
 * @author Kevin Zhan
 * @author Benjamin Hetherington
 */
public class Unit extends Entity {

	public Unit(Rectangle2D bounds) {
		super(bounds);
	}

	public Unit() {
	}

	public void jump() {
		super.jump();
	}
}
