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
		class Renderer extends JComponent {
			public Level level;
			
			public Renderer(Level level) {
				this.level = level;
			}
			
			public void paintComponent(Graphics g) {
				level.render(g);
			}
		}
		
		final Level l = new Level(new Rectangle2D.Double(0, 0, 600, 400));
		final Renderer r = new Renderer(l);
		
		final Entity entity = new Entity(new Rectangle2D.Double(0, 0, 20, 20));
		
		final Controller c = new Controller(entity);
		frame.addKeyListener(c);
		
		Entity wall = new Entity(new Rectangle2D.Double(200, 0, 0, 200));
//		l.addEntity(wall);
		
		final int[] a = {0};
		Timer t = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.update();
				r.repaint();
				c.update();
				if (a[0] == 30) {
					a[0] = 0;
//					entity.applyForce(Math.toRadians(-45), 2);
				}
				System.out.println(entity.getLocation());
				a[0]++;
			}
		});
		
		r.setPreferredSize(new Dimension(600, 400));
		frame.getContentPane().add(r);
		
		t.start();
		l.addEntity(entity);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
