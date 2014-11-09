package patterns.clone.immutable;

import java.awt.Rectangle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RectangleTest {
	public static final int X = 1;
	public static final int Y = 2;
	public static final int W = 3;
	public static final int H = 4;
	
	
	private Rectangle r;
	private RectangleFigure f;
	
	@Before
	public void setUp() {
		r = new Rectangle(X, Y, W, H);
		f = new RectangleFigure(r);
	}
	
	@Test
	public void test1() {
		checkImmutability(f);
	}
	
	@Test
	public void test2() {
		f.getBounds().width = 100;
		checkImmutability(f);
	}
	
	@Test
	public void test3() {
		r.height = r.width;
		checkImmutability(f);
	}
	
	@Test
	public void test4() {
		f.move(2, 2);
		checkImmutability(f);
	}
	
	public void checkImmutability(RectangleFigure f) {
		Rectangle bounds = f.getBounds();
		Assert.assertEquals(X, bounds.x);
		Assert.assertEquals(Y, bounds.y);
		Assert.assertEquals(W, bounds.width);
		Assert.assertEquals(H, bounds.height);
	}

}
