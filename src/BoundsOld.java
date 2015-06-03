import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

public class BoundsOld {
	private Point2D center;
	private Area area;
	private double angle;
	
	public BoundsOld(Shape... shapes) {
		area = new Area();
		for (Shape shape : shapes) {
			area.add(new Area(shape));
		}
		Rectangle r = area.getBounds();
		center = new Point2D.Double(r.getCenterX(), r.getCenterY());
	}
	
	public Point2D getCenter() {
		return center;
	}
	
	public double getX() {
		return center.getX();
	}
	
	public double getY() {
		return center.getY();
	}
	
	public boolean intersects(BoundsOld b) {
		Area intersection = (Area) area.clone();
		intersection.intersect(b.area);
		return !intersection.isEmpty();
	}
	
	public void setCenter(Point2D p) {
		double dx = p.getX() - center.getX(), dy = p.getY() - center.getY();
		center.setLocation(center.getX() + dx, center.getY() + dy);
		area.transform(AffineTransform.getTranslateInstance(dx, dy));
	}
	
	/**
	 * Rotates the bounds to face the specified angle.
	 * @param angle The angle of the bounds
	 */
	public void setAngle(double angle) {
		area.transform(AffineTransform.getRotateInstance(angle - this.angle, center.getX(), center.getY()));
		this.angle = angle;
	}
	
	/**
	 * Returns the underlying area of the bounds.
	 * @return The area of the bounds
	 */
	public Area getArea() {
		return area;
	}
	
	/**
	 * Translates the bounds a specified 
	 * @param dx The X distance to translate
	 * @param dy The Y distance to translate
	 */
	public void translate(double dx, double dy) {
		area.transform(AffineTransform.getTranslateInstance(dx, dy));
	}
	
	/**
	 * Returns true if the bounds contains the specified point, and
	 * false otherwise.
	 * @param point The point to check
	 * @return Whether or not the bounds contains the point
	 */
	public boolean contains(Point2D point) {
		return area.contains(point);
	}
}
