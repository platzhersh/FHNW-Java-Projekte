package jdraw.figures;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
public class EllipseTool extends AbstractRectangularDrawTool {

	/**
	 * Create a new circle tool for the given context.
	 * @param context a context to use this tool in.
	 */
	public EllipseTool(DrawContext context) {
		this.context = context;
		this.view = context.getView();
	}

	
	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (fig != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		fig = new Ellipse(x, y, 0, 0);
		view.getModel().addFigure(fig);
		
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
		return "Ellipse";
	}

}
