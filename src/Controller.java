import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Controller implements KeyListener {
	private Entity unit;
	private boolean[] keysDown;
	private boolean jumped;
	
	private static final int
		LEFT = 0,
		RIGHT = 1,
		UP = 2;
	
	public Controller(Entity unit) {
		this.unit = unit;
		keysDown = new boolean[3];
	}
	
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			keysDown[LEFT] = true;
			System.out.println("left press");
			break;
		case KeyEvent.VK_D:
			keysDown[RIGHT] = true;
			System.out.println("right press");
			break;
		case KeyEvent.VK_W:
			keysDown[UP] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			keysDown[LEFT] = false;
			break;
		case KeyEvent.VK_D:
			keysDown[RIGHT] = false;
			break;
		case KeyEvent.VK_W:
			keysDown[UP] = false;
			jumped = false;
		}
	}

	public void keyTyped(KeyEvent e) {}
	
	public void update() {
		if (keysDown[UP] && !jumped) {
			unit.pushUp();
			jumped = true;
		}
		if (keysDown[LEFT]) {
			unit.applyForce(Math.PI, 1);
		}
		if (keysDown[RIGHT]) {
			unit.applyForce(0, 1);
		}
	}
}
