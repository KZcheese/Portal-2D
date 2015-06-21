/**
 * A weapon can be attached to a source unit and entity. Its fire 
 * methods method can be overridden to allow customizable behavior.
 * 
 * @author Benjamin Hetherington
 *
 */
public abstract class Weapon {
	protected Unit source;
	protected Entity entity;
	protected double angle;
	protected int fireDelay;
	private boolean isFiring;
	private ActionTimer actionTimer;

	/**
	 * Constructs a weapon with a specified fire delay.
	 * 
	 * @param fireDelay
	 */
	public Weapon(int fireDelay) {
		actionTimer = new ActionTimer(fireDelay / 1000, new Action() {
			public void perform() {
				fire();
			}
		});
		setSpeed(fireDelay);
	}

	/**
	 * Returns whether or not the weapon is firing.
	 * 
	 * @return
	 */
	public boolean isFiring() {
		return isFiring;
	}

	/**
	 * Sets the source unit.
	 * 
	 * @param source
	 */
	public void setSource(Unit source) {
		this.source = source;
	}

	/**
	 * Sets the source entity.
	 * 
	 * @param entity
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Returns the source entity.
	 * 
	 * @return
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Returns the weapon's angle.
	 * 
	 * @return
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Sets the weapon's angle.
	 * 
	 * @param angle
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * Starts firing the weapon.
	 */
	public void fireStart() {
		actionTimer.start();
	}

	/**
	 * Stops firing the weapon.
	 */
	public void fireStop() {
		actionTimer.stop();
	}

	/**
	 * Fires the weapon.
	 */
	public abstract void fire();

	/**
	 * Returns the source unit.
	 * 
	 * @return
	 */
	public Unit getSource() {
		return source;
	}

	/**
	 * Returns the source entity if it is a turret, otherwise the source 
	 * unit.
	 * 
	 * @return
	 */
	public Entity getFireSource() {
		return entity instanceof Turret ? entity : source;
	}

	/**
	 * Updates the fire timer.
	 */
	public void update() {
		actionTimer.setTimeScale(source.getTimeScale());
		actionTimer.update();
	}

	/**
	 * Updates the weapon.
	 */
	public abstract void upgrade();

	/**
	 * Sets the speed of the weapon.
	 * 
	 * @param fireDelay
	 */
	public void setSpeed(int fireDelay) {
		this.fireDelay = fireDelay;
		actionTimer.setDelay(fireDelay / 30);

	}

	/**
	 * Kills the weapon.
	 */
	public void kill() {
		fireStop();
	}
}
