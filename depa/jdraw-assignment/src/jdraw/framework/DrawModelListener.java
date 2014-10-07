/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.util.EventListener;

/**
 * Listener interested in draw model changes.
 *
 * @author  Dominik Gruntz & Christoph Denzler
 * @version 2.5
 */
public interface DrawModelListener extends EventListener {

	/**
	 * Sent when a draw model has changed.
	 * 
	 * @param e draw model event
	 */
	void modelChanged(DrawModelEvent e);
}
