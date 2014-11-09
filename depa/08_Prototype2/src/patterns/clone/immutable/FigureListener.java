/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package patterns.clone.immutable;

import java.util.EventListener;

/**
 * Listener interested in figure changes.
 */
public interface FigureListener extends EventListener {

	/**
	 * Sent when a figure has changed.
	 * 
	 * @param e figure event
	 */
	void figureChanged(FigureEvent e);
}
