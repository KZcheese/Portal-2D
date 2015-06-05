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
		
		final Level l = new Level();
		final Renderer r = new Renderer(l);
		
		Timer t = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.update();
				r.repaint();
				System.out.println("updating");
			}
		});
		
		r.setPreferredSize(new Dimension(600, 400));
		frame.getContentPane().add(r);
		
		t.start();
		
		Entity e = new Entity(new Rectangle2D.Double(0, 0, 20, 20));
		e.pushForward();
		l.addEntity(e);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
