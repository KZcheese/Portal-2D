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

		final Level l = new Level(new Rectangle2D.Double(0, 0, 640, 480));
		final Renderer r = new Renderer(l);

		final Entity entity = new Entity(new Rectangle2D.Double(25, 10, 20, 35));
		// entity.pushForward();

		final Controller c = new Controller(entity);
		frame.addKeyListener(c);

		Entity wall1 = new Entity(new Rectangle2D.Double(0, 460, 100, 2));
		wall1.enablePhysics(false);
		l.addEntity(wall1);
		
		Entity wall2 = new Entity(new Rectangle2D.Double(80, 380, 100, 2));
		wall2.enablePhysics(false);
		l.addEntity(wall2);
		
		Entity wall3 = new Entity(new Rectangle2D.Double(160, 300, 100, 2));
		wall3.enablePhysics(false);
		l.addEntity(wall3);
		
		Entity wall4 = new Entity(new Rectangle2D.Double(240, 220, 100, 2));
		wall4.enablePhysics(false);
		l.addEntity(wall4);
		
		Entity wall5 = new Entity(new Rectangle2D.Double(80, 380, 2, 100));
		wall5.enablePhysics(false);
//		l.addEntity(wall5);
		
		Entity wall6 = new Entity(new Rectangle2D.Double(160, 300, 2, 100));
		wall6.enablePhysics(false);
		l.addEntity(wall6);
		
		Entity wall7 = new Entity(new Rectangle2D.Double(240, 220, 2, 100));
		wall7.enablePhysics(false);
		l.addEntity(wall7);
	

		final int[] a = { 0 };
		Timer t = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.update();
				r.repaint();
				c.update();
				wall3.jump();
				// System.out.println(entity.getLocation());
				a[0]++;
			}
		});

		// frame.setPreferredSize(new Dimension(640, 480));
		r.setPreferredSize(new Dimension(640, 480));
		frame.getContentPane().add(r);

		t.start();
		l.addEntity(entity);

		frame.pack();
		frame.setVisible(true);
	}

}
