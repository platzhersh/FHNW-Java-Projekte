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
	// XXX mit gefällt nicht so sehr dass diese Variable protected deklariert ist (das final ist gut).
	//     Jetzt weiss man nicht so recht wann und von wem das dann verwendet wird. Besser wäre es
	//     in diesem Fall, wenn diese Klasse die Handles initialisieren würde, z.B. innerhalb der
	//     Methode getHandles solange die Handles noch  nicht initialisiert sind. Sie erzeugen sie
	//     jetzt im mouseUp über den Aufruf von createHandles, aber genausogut könnten die Handles
	//     auch im Konstruktor definiert werden. 
	protected final HandleHandler handles = new HandleHandler();
	
	// XXX die aus dem Interface geerbten Methoden müssen eigentlich nicht nochmals wiederholt werden.
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
	}
	public void flipVertical(){
		handles.flipVertical();
	}
}
