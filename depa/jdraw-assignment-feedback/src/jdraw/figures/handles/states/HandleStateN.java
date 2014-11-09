package jdraw.figures.handles.states;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;

public class HandleStateN extends AbstractHandleState {

	@Override
	public AbstractHandleState getHorizontalFlipState() {
		return this;
	}

	@Override
	public AbstractHandleState getVerticalFlipState() {
		return new HandleStateS();
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
	}

	@Override
	public Point getLocation() {
		Point l = owner.getBounds().getLocation();
		l.x += owner.getBounds().width / 2;
		return l;
	}
	

	@Override
	public Point getAnchor() {
		// anchor is HandleStateSW
		Point l = owner.getBounds().getLocation();
		l.y += owner.getBounds().height;
		return l;
	}

	@Override
	public void dragInteraction(int x, int y) {
		Rectangle r = owner.getBounds();
		owner.setBounds(anchor, new Point(anchor.x+r.width,y));
		
		if (y > r.y + r.height) {
			owner.flipVertical();
		}
	}


}
