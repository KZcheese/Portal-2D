
public class Util {
	public static final double TWO_PI = 2 * Math.PI;
	
	public static double normalizeAngle(double angle) {
		return (angle % TWO_PI + TWO_PI) % TWO_PI;
	}
}
