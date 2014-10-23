package jdraw.figures;

import java.awt.Rectangle;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

public abstract class AbstractRectangularFigure extends AbstractFigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3447442418532098983L;
	
	Rectangle rectangle;
	
	@Override
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
	public void move(int dx, int dy) {
		if(dx != 0 || dy != 0) {
			rectangle.x += dx;
			rectangle.y += dy;
			propagateFigureEvent(new FigureEvent(this));
		}

	}
	
	@Override
	public Rectangle getBounds() {
		return rectangle.getBounds();
	}


	@Override
	public void addFigureListener(FigureListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeFigureListener(FigureListener listener) {
		listeners.remove(listener);
	}
	

	
	@Override
	public Figure clone() {
		return null;
	}
}
