import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;


public class Level {
	private List<Entity> entities;
	private Bounds bounds;
	private Point corner;
	
	public Level() {
		this(new Rectangle());
	}
	
	public Level(Rectangle bounds) {
		entities = new LinkedList<>();
		
		Area a = new Area(new Rectangle(bounds.x - 10, bounds.y - 10, bounds.width + 20, bounds.height + 20));
		a.subtract(new Area(bounds));
		
		this.bounds = new Bounds(a);
		
		corner = new Point();
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
		e.setLevel(this);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
		e.setLevel(null);
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public Point getCorner() {
		return corner;
	}
	
	public Bounds getBounds() {
		return bounds;
	}
	
	public void render(Graphics g) {
		for (Entity e : entities) {
			e.render(g);
		}
		((Graphics2D) g).draw(bounds.getArea());
	}
	
	public void update() {
		for (Entity e : entities) {
			e.update();
		}
	}
	
	public static void main(String[] args) {
		Level l = new Level(new Rectangle(600, 400));
		class Renderer extends JComponent {
			
			public final Level level;
			
			public Renderer(Level level) {
				this.level = level;
				
				Timer timer = new Timer(20, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Renderer.this.level.update();
					}
				});
				timer.start();
				Renderer.this.repaint();
			}
			
			public void paintComponent(Graphics g) {
				level.render(g);
			}
			
		}
		Renderer r = new Renderer(l);
		r.setPreferredSize(new Dimension(600, 400));
		Entity e = new Entity(new Rectangle2D.Double(0, 0, 20, 30));
		l.addEntity(e);
		e.setLocation(new Point2D.Double(100, 50));
		e.pushForward();
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(r);
		frame.pack();
		frame.setVisible(true);
	}
}
