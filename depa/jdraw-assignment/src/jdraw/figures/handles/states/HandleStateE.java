package jdraw.figures.handles.states;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;


public class HandleStateE extends AbstractHandleState {

	@Override
	public AbstractHandleState getHorizontalFlipState() {
		return new HandleStateW();
	}

	@Override
	public AbstractHandleState getVerticalFlipState() {
		return this;
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
	}

	@Override
	public Point getLocation() {
		Point l = owner.getBounds().getLocation();
		l.x += owner.getBounds().width;
		l.y += owner.getBounds().height / 2;
		return l;
	}

	@Override
	public Point getAnchor() {
		// anchor is HandleStateNW
		return owner.getBounds().getLocation();
	}

	@Override
	public void dragInteraction(int x, int y) {
		Rectangle r = owner.getBounds();
		owner.setBounds(anchor, new Point(x,r.y+r.height));
		
		if (x < r.x) {
			owner.flipHorizontal();
		}
	}


}
