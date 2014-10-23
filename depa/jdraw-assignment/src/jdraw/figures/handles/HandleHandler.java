package jdraw.figures.handles;

import java.util.LinkedList;
import java.util.List;


import jdraw.framework.FigureHandle;

public class HandleHandler {
	
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
