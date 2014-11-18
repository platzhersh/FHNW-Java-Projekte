package patterns.decorator;

import java.awt.Graphics;

public class RedDecorator implements Phone {
	private final Phone inner;

	public RedDecorator(Phone inner) {
		this.inner = inner;
	}

	public void dial(String nr) {
		inner.dial(nr);
	}

	public void draw(Graphics g) {
		inner.draw(g);
	}

}
