public abstract class Weapon {
	protected Unit source;
	protected Entity entity;
	protected double angle;
	protected int fireDelay;
	private boolean isFiring;
	private ActionTimer actionTimer;

	public Weapon(int fireDelay) {
		actionTimer = new ActionTimer(fireDelay / 1000, new Action() {
			public void perform() {
				fire();
			}
		});
		setSpeed(fireDelay);
	}

	public boolean isFiring() {
		return isFiring;
	}

	public void setSource(Unit source) {
		this.source = source;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void fireStart() {
		actionTimer.start();
	}

	public void fireStop() {
		actionTimer.stop();
	}

	public abstract void fire();

	public Unit getSource() {
		return source;
	}

	public Entity getFireSource() {
		return entity instanceof Turret ? entity : source;
	}

	public void update() {
		actionTimer.setTimeScale(source.getTimeScale());
		actionTimer.update();
	}

	public void upgrade() {
	}

	public void setSpeed(int fireDelay) {
		this.fireDelay = fireDelay;
		actionTimer.setDelay(fireDelay / 30);

	}

	public void kill() {
		fireStop();
	}
}
