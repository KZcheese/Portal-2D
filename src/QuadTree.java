import java.awt.geom.Point2D;
import java.util.LinkedList;

public class QuadTree {
	LinkedList<Entity> iterate = new LinkedList<Entity>();
	Node head = new Node(new Point2D.Double(0, 0));

	private class Node {
		Entity value;
		Point2D p;
		Node[] next = new Node[4];

		public Node(Entity value) {
			this.value = value;
		}

		public Node(Point2D p) {
			this.p = p;
		}
	}
}
