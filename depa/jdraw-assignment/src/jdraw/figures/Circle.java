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
    	rectangle = new Rectangle(x, y, diameter, diameter);
    }
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		java.awt.Rectangle original = new java.awt.Rectangle(rectangle);
		
		
		int d1 = corner.x - origin.x;
		int d2 = corner.y - origin.y;
		rectangle.width = Math.abs(d1) < Math.abs(d2) ? d1 : d2;
		rectangle.height = rectangle.width;
		

		corner.x = origin.x + rectangle.width;
		corner.y = origin.y + rectangle.height;
		
		rectangle.setFrameFromDiagonal(origin, corner);
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
