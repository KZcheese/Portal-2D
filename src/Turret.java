/**
 * An entity that is holds a weapon and is attached to another entity.
 * 
 * @author Benjamin Hetherington
 *
 */
public abstract class Turret extends Entity {
	private Unit host;
	private Weapon weapon;
	private int attachment;

	/**
	 * Constructs a turret with the specified weapon and sprite.
	 * 
	 * @param weapon
	 * @param sprite
	 */
	public Turret(Weapon weapon, SpriteSheet sprite) {
		setSprite(sprite);
		this.weapon = weapon;
		if (weapon != null) {
			this.weapon.setEntity(this);
		}

	}

	/**
	 * Returns whether or not the turret is firing.
	 * 
	 * @return
	 */
	public boolean isFiring() {
		return weapon != null && weapon.isFiring();
	}

	/**
	 * Sets the host unit of the turret.
	 * 
	 * @param host
	 * @param attachment
	 */
	public void setHost(Unit host, int attachment) {
		this.host = host;
		this.attachment = attachment;
		setLevel(host.getLevel());
		if (weapon != null) {
			weapon.setSource(host);
		}
	}

	/**
	 * Returns the weapon of the turret.
	 * 
	 * @return
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Updates the turret.
	 */
	public void update() {
		super.update();
		setLocation(host.getSprite().getHardPoint(attachment));
		weapon.update();
	}

	/**
	 * Sets the turret's weapon.
	 * 
	 * @param weapon
	 */
	public void setWeapon(Weapon weapon) {
		if (this.weapon != null) {
			this.weapon.fireStop();
		}
		this.weapon = weapon;
		this.weapon.setSource(host);
		this.weapon.setEntity(this);
	}

	/**
	 * Starts attacking.
	 */
	public void attackStart() {
		if (weapon != null) {
			weapon.fireStart();
		}
	}

	/**
	 * Stops attacking.
	 */
	public void attackStop() {
		if (weapon != null) {
			weapon.fireStop();
		}
	}

	/**
	 * Returns the turret's host unit.
	 * @return
	 */
	public Unit getHost() {
		return host;
	}
}