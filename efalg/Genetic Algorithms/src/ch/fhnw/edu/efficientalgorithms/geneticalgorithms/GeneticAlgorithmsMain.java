package ch.fhnw.edu.efficientalgorithms.geneticalgorithms;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.gui.EditorGUI;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.impl.ProgramStatusImpl;

/**
 * Starts the application.
 *
 * @author Martin Schaub
 */
public final class GeneticAlgorithmsMain {

	/**
	 * Main method.
	 *
	 * @param args command line parameters
	 */
	public static void main(final String[] args) {
		ProgramStatus status = new ProgramStatusImpl();
		new EditorGUI(status);
	}
}
