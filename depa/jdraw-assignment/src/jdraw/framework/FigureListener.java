/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.util.EventListener;

/**
 * Listener interested in figure changes.
 * 
 * @see FigureEvent
 *
 * @author  Dominik Gruntz & Christoph Denzler
 * @version 2.5
 */
public interface FigureListener extends EventListener {

	/**
	 * Sent when a figure has changed.
	 * 
	 * @param e figure event
	 */
	void figureChanged(FigureEvent e);
}
