/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 * @author  Christoph Denzler
 * @version 2.1, 27.09.07
 */
public class RectTool implements DrawTool {
  
	/** 
	 * the image resource path. 
	 */
	private static final String IMAGES = "/images/";

	/**
	 * The context we use for drawing.
	 */
	private DrawContext context;

	/**
	 * The context's view. This variable can be used as a shortcut, i.e.
	 * instead of calling context.getView().
	 */
	private DrawView view;

	/**
	 * Temporary variable. During rectangle creation (during a
	 * mouse down - mouse drag - mouse up cycle) this variable refers
	 * to the new rectangle that is inserted.
	 */
	private Rect newRect = null;

	/**
	 * Temporary variable.
	 * During rectangle creation this variable refers to the point the
	 * mouse was first pressed.
	 */
	private Point anchor = null;

	/**
	 * Create a new rectangle tool for the given context.
	 * @param context a context to use this tool in.
	 */
	public RectTool(DrawContext context) {
		this.context = context;
		this.view = context.getView();
	}

	/**
	 * Deactivates the current mode by resetting the cursor
	 * and clearing the status bar.
	 * @see jdraw.framework.DrawTool#deactivate()
	 */
	public void deactivate() {
		this.context.showStatusText("");
	}

	/**
	 * Activates the Rectangle Mode. There will be a
	 * specific menu added to the menu bar that provides settings for
	 * Rectangle attributes
	 */
	public void activate() {
		this.context.showStatusText("Rectangle Mode");
	}

	/**
	 * Initializes a new Rectangle object by setting an anchor
	 * point where the mouse was pressed. A new Rectangle is then
	 * added to the model.
	 * @param x x-coordinate of mouse
	 * @param y y-coordinate of mouse
	 * @param e event containing additional information about which keys were pressed.
	 * 
	 * @see jdraw.framework.DrawTool#mouseDown(int, int, MouseEvent)
	 */
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newRect != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		newRect = new Rect(x, y, 0, 0);
		view.getModel().addFigure(newRect);
	}

	/**
	 * During a mouse drag, the Rectangle will be resized according to the mouse
	 * position. The status bar shows the current size.
	 * 
	 * @param x   x-coordinate of mouse
	 * @param y   y-coordinate of mouse
	 * @param e   event containing additional information about which keys were
	 *            pressed.
	 * 
	 * @see jdraw.framework.DrawTool#mouseDrag(int, int, MouseEvent)
	 */
	public void mouseDrag(int x, int y, MouseEvent e) {
		newRect.setBounds(anchor, new Point(x, y));
		java.awt.Rectangle r = newRect.getBounds();
		this.context.showStatusText("w: " + r.width + ", h: " + r.height);
	}

	/**
	 * When the user releases the mouse, the Rectangle object is updated
	 * according to the color and fill status settings.
	 * 
	 * @param x   x-coordinate of mouse
	 * @param y   y-coordinate of mouse
	 * @param e   event containing additional information about which keys were
	 *            pressed.
	 * 
	 * @see jdraw.framework.DrawTool#mouseUp(int, int, MouseEvent)
	 */
	public void mouseUp(int x, int y, MouseEvent e) {
		newRect = null;
		anchor = null;
		this.context.showStatusText("Rectangle Mode");
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + "rectangle.png"));
	}

	@Override
	public String getName() {
		return "Rectangle";
	}

}
