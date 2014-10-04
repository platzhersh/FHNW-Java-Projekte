package ch.fhnw.edu.efficientalgorithms.lineintersection;

import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui.LineIntersectionGUI;

/**
 * Starts the application.
 * 
 * @author Martin Schaub
 */
public final class LineIntersectionMain {

	/**
	 * Constructor
	 */
	private LineIntersectionMain() {
		// No code, static class
	}

	/**
	 * Main method.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(final String[] args) {
		new LineIntersectionGUI();
	}
}
