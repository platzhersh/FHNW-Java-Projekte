package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public class Circle implements Figure {
	
	int x;
	int y;
	int diameter;
	Ellipse2D circle;
	
	Rectangle rectangle;
	
	private final List<FigureListener> listeners = new LinkedList<FigureListener>();
	
    /***
     * 
     * @param x the x coordinate of the upper left corner of the oval to be drawn.
     * @param y the y coordinate of the upper left corner of the oval to be drawn.
     * @param width the width of the oval to be drawn.
     * @param height the height of the oval to be drawn.
     */
	public Circle(int x, int y, int diameter) {
    	this.x = x;
    	this.y = y;
    	this.diameter = diameter;
    	rectangle = new Rectangle(x, y, diameter, diameter);
    }
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillOval(x, y, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, diameter, diameter);
	}

	@Override
	public void move(int dx, int dy) {
		if(dx != 0 || dy != 0) {
			x += dx;
			y += dy;
			propagateFigureEvent(new FigureEvent(this));
		}

	}

	@Override
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		java.awt.Rectangle original = new java.awt.Rectangle(rectangle);
		rectangle.setFrameFromDiagonal(origin, corner);

		
		int d1 = Math.abs(corner.x - origin.x);
		int d2 = Math.abs(corner.y - origin.y);
		diameter = d1 < d2 ? d1 : d2;
		
		if(!original.equals(rectangle)) {
			propagateFigureEvent(new FigureEvent(this));
		}
	}

	@Override
	public Rectangle getBounds() {
		return rectangle.getBounds();
	}

	@Override
	public List<FigureHandle> getHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFigureListener(FigureListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeFigureListener(FigureListener listener) {
		listeners.remove(listener);
	}
	
	protected void propagateFigureEvent(FigureEvent evt){
		FigureListener[] copy = listeners.toArray(
			new FigureListener[listeners.size()]);
			for(FigureListener listener : copy) {
			listener.figureChanged(evt);
		}
	}
	
	@Override
	public Figure clone() {
		return null;
	}

}
