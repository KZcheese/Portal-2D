
public class Vector {
	public double dx, dy;
	
	public Vector() {}
	
	public Vector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public double getAngle() {
		return Math.atan2(dy, dx);
	}
	
	public double getMagnitude() {
		System.out.println("dx: " + dx + " dy: " + dy);
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public void setAngleAndMagnitude(double angle, double magnitude) {
		dx = Math.cos(angle) * magnitude;
		dy = Math.sin(angle) * magnitude;
	}
	
	public void add(Vector v) {
		dx += v.dx;
		dy += v.dy;
	}
	
	public void apply(double angle, double magnitude) {
		Vector v = new Vector();
		v.setAngleAndMagnitude(angle, magnitude);
		add(v);
	}
	
	public void setAngle(double angle) {
		double magnitude = getMagnitude();
		dx = Math.cos(angle) * magnitude;
		dy = Math.sin(angle) * magnitude;
	}
	
	public void rotate(double angle) {
		setAngle(angle - getAngle());
	}
	
	public void setMagnitude(double magnitude) {
		double angle = getAngle();
		dx = Math.cos(angle) * magnitude;
		dy = Math.sin(angle) * magnitude;
	}
}

