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
	private int width, height, paddingX, paddingY, frames;
	private Point2D[][] hardPoints;

	public SpriteSheet(BufferedImage image, int width, int height, int frames) {
		this(image, width, height, 0, 0, frames);
	}

	public SpriteSheet(BufferedImage image, int width, int height,
			int paddingX, int paddingY, int frames) {
		this.image = image;
		this.width = width;
		this.height = height;
		this.paddingX = paddingX;
		this.paddingY = paddingY;
	}

	public Image getCurrentFrame() {
		if (image == null) {
			return image;
		}
		int frame = currentAnimation.current;
		return image.getSubimage(getCol(frame) * width, getRow(frame) * height,
				width, height);
	}

	public void setHardPoints(Point2D[][] hardPoints) {
		this.hardPoints = hardPoints;
	}

	public Point2D getHardPoint(int point) {
		return hardPoints[currentAnimation.current][point];
	}

	public void update() {
		currentAnimation.update();
	}

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
		return (image.getWidth() + paddingX) / (width + paddingX);
	}

	private int getRows() {
		return (image.getWidth() + paddingY) / (width + paddingY);
	}

	private int getCol(int frame) {
		return frame % getCols();
	}

	private int getRow(int frame) {
		return frame / getCols();
	}
	
	public boolean isDone() {
		return currentAnimation.isDone;
	}

	public void cueAnimation(Animation anim) {

	}

}
