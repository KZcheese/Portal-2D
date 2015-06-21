/**
 * A vector class that stores a horizontal and vertical component.
 * 
 * @author Benjamin Hetherington
 *
 */
public class Vector {
	public double dx, dy;

	/**
	 * Constructs a vector with a magnitude of zero.
	 */
	public Vector() {
	}

	/**
	 * Constructs a vector with the specified X and Y components.
	 * 
	 * @param dx
	 * @param dy
	 */
	public Vector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Returns the angle of the vector.
	 * 
	 * @return
	 */
	public double getAngle() {
		return Math.atan2(dy, dx);
	}

	/**
	 * Returns the magnitude of the vector.
	 * 
	 * @return
	 */
	public double getMagnitude() {
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Sets the angle and magnitude of the vector.
	 * 
	 * @param angle
	 * @param magnitude
	 */
	public void setAngleAndMagnitude(double angle, double magnitude) {
		dx = Math.cos(angle) * magnitude;
		dy = Math.sin(angle) * magnitude;
	}

	/**
	 * Adds the specified vector to the vector.
	 * 
	 * @param v
	 */
	public void add(Vector v) {
		dx += v.dx;
		dy += v.dy;
	}

	/**
	 * Adds the vector with the specified angle and magnitude to the 
	 * vector.
	 * 
	 * @param angle
	 * @param magnitude
	 */
	public void apply(double angle, double magnitude) {
		Vector v = new Vector();
		v.setAngleAndMagnitude(angle, magnitude);
		add(v);
	}

	/**
	 * Sets the angle of the vector.
	 * 
	 * @param angle
	 */
	public void setAngle(double angle) {
		double magnitude = getMagnitude();
		dx = Math.cos(angle) * magnitude;
		dy = Math.sin(angle) * magnitude;
	}

	/**
	 * Rotates the vector a specified angle.
	 * 
	 * @param angle
	 */
	public void rotate(double angle) {
		setAngle(angle - getAngle());
	}

	/**
	 * Sets the magnitude of the vector.
	 * 
	 * @param magnitude
	 */
	public void setMagnitude(double magnitude) {
		double angle = getAngle();
		dx = Math.cos(angle) * magnitude;
		dy = Math.sin(angle) * magnitude;
	}
}
