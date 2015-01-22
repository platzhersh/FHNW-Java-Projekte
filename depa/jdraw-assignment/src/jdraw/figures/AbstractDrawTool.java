package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

public abstract class AbstractDrawTool implements DrawTool {
	
	
	/**
	 * Temporary variable. During rectangle creation (during a
	 * mouse down - mouse drag - mouse up cycle) this variable refers
	 * to the new rectangle that is inserted.
	 */
	protected AbstractFigure fig;
	
	/** 
	 * the image resource path. 
	 */
	protected static final String IMAGES = "/images/";
	
	/**
	 * The context we use for drawing.
	 */
	protected DrawContext context;
	
	/**
	 * The context's view. This variable can be used as a shortcut, i.e.
	 * instead of calling context.getView().
	 */
	protected DrawView view;
	
	/**
	 * Temporary variable.
	 * During rectangle creation this variable refers to the point the
	 * mouse was first pressed.
	 */
	protected Point anchor = null;
	
	
	@Override
	public void deactivate() {
		this.context.showStatusText("");
		
	}
	
	@Override
	public void activate() {
		this.context.showStatusText(this.getName() + " Mode");
	}
	
	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		createHandles();
		fig = null;
		anchor = null;
		this.context.showStatusText(getName() + " Mode");
	}
	
	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		fig.setBounds(anchor, new Point(x, y));
		java.awt.Rectangle r = fig.getBounds();
		this.context.showStatusText("w: " + r.width + ", h: " + r.height);
	}
	
	abstract public void createHandles();
}
