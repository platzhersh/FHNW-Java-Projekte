package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;


public class Ellipse extends AbstractRectangularFigure {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3291112924237530027L;

	/***
     * 
     * @param x the x coordinate of the upper left corner of the oval to be drawn.
     * @param y the y coordinate of the upper left corner of the oval to be drawn.
     * @param width the width of the oval to be drawn.
     * @param height the height of the oval to be drawn.
     */
	public Ellipse(int x, int y, int width, int height) {
    	rectangle = new Rectangle(x, y, width, height);
    }
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		java.awt.Rectangle original = new java.awt.Rectangle(rectangle);
		rectangle.setFrameFromDiagonal(origin, corner);
		
		if(!original.equals(rectangle)) {
			propagateFigureEvent(new FigureEvent(this));
		}
	}

	
	@Override
	public Figure clone() {
		return null;
	}

}
