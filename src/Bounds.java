import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

public class Bounds {
	private Point2D center;
	private Area area;
	private double angle;
	
	public Bounds(Shape... shapes) {
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
	
	public boolean intersects(Bounds b) {
		Area intersection = (Area) area.clone();
		intersection.intersect(b.area);
		return !intersection.isEmpty();
	}
	
	public void setCenter(Point2D p) {
		double dx = p.getX() - center.getX(), dy = p.getY() - center.getY();
		center.setLocation(p);
		area.transform(AffineTransform.getTranslateInstance(dx, dy));
	}
	
	public void setAngle(double angle) {
		area.transform(AffineTransform.getRotateInstance(angle - this.angle, center.getX(), center.getY()));
		this.angle = angle;
	}
	
	public Area getArea() {
		return area;
	}
	
	public void offset(double x, double y) {
		area.transform(AffineTransform.getTranslateInstance(x, y));
	}
	
	public boolean contains(Point2D point) {
		return area.contains(point);
	}
	
	public Bounds copy() {
		Area a = new Area();
		a.add(area);
		return new Bounds(a);
	}
}
