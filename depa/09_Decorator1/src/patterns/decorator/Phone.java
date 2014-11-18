package patterns.decorator;

import java.awt.Graphics;

public interface Phone {
	public void dial(String nr);
	public void draw(Graphics g);
}

class Mobile implements Phone {
	public void dial(String nr) {
		// call(nr);
	}

	public void draw(Graphics g) {
		// drawHandy();
	}

}
