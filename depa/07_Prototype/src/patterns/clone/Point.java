package patterns.clone;

import java.awt.Color;

public class Point implements Cloneable {
	private double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	public String toString() {
		return String.format("Point(%f, %f)", x, y);
	}
}

class ColoredPoint extends Point {
	private Color color;

	public ColoredPoint(double x, double y, Color c) {
		super(x, y);
		this.color = c;
	}

	public String toString() {
		return String.format("ColorPoint(%s, %s)", super.toString(), color);
	}

}
