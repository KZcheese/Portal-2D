import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Game extends JFrame {
	private static final long serialVersionUID = 8042357299242990677L;
	private Level map;
	private GameTimer gameTimer;
	private JComponent glassPane;
	private boolean paused;
	private Controller controller;
	private Renderer view;
	public int targetFrameRate = 60;
	private static final int TARGET_FRAME_DURATION = 0;
	private boolean showDebug;
	private boolean fullScreen;
	private Rectangle fullScreenBounds;
	private int state;
	private Rectangle radarMapArea;
	
	private boolean showUI;
	
	private JPanel menu;
	
	private double globalTimeScale = 0.1;
	
	private Timer spawnTimer;
	
	private class GameTimer {
		private Timer timer;
		private long start, stop;
		private long frame, frameTime;
		private int frameRate;
		private double timeScale;
		
		public GameTimer() {
			timer = new Timer(TARGET_FRAME_DURATION, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				if (true) {
					System.out.println("Map updating");
					stop = System.nanoTime();
					
					if (false) {
						paused = true;
					}
					
					controller.update();
				
					Thread 
						update = new Thread(new Runnable() {
							public void run() {
								if (map != null) {
									map.update();
								}
							}
						}),
						render = new Thread(new Runnable() {
							public void run() {
								view.repaint();
							}
						});
					
					update.start();
					render.start();
					
					try {
						update.join();
						render.join();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					frameTime += (stop - start);
					frame++;
					
					if (frame % 1 == 0) {
						frameRate = (int) (frame / (frameTime / 1000000000.0));
						frameTime = 0;
						frame = 0;
						if (frameRate != 0) {
							timeScale = (double) targetFrameRate * globalTimeScale / frameRate;
							map.setTimeScale(timeScale);
						}
					}
					start = System.nanoTime();
				}
				}});
		}
		
		public void start() {
			System.out.println("Started");
			timer.start();
			start = System.nanoTime();
		}
		
		public void restart() {
			timer.restart();
			start = System.nanoTime();
		}
		
		public void stop() {
			timer.stop();
			stop = System.nanoTime();
		}
		
		public int getFrameRate() {
			return frameRate;
		}
		
		public double getTimeScale() {
			return timeScale;
		}
	}
	
	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("Hi");
		map = new Level(new Rectangle2D.Double(0, 0, 16 * Block.SIZE,
				12 * Block.SIZE));
		view = new Renderer(map);

		final Entity entity = new Player();
		controller = new Controller(entity);
		addKeyListener(controller);

		for (int i = 0; i < 16; i++) {
			if (i == 8) {
				map.addEntity(new Goal(i, 11));
			} else if (i == 0) {
				map.addEntity(new CheckPoint(i, 11));
			} else {
				map.addEntity(new SolidBlock(i, 11));
			}
		}

		map.addEntity(new SolidBlock(5, 9));
		map.addEntity(new SolidBlock(4, 9));
		
		for (int i = 7; i < 14; i++) {
			map.addEntity(new SolidBlock(i, 8));
		}
		
		map.addEntity(new Spike(3, 10));
		map.addEntity(new Spike(6, 10));
		map.addEntity(new Spike(8, 7));
		map.addEntity(new Spike(9, 7));
		map.addEntity(new Spike(13, 7));
		
		map.addEntity(new SolidBlock(11, 7));
		map.addEntity(new SolidBlock(11, 6));
		map.addEntity(new CheckPoint(11, 5));

		GameTimer timer = new GameTimer();

		// frame.setPreferredSize(new Dimension(640, 480));
		view.setPreferredSize(new Dimension(16 * Block.SIZE, 12 * Block.SIZE));
		getContentPane().add(view);
		map.addEntity(entity);
		
		timer.start();

		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
}
