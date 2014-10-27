package jdraw.std.grid;

import java.awt.Point;

import jdraw.framework.DrawView;

public class SnapGrid extends GridBehaviour {

	
	public SnapGrid(DrawView v) {
		view = v;
	}
	
	@Override
	public Point constrainPoint(Point p) {
		//view.getModel().getFigures()
		return p;
	}

	@Override
	public int getStepX(boolean right) {
		// return right ? 50 : -50;
		return 1;
	}

	@Override
	public int getStepY(boolean down) {
		// return down ? 50 : -50;
		return 1;
	}
}
