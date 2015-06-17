public class PortalGunProjectile extends Projectile {
	public boolean color;

	public PortalGunProjectile(boolean color) {
		super(12, 4, new SpriteSheet(Util.readImage("blue_projectile.png"), 6,
				7, 4));
		this.color = color;
	}

	public void collide(Entity e) {
		// Do collision stuff
	}
}