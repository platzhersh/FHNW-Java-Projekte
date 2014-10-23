package jdraw.figures;

import java.util.LinkedList;
import java.util.List;

import jdraw.figures.handles.Handle;
import jdraw.figures.handles.HandleHandler;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractFigure implements Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1941924442198018840L;
	protected final List<FigureListener> listeners = new LinkedList<FigureListener>();
	protected final HandleHandler handles = new HandleHandler();
	
	public abstract Figure clone();
	

	
	public void addHandle(Handle h) {
		handles.addHandler(h);
	}

	@Override
	public List<FigureHandle> getHandles() {
		return handles.getHandles();
	}
	
	protected void propagateFigureEvent(FigureEvent evt){
		FigureListener[] copy = listeners.toArray(
			new FigureListener[listeners.size()]);
			for(FigureListener listener : copy) {
				listener.figureChanged(evt);
			}
	}

	public void flipHorizontal(){
		handles.flipHorizontal();
		System.out.println("FlipHorizontal");
	}
	public void flipVertical(){
		handles.flipVertical();
	}
}
