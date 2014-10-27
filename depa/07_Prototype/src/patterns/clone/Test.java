package patterns.clone;

import java.awt.Color;

public class Test {
	public static void main(String[] args) {
		ColoredPoint p1 = new ColoredPoint(1, 2, Color.RED);
		ColoredPoint p2 = (ColoredPoint)p1.clone();
		
		System.out.println(p1);
		System.out.println(p2);
	}
}
