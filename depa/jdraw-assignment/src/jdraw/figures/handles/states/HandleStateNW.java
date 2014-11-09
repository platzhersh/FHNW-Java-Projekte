package jdraw.figures.handles.states;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;

public class HandleStateNW extends AbstractHandleState {

	@Override
	public AbstractHandleState getHorizontalFlipState() {
		return new HandleStateNE();
	}

	@Override
	public AbstractHandleState getVerticalFlipState() {
		return new HandleStateSW();
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
	}

	@Override
	public Point getLocation() {
		return owner.getBounds().getLocation();
	}
	
	@Override
	public Point getAnchor() {
		// anchor is HandleStateSW
		Point l = owner.getBounds().getLocation(); 
		l.x += owner.getBounds().width;
		l.y += owner.getBounds().height;
		return l;
	}

	@Override
	public void dragInteraction(int x, int y) {
		Rectangle r = owner.getBounds();
		owner.setBounds(anchor, new Point(x,y));
		
		if (x > r.x + r.width) {
			owner.flipHorizontal();
		}
		if (y > r.y + r.height) {
			owner.flipVertical();
		}
		
	}
		

}
