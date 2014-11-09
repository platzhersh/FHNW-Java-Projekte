package jdraw.figures.handles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;


import jdraw.figures.AbstractFigure;
import jdraw.figures.handles.states.AbstractHandleState;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

public class Handle implements FigureHandle {
	
	// XXX die beiden folgenden Variablen sollten privat deklariert werden, und owner zusätzlich final, denn der Owner ändert sich ja nicht
	AbstractHandleState state;
	AbstractFigure owner;
	
	// XXX die Variable handler wrid nie verwendet (auch nicht initialisiert). Eclipse reklamiert da nicht da es nicht privat deklariert ist.
	HandleHandler handler;
	
	public Handle(AbstractHandleState hs, AbstractFigure o) {
		state = hs;
		owner = o;
		state.setOwner(owner);
	}
	
	// XXX Variante wäre, diese Methode auch an den State zu delegieren.
	@Override
	public Figure getOwner() {
		return owner;
	}

	@Override
	public Point getLocation() {
		return state.getLocation();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(getLocation().x-3, getLocation().y-3, 6, 6);
		g.setColor(Color.BLACK);
		g.drawRect(getLocation().x-3, getLocation().y-3, 6, 6);
	}

	@Override
	public Cursor getCursor() {
		return state.getCursor();
	}

	@Override
	public boolean contains(int x, int y) {
		return (x < getLocation().x + 3 && x > getLocation().x - 3 &&
				y < getLocation().y + 3 && y > getLocation().y - 3);

	}

	@Override
	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		state.startInteraction();
	}

	@Override
	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		state.dragInteraction(x, y);
	}

	@Override
	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		state.stopInteraction(x, y);
	}
	
	public void flipHorizontal() {
		state = state.getHorizontalFlipState();
		state.setOwner(owner);
		state.startInteraction();
	}

	public void flipVertical() {
		state = state.getVerticalFlipState();
		state.setOwner(owner);
		state.startInteraction();
	}
	
	public void setState(AbstractHandleState s) {
		state = s;
	}

}
