package ch.fhnw.edu.efficientalgorithms.impl.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;

/**
 * Saves the current state to a file. The save mechanism is tightly coupled with the load mechanism. Hence if changing
 * one the other needs change too!
 * 
 * @author Martin Schaub
 */
public final class SaveAction extends AbstractAction {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -4423072362673214090L;

	/**
	 * Model that will be saved.
	 */
	private final TSPModel modelToSave;
	/**
	 * Tunables which will be saved.
	 */
	private final List<TSPAlgorithmTunable> tunables;

	/**
	 * Constructor
	 * 
	 * @param modelToSave model to save
	 * @param tunables tunables to save
	 */
	public SaveAction(final TSPModel modelToSave, final List<TSPAlgorithmTunable> tunables) {
		if (modelToSave == null || tunables == null) {
			throw new NullPointerException();
		}
		this.modelToSave = modelToSave;
		this.tunables = tunables;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		chooser.setFileFilter(new FileNameExtensionFilter("TSP files", "tsp"));

		boolean saved = false;
		while (!saved && chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

			// Add the extension, if there is no one.
			File toWrite = chooser.getSelectedFile();
			if (!toWrite.getName().endsWith(".tsp") && !toWrite.getName().matches(".+\\.[^.]+")) {
				toWrite = new File(toWrite.getAbsoluteFile() + ".tsp");
			}

			// Overwrite the file?
			boolean write = false;
			if (toWrite.exists()) {
				int ans = JOptionPane.showConfirmDialog(null, "File already exists. Do you want to overwrite it?",
						"File already exists", JOptionPane.YES_NO_OPTION);
				if (ans == JOptionPane.YES_OPTION) {
					write = true;
				}
			}
			else {
				// If the files does not exist, write it!
				write = true;
			}

			if (write) {
				try {

					PrintWriter out = new PrintWriter(new FileOutputStream(toWrite));
					synchronized (modelToSave) {
						for (Location loc : modelToSave.getLocations()) {
							out.println(loc.getCoordinate().getX() + ":" + loc.getCoordinate().getY() + ":" + loc.getLabel());
						}
						for (TSPAlgorithmTunable tunable : tunables) {
							out.println("");
							out.println(tunable.getClass().getName());
							out.println(tunable.getAlgorithm().getClass().getName());
							for (String attribute : tunable.getAttributes()) {
								out.println(attribute + ":" + tunable.getValue(attribute));
							}

						}
					}
					out.close();

					saved = true;
				}
				catch (final IOException ex) {
					JOptionPane.showMessageDialog(null, "Error saving file! " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}
}