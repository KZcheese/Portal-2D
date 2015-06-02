import java.awt.geom.Point2D;
import java.util.LinkedList;

public class QuadTree {
	LinkedList<Node> iterate = new LinkedList<Node>();
	Node head;

	public QuadTree(Level level) {
		double centerX = level.getBounds().getCenterX();
		double centerY = level.getBounds().getCenterY();
		head = new Node(new Point2D.Double(centerX, centerY));
	}

	public void update() {
		for (Node n : iterate)
			n.p = n.value.getPosition();
	}

	public void add(Entity e) {
		Node node = new Node(e);
		Node otherNode = head;
		int nextIndex = -1;
		while (nextIndex < 0 || otherNode.next[nextIndex] != null) {
			nextIndex = compare(otherNode, node);
		}
		otherNode.next[nextIndex] = node;
	}

	private int compare(Node a, Node b) {
		double aX = a.p.getX(), aY = a.p.getX(), bX = b.p.getX(), bY = b.p
				.getX();
		if (bX > aX) {
			if (bY > aY)
				return 0;
			return 3;
		}
		if (bY > aY)
			return 1;
		return 2;
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
