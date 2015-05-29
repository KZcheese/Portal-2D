import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

public class Bounds {
	private DPoint center;
	private Area area;
	private double angle;
	
	public Bounds(Shape... shapes) {
		area = new Area();
		for (Shape shape : shapes) {
			area.add(new Area(shape));
		}
		Rectangle r = area.getBounds();
		center = new DPoint(r.getCenterX(), r.getCenterY());
	}
	
	public DPoint getCenter() {
		return center;
	}
	
	public double getX() {
		return center.x;
	}
	
	public double getY() {
		return center.y;
	}
	
	public boolean intersects(Bounds b) {
		Area intersection = (Area) area.clone();
		intersection.intersect(b.area);
		return !intersection.isEmpty();
	}
	
	public void setCenter(DPoint p) {
		double dx = p.x - center.x, dy = p.y - center.y;
		center.translate(dx, dy);
		area.transform(AffineTransform.getTranslateInstance(dx, dy));
	}
	
	public void setAngle(double angle) {
		area.transform(AffineTransform.getRotateInstance(angle - this.angle, center.x, center.y));
		this.angle = angle;
	}
	
	public Area getArea() {
		return area;
	}
	
	public void offset(double x, double y) {
		area.transform(AffineTransform.getTranslateInstance(x, y));
	}
	
	public boolean contains(DPoint point) {
		return area.contains(new Point2D.Double(point.x, point.y));
	}
}
