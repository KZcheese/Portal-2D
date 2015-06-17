import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
	private Entity unit;
	private boolean[] keysDown;
	private boolean jumped;

	private static final int LEFT = 0, RIGHT = 1, UP = 2;

	public Controller(Entity unit) {
		this.unit = unit;
		keysDown = new boolean[3];
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			keysDown[LEFT] = true;
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			keysDown[RIGHT] = true;
		}
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP
				|| keyCode == KeyEvent.VK_SPACE) {
			keysDown[UP] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			keysDown[LEFT] = false;
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			keysDown[RIGHT] = false;
		}
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP
				|| keyCode == KeyEvent.VK_SPACE) {
			keysDown[UP] = false;
			jumped = false;
		}
		if (keyCode == KeyEvent.VK_SHIFT) {
			unit.applyForce(-Math.PI / 4, 50);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void update() {
		if (keysDown[UP]) {
			unit.jump();
		}
		if (keysDown[LEFT]) {
			unit.applyMovement(Math.PI, 2);
		}
		if (keysDown[RIGHT]) {
			unit.applyMovement(0, 2);
		}
	}
}
