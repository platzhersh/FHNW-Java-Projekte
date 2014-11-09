/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package patterns.clone.immutable;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

/**
 * Base class for figure implementations. Implements listener management.
 * 
 * @author Christoph Denzler
 */
public abstract class Figure {
	private List<FigureListener> listeners = new LinkedList<FigureListener>();

	public void addFigureListener(FigureListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeFigureListener(FigureListener listener) {
		listeners.remove(listener);
	}

	protected void notifyChange(FigureEvent fe) {
		FigureListener[] list = listeners.toArray(new FigureListener[] {});
		for (FigureListener fl : list) {
			fl.figureChanged(fe);
		}
	}
	
	public abstract Rectangle getBounds();

}
