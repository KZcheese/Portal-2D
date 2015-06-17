public class PortalGunTurret extends Turret {
	public PortalGunTurret() {
		super(new PortalGun(), new SpriteSheet(
				Util.readImage("portal-gun.png"), 52, 32, 1));
	}
}
