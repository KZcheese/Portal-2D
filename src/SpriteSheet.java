import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class SpriteSheet {
	public static class Animation {
		private int start, stop, current;
		private double speed, time;
		private boolean repeat, isDone;

		/**
		 * Constructs an animation with a specified start and stop frame.
		 * 
		 * @param startThe first frame of the animation
		 * @param stop The last frame of the animation
		 */
		public Animation(int start, int stop, boolean repeat) {
			this(start, stop, repeat, 1);
		}
		
		/**
		 * Constructs an animation with the specified start frame, stop 
		 * frame, repeat mode, and speed.
		 * 
		 * @param start
		 * @param stop
		 * @param repeat
		 * @param speed
		 */
		public Animation(int start, int stop, boolean repeat, double speed) {
			this.start = start;
			this.stop = stop;
			current = start;
			this.repeat = repeat;
			this.speed = speed;
		}

		/**
		 * Returns the current frame.
		 * 
		 * @return The current frame
		 */
		public int getCurrent() {
			return current;
		}

		/**
		 * Returns true if the current frame is less than the end frame.
		 * 
		 * @return Whether or not the animation is complete
		 */
		public boolean isDone() {
			return isDone;
		}

		public void restart() {
			current = start;
			isDone  = false;
		}

		/**
		 * Increments the current frame if the animation is not complete.
		 */
		public void update() {
			time += speed;
			if (time >= 1) {
				while (time-- >= 1);
				current++;
				if (current > stop) {
					isDone = true;
					if (repeat) {
						current = start;
					} else {
						current--;
					}
				}
			}
		}
	}

	private BufferedImage image;
	private Animation currentAnimation;
	private int width, height, frames;
	private Point2D[][] hardPoints;

	/**
	 * Constructs a sprite sheet with the specified image, frame width 
	 * and height, and number of frames.
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @param frames
	 */
	public SpriteSheet(BufferedImage image, int width, int height, int frames) {
		this.image = image;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the current frame of the sprite.
	 * 
	 * @return
	 */
	public Image getCurrentFrame() {
		if (image == null) {
			return image;
		}
		int frame = currentAnimation.current;
		return image.getSubimage(getCol(frame) * width, getRow(frame) * height,
				width, height);
	}

	/**
	 * Sets the set of hardpoints.
	 * 
	 * @param hardPoints
	 */
	public void setHardPoints(Point2D[][] hardPoints) {
		this.hardPoints = hardPoints;
	}
	
	/**
	 * Returns the specified hardpoint.
	 * 
	 * @param point
	 * @return
	 */
	public Point2D getHardPoint(int point) {
		return hardPoints[currentAnimation.current][point];
	}

	/**
	 * Updates the sprite.
	 */
	public void update() {
		currentAnimation.update();
	}

	/**
	 * Plays the specified animation.
	 * @param anim
	 */
	public void playAnimation(Animation anim) {
		if (currentAnimation != anim) {
			if (currentAnimation != null) {
				currentAnimation.restart();
			}
			currentAnimation = anim;
			anim.restart();
		}
	}

	private int getCols() {
		return (image.getWidth()) / (width);
	}

	private int getRows() {
		return (image.getWidth()) / (width);
	}

	private int getCol(int frame) {
		return frame % getCols();
	}

	private int getRow(int frame) {
		return frame / getCols();
	}
	
	/**
	 * Returns whether or not the current animation has completed.
	 * 
	 * @return
	 */
	public boolean isDone() {
		return currentAnimation.isDone;
	}
}
