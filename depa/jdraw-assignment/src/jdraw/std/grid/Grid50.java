package jdraw.std.grid;

import java.awt.Point;

import jdraw.framework.DrawView;

public class Grid50 extends GridBehaviour {
	
	public Grid50(DrawView v) {
		view = v;
	}
	
	@Override
	public Point constrainPoint(Point p) {
		return new Point(p.x/50*50, p.y/50*50);
	}

	@Override
	public int getStepX(boolean right) {
		// return right ? 50 : -50;
		return 50;
	}

	@Override
	public int getStepY(boolean down) {
		// return down ? 50 : -50;
		return 50;
	}
}
