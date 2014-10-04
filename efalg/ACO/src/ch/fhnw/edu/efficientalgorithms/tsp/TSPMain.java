package ch.fhnw.edu.efficientalgorithms.tsp;

import ch.fhnw.edu.efficientalgorithms.impl.TSPModelImpl;
import ch.fhnw.edu.efficientalgorithms.impl.gui.TSPGUI;

/**
 * Main class to start the application.
 * 
 * @author Martin Schaub
 */
public final class TSPMain {

	/**
	 * Application entry point
	 * 
	 * @param args command line arguments
	 */
	public static void main(final String[] args) {
		TSPModel model = new TSPModelImpl();
		new TSPGUI(model);
	}
}
