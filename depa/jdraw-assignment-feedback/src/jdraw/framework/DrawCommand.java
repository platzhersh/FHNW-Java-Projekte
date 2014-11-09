/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

/**
 * The interface DrawCommand defines the functionality provided by
 * commands which can be stored in the command history.
 *
 * @see DrawCommandHandler
 *
 * @author  Dominik Gruntz & Christoph Denzler
 * @version 2.5
 */

public interface DrawCommand {
	
	/**
	 * Executes a command.
	 */
	void redo();

	/**
	 * Undoes the action performed by execute. 
	 */
	void undo();
}

