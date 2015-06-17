import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

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
	private static final int TARGET_FRAME_DURATION = 16;
	private boolean showDebug;
	private boolean fullScreen;
	private Rectangle fullScreenBounds;
	private int state;
	private Rectangle radarMapArea;

	private boolean showUI;

	private JPanel menu;

	private double globalTimeScale = 1;

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
						stop = System.nanoTime();

						if (!map.hasAnyLivingPlayer()) {
							paused = true;
						}

						controller.update();

						Thread update = new Thread(new Runnable() {
							public void run() {
								if (map != null) {
									map.update();
								}
							}
						}), render = new Thread(new Runnable() {
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
								timeScale = (double) targetFrameRate
										* globalTimeScale / frameRate;
								map.setTimeScale(timeScale);
							}
						}
						start = System.nanoTime();
					}
				}
			});
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
		map = new Level(new Rectangle2D.Double(0, 0, 20 * Block.SIZE,
				14 * Block.SIZE));
		map.setBackground(Util.readImage("background_panel.png"));
		view = new Renderer(map);

		final Entity entity = new Player();
		entity.setLocation(192, 192);
		controller = new Controller(entity);
		addKeyListener(controller);

		int var = 0;
		
		for (int i = 0; i < 20; i++) {
			var = i % 2;
			map.addEntity(new FrameBlock(i, 13, var));
			map.addEntity(new FrameBlock(i, 0, var));
			
			if (1 < i && i < 18) {
				map.addEntity(new SolidBlock(i, 12));
				map.addEntity(new SolidBlock(i, 1));
			}
		}
		
		for (int i = 0; i < 14; i++) {
			var = i % 2;
			map.addEntity(new FrameBlock(0, i, var));
			map.addEntity(new FrameBlock(19, i, var));
			
			if (2 < i && i < 11) {
				map.addEntity(new SolidBlock(1, i));
				map.addEntity(new SolidBlock(18, i));	
			} else {
				map.addEntity(new FrameBlock(1, i));
				map.addEntity(new FrameBlock(18, i));
			}
		}

		GameTimer timer = new GameTimer();

		// frame.setPreferredSize(new Dimension(640, 480));
		view.setPreferredSize(new Dimension(20 * Block.SIZE, 14 * Block.SIZE));
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
