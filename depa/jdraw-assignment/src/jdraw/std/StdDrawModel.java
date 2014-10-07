/*
 * Copyright (c) 2000-2013 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 * @author TODO add your name here
 *
 */
public class StdDrawModel implements DrawModel, FigureListener {

	/** List of figures contained in the drawing. */
	private final List<Figure> figures = new LinkedList<Figure>();
	
	/** List of listeners interested in changes of any figure. */
	private final List<DrawModelListener> listeners = new LinkedList<DrawModelListener>();

	
	@Override
	public void addFigure(Figure f) {
		if (f != null && !figures.contains(f)) {
			figures.add(f);
			f.addFigureListener(this);
			notifyListeners(f, DrawModelEvent.Type.FIGURE_ADDED);
		}
	}

	@Override
	public Iterable<Figure> getFigures() {
		return Collections.unmodifiableList(figures);
	}

	@Override
	public void removeFigure(Figure f) {
		if (figures.remove(f)) {
			f.removeFigureListener(this);
			notifyListeners(f, DrawModelEvent.Type.FIGURE_REMOVED);
		}
	}
	
	protected void notifyListeners(Figure f, DrawModelEvent.Type type) {
			DrawModelEvent dme = new DrawModelEvent(this, f, type);
			DrawModelListener[] copy =
			listeners.toArray(new DrawModelListener[]{});
			
			for (DrawModelListener l : copy) { 
				l.modelChanged(dme); 
			}
	}
	
	@Override
	public void figureChanged(FigureEvent e) {
		notifyListeners(e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED);
	}
	
	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		if (listener != null && !listeners.contains(listeners)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		listeners.remove(listener);
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * @return the draw command handler.
	 */
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		int pos = figures.indexOf(f);
		if (pos < 0) {
			throw new IllegalArgumentException(
			"Figure f not contained in model");
		}
		if (pos != index) {
			figures.remove(f);
			figures.add(index, f);
			notifyListeners(f, DrawModelEvent.Type.DRAWING_CHANGED);
		}
	}

	@Override
	public void removeAllFigures() {
		// TODO to be implemented  
		System.out.println("StdDrawModel.removeAllFigures has to be implemented");
	}

}
