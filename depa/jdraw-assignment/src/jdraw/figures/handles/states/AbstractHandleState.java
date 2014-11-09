package jdraw.figures.handles.states;

import java.awt.Cursor;
import java.awt.Point;

import jdraw.figures.AbstractFigure;


public abstract class AbstractHandleState {
	
	protected AbstractFigure owner;
	protected Point anchor;
	
	public abstract AbstractHandleState getHorizontalFlipState();
	public abstract AbstractHandleState getVerticalFlipState();
	public abstract Cursor getCursor();
	protected abstract Point getAnchor();
	public abstract Point getLocation();
	
	public void setOwner(AbstractFigure fig) {
		owner = fig;
	}
	public abstract void dragInteraction(int x, int y);
	public void startInteraction() {
		anchor = getAnchor();
	}
	public void stopInteraction(int x, int y) {
		anchor = null;
	}
	
	// public abstract Point getFixedCorner();	
	// public abstract Point getVariableCorner(int x, int y);
	
}
