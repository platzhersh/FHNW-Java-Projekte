package jdraw.figures.handles;

import java.util.LinkedList;
import java.util.List;


import jdraw.framework.FigureHandle;

public class HandleHandler {
	
	// XXX diese Liste sollte auch final und privat deklariert werden.
	// XXX anstelle von FigureHandle könnte man den Typparameter und die Argumente der Methode addHandler
	//     auch gerade durch Handle ersetzen, denn in der Methode flipHorizontal/Vertical wird ja dann
	//     sowieso ein Cast auf Handle gemacht.
	LinkedList<FigureHandle> handles = new LinkedList<FigureHandle>();
	
	public void addHandler(FigureHandle h) {
		handles.add(h);
	}
	
	public List<FigureHandle> getHandles() {
		return handles;
	}
	
	public void flipHorizontal() {
		for (FigureHandle h : handles) {
			((Handle) h).flipHorizontal();
		}
	}
	
	public void flipVertical() {
		for (FigureHandle h : handles) {
			((Handle) h).flipVertical();
		}
	}
}
