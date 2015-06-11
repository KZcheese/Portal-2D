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
			this.repeat = repeat;
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
			if (true) {
				current++;
				if (current > stop) {
					System.out.println(repeat);
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
	
	public SpriteSheet(BufferedImage image, int width, int height, int frames) {
		this.image = image;
		this.width = width;
		this.height = height;
	}
	
	public Image getCurrentFrame() {
		if (image == null) {
			return image;
		}
		int frame = currentAnimation.current;
		return image.getSubimage(getCol(frame) * width, getRow(frame) * height, width, height);
	}
	
	public void update() {
		currentAnimation.update();
	}
	
	public void playAnimation(Animation anim) {
		currentAnimation = anim;
	}
	
	private int getCols() {
		return image.getWidth() / width;
	}
	
	private int getRows() {
		return image.getHeight() / height;
	}
	
	private int getCol(int frame) {
		return frame % getCols();
	}
	
	private int getRow(int frame) {
		return frame / getCols();
	}
	
	public static void main(String[] args) {
		SpriteSheet s = new SpriteSheet(Util.readImage("sprite_test.png"), 48, 64, 8);
		int f = 2;
		System.out.println(s.getRow(f) * s.width + " " + s.getCol(f) * s.height + " " + s.width + " " + s.height);
		
	}
	
	public void cueAnimation(Animation anim) {
		
	}
	
}
