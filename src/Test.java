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

		final Level l = new Level(new Rectangle2D.Double(0, 0, 16 * Block.SIZE,
				12 * Block.SIZE));
		final Renderer r = new Renderer(l);

		final Entity entity = new Player();
		final Controller c = new Controller(entity);
		frame.addKeyListener(c);

		for (int i = 0; i < 16; i++) {
			if (i == 8) {
				l.addEntity(new Goal(i, 11));
			} else {
				l.addEntity(new SolidBlock(i, 11));
			}
		}

		l.addEntity(new SolidBlock(5, 9));
		l.addEntity(new SolidBlock(4, 9));
		
		for (int i = 7; i < 14; i++) {
			l.addEntity(new SolidBlock(i, 8));
		}
		
		l.addEntity(new Spike(3, 10));
		l.addEntity(new Spike(6, 10));
		l.addEntity(new Spike(8, 7));
		l.addEntity(new Spike(9, 7));
		l.addEntity(new Spike(13, 7));
		
		l.addEntity(new SolidBlock(11, 7));
		l.addEntity(new SolidBlock(11, 6));
		l.addEntity(new SolidBlock(11, 5));

		final int[] a = { 0 };
		Timer t = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.update();
				r.repaint();
				c.update();
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
