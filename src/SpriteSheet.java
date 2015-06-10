import java.awt.Image;
import java.awt.image.BufferedImage;


public class SpriteSheet {
	public static class Animation {
		private int start, stop, current;
		private boolean repeat;
		
		/**
		 * Constructs an animation with a specified start and stop
		 * frame.
		 * @param start The first frame of the animation
		 * @param stop The last frame of the animation
		 */
		public Animation(int start, int stop, boolean repeat) {
			this.start = start;
			this.stop = stop;
			current = start;
		}
		
		/**
		 * Returns the current frame.
		 * @return The current frame
		 */
		public int getCurrent() {
			return current;
		}
		
		/**
		 * Returns true if the current frame is less than the end frame.
		 * @return Whether or not the animation is complete
		 */
		public boolean isDone() {
			return current < stop;
		}
		
		/**
		 * Increments the current frame if the animation is not complete.
		 */
		public void update() {
			if (!isDone()) {
				current++;
			} else if (repeat) {
				current = start;
			}
		}
	}
	
	private BufferedImage image;
	private Animation currentAnimation;
	private int width, height, frames;
	
	public SpriteSheet(BufferedImage image, int width, int height, int frames) {
		this.image = image;
		this.width = width;
		this.height = height;
		System.out.println(image);
	}
	
	public Image getCurrentFrame() {
		if (image == null) {
			return image;
		}
		int rows = image.getWidth() / width,
			cols = image.getHeight() / height,
			row = currentAnimation.getCurrent() % rows,
			col = currentAnimation.getCurrent() / cols;
		return image.getSubimage(row * width, col * height, width, height);
	}
	
	public void update() {
		currentAnimation.update();
	}
	
	public void playAnimation(Animation anim) {
		currentAnimation = anim;
	}
	
}
