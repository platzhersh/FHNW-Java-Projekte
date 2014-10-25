package jdraw.std;

import java.awt.Point;

import jdraw.framework.DrawView;
import jdraw.framework.PointConstrainer;
import jdraw.std.grid.GridBehaviour;

public class StdPointConstrainer implements PointConstrainer {

	GridBehaviour strategy;
	DrawView view;
	
	
	public StdPointConstrainer(DrawView v, GridBehaviour g) {
		view = v;
		strategy = g;
	}
	
	@Override
	public Point constrainPoint(Point p) {
		return strategy.constrainPoint(p);
	}

	@Override
	public int getStepX(boolean right) {
		return strategy.getStepX(right);
	}

	@Override
	public int getStepY(boolean down) {
		return strategy.getStepY(down);
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void deactivate() {
		view.setConstrainer(null);
	}

	
	/* Maybe use this to fit a misplaced figure into the grid when selecting it */
	@Override
	public void mouseDown() {
		// TODO Auto-generated method stub
		System.out.println("Mouse down");

	}

	@Override
	public void mouseUp() {
		// TODO Auto-generated method stub

	}

}
