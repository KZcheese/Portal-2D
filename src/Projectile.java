import java.awt.geom.Rectangle2D;

public abstract class Projectile extends Entity {

	public Projectile(double speed, double radius, SpriteSheet sprite) {
		setBounds(new Rectangle2D.Double(0, 0, radius, radius));
	}

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