package jdraw.figures.handles.states;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;


public class HandleStateSW extends AbstractHandleState {

	@Override
	public AbstractHandleState getHorizontalFlipState() {
		return new HandleStateSE();
	}

	@Override
	public AbstractHandleState getVerticalFlipState() {
		return new HandleStateNW();
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
	}

	@Override
	public Point getLocation() {
		Point l = owner.getBounds().getLocation();
		l.y += owner.getBounds().height;
		return l;
	}

	@Override
	public Point getAnchor() {
		// anchor is NE
		Point l = owner.getBounds().getLocation();
		l.x += owner.getBounds().width;
		return l;
	}

	@Override
	public void dragInteraction(int x, int y) {
		Rectangle r = owner.getBounds();
		owner.setBounds(anchor, new Point(x,y));
		
		if (x > r.x + r.width) {
			owner.flipHorizontal();
		}
		if (y < r.y) {
			owner.flipVertical();
		}
	}

}
