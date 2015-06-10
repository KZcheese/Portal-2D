import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


public class Util {
	private static Map<String, Image> images = new HashMap<>();
	
	public static final double TWO_PI = 2 * Math.PI;
	
	/**
	 * Returns the specified angle as a value in the interval
	 * [0, 2 * pi).
	 * @param angle The angle to normalize
	 * @return The normalized angle
	 */
	public static double normalizeAngle(double angle) {
		return (angle % TWO_PI + TWO_PI) % TWO_PI;
	}
	
	/**
	 * Reads the image with the specified path within the resources
	 * folder. If an image is read at the specified path, the image will
	 * be stored in a map with its path as the key. If the map contains
	 * the key, the previously read image will be returned.
	 * @param path The path of the image
	 * @return The image at the specified path
	 */
	public Image readImage(String path) {
		Image image = null;
		path = "resources/" + path;
		if (images.containsKey(path)) {
			image = images.get(image);
		} else {
			try {
				image = ImageIO.read(new File(path));
				images.put(path, image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}
}
