package jdraw.figures;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
public class EllipseTool extends AbstractDrawTool {

	/**
	 * Temporary variable. During rectangle creation (during a
	 * mouse down - mouse drag - mouse up cycle) this variable refers
	 * to the new rectangle that is inserted.
	 */
	private Ellipse newElli = null;
	
	/**
	 * Create a new circle tool for the given context.
	 * @param context a context to use this tool in.
	 */
	public EllipseTool(DrawContext context) {
		this.context = context;
		this.view = context.getView();
	}

	/**
	 * Temporary variable.
	 * During rectangle creation this variable refers to the point the
	 * mouse was first pressed.
	 */
	private Point anchor = null;
	
	@Override
	public void activate() {
		this.context.showStatusText("Circle Mode");
		
	}

	@Override
	public void deactivate() {
		this.context.showStatusText("");
		
	}

	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newElli != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		newElli = new Ellipse(x, y, 0, 0);
		view.getModel().addFigure(newElli);
		
	}

	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		newElli.setBounds(anchor, new Point(x, y));
		java.awt.Rectangle r = newElli.getBounds();
		this.context.showStatusText("w: " + r.width + ", h: " + r.height);
	}

	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		newElli = null;
		anchor = null;
		this.context.showStatusText("Circle Mode");
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + "oval.png"));
	}

	@Override
	public String getName() {
		return "Circle";
	}

}
