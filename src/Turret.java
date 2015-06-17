import java.awt.image.BufferedImage;

public abstract class Turret extends Entity {
	private Unit host;
	private Weapon weapon;
	private int attachment;

	public Turret(Weapon weapon, SpriteSheet sprite) {
		setSprite(sprite);
		this.weapon = weapon;
		if (weapon != null) {
			this.weapon.setEntity(this);
		}

	}

	public boolean isFiring() {
		return weapon != null && weapon.isFiring();
	}

	public void setHost(Unit host, int attachment) {
		this.host = host;
		this.attachment = attachment;
		setLevel(host.getLevel());
		if (weapon != null) {
			weapon.setSource(host);
		}
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void update() {
		super.update();
		setLocation(host.getSprite().getHardPoint(attachment));
		weapon.update();
	}

	public void setWeapon(Weapon weapon) {
		if (this.weapon != null) {
			this.weapon.fireStop();
		}
		this.weapon = weapon;
		this.weapon.setSource(host);
		this.weapon.setEntity(this);
	}

	public void attackStart() {
		if (weapon != null) {
			weapon.fireStart();
		}
	}

	public void attackStop() {
		if (weapon != null) {
			weapon.fireStop();
		}
	}

	public Unit getHost() {
		return host;
	}
}
