package jdraw.figures.handles.states;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;

public class HandleStateW extends AbstractHandleState {

	@Override
	public AbstractHandleState getHorizontalFlipState() {
		return new HandleStateE();
	}

	@Override
	public AbstractHandleState getVerticalFlipState() {
		return this;
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
	}

	@Override
	public Point getLocation() {
		Point l = owner.getBounds().getLocation();
		l.y += owner.getBounds().height / 2;
		return l;
	}

	@Override
	public Point getAnchor() {
		// anchor is SE
		Point l = owner.getBounds().getLocation();
		l.x += owner.getBounds().width;
		l.y += owner.getBounds().height;
		return l;
	}

	@Override
	public void dragInteraction(int x, int y) {
		Rectangle r = owner.getBounds();
		owner.setBounds(new Point(x,r.y), anchor);
		
		if (x > r.x + r.width) {
			owner.flipHorizontal();
		}
		
	}

}
