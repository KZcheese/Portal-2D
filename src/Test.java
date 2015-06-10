import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		@SuppressWarnings("serial")
		class Renderer extends JComponent {
			public Level level;

			public Renderer(Level level) {
				this.level = level;
			}

			public void paintComponent(Graphics g) {
				level.render(g);
			}
		}

		final Level l = new Level(new Rectangle2D.Double(0, 0, 16 * Block.SIZE, 12 * Block.SIZE));
		final Renderer r = new Renderer(l);

		final Entity entity = new Entity(new Rectangle2D.Double(25, 10, 20, 35));
		// entity.pushForward();

		final Controller c = new Controller(entity);
		frame.addKeyListener(c);

		l.addEntity(new Block(0, 11));
		l.addEntity(new Block(2, 10));
		l.addEntity(new Block(4, 9));
		final Block b = new Block(4, 1);
		l.addEntity(b);

		final int[] a = { 0 };
		Timer t = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.update();
				r.repaint();
				c.update();
				b.jump();
				// System.out.println(entity.getLocation());
				a[0]++;
			}
		});

		// frame.setPreferredSize(new Dimension(640, 480));
		r.setPreferredSize(new Dimension(16 * Block.SIZE, 12 * Block.SIZE));
		frame.getContentPane().add(r);

		t.start();
		l.addEntity(entity);

		frame.pack();
		frame.setVisible(true);
	}

}
