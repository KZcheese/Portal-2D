import java.awt.geom.Point2D;
import java.util.LinkedList;

public class QuadTree {
	LinkedList<Entity> iterate = new LinkedList<Entity>();
	Node head;

	public QuadTree(Level level) {
		double x = level.getBounds().getCenterX();
		double y = level.getBounds().getCenterY();
		head = new Node(new Point2D.Double(x, y));

	}

	public void add(Entity e) {
		Node node = new Node(e);
	}

	private class Node {
		Entity value;
		Point2D p;
		Node[] next = new Node[4];

		public Node(Entity value) {
			this.value = value;
			p = value.getPosition();
		}

		public Node(Point2D p) {
			this.p = p;
		}
	}
}
