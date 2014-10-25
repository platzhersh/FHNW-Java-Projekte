package jdraw.std.grid;

import java.awt.Point;

import jdraw.framework.DrawView;

public abstract class GridBehaviour {

	DrawView view;
	
	abstract public Point constrainPoint(Point p);
	abstract public int getStepX(boolean right);
	abstract public int getStepY(boolean down);
	
	
}
