import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class SolidBlock extends Block {
	public SolidBlock(int x, int y) {
		super(x, y);
	}
	
	public void render(Graphics g) {
		Point corner = getLevel().getCorner();
		int dx = (int) (getLocation().getX() - corner.x), dy = (int) (getLocation()
				.getY() - corner.y),
			w = (int) getBounds().getWidth(),
			h = (int) getBounds().getHeight();
		g.setColor(new Color(225, 225, 225));
		g.fillRect(dx, dy, w, h);
		g.setColor(new Color(235, 235, 235));
		g.drawRect(dx, dy, w, h);
	}
}
