package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public class Circle implements Figure {
	
	int x;
	int y;
	int width;
	int height;
	
    /***
     * 
     * @param x the x coordinate of the upper left corner of the oval to be drawn.
     * @param y the y coordinate of the upper left corner of the oval to be drawn.
     * @param width the width of the oval to be drawn.
     * @param height the height of the oval to be drawn.
     */
	public Circle(int x, int y, int width, int height) {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    }
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, height);
	}

	@Override
	public void move(int dx, int dy) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FigureHandle> getHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFigureListener(FigureListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFigureListener(FigureListener listener) {
		// TODO Auto-generated method stub

	}

}
