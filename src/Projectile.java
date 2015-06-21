import java.awt.geom.Rectangle2D;

/**
 * An entity that is given an initial velocity, and travels in the
 * velocity until it hits another entity.
 * 
 * @author Benjamin Hetherington
 *
 */
public abstract class Projectile extends Entity {

	/**
	 * Constructs a projectile with an initial speed, radius, and 
	 * sprite.
	 * 
	 * @param speed
	 * @param radius
	 * @param sprite
	 */
	public Projectile(double speed, double radius, SpriteSheet sprite) {
		setBounds(new Rectangle2D.Double(0, 0, radius, radius));
	}

	/**
	 * This method is called when the projectile collides with another 
	 * entity.
	 * 
	 * @param e
	 */
	public abstract void collide(Entity e);

	public void collideTop(Entity e) {
		collide(e);
	}

	public void collideBottom(Entity e) {
		collide(e);
	}

	public void collideLeft(Entity e) {
		collide(e);
	}

	public void collideRight(Entity e) {
		collide(e);
	}

}