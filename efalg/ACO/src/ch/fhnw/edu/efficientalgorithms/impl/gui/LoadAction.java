package ch.fhnw.edu.efficientalgorithms.impl.gui;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.fhnw.edu.efficientalgorithms.impl.ImmutableLocation;
import ch.fhnw.edu.efficientalgorithms.impl.ImmutablePoint;
import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus.Status;

/**
 * Loads a previously saved file. The load mechanism is tightly coupled with the save mechanism. Hence if changing one
 * the other needs change too!
 * 
 * @author Martin Schaub
 */
public final class LoadAction extends AbstractAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 7550600680753228776L;

	/**
	 * To which model the data is loaded.
	 */
	private final TSPModel model;
	/**
	 * Tunables which will be loaded.
	 */
	private final List<TSPAlgorithmTunable> tunables;
	/**
	 * Algorithm status to determine if the loading operation is possible.
	 */
	private final TSPAlgorithmStatus status;

	/**
	 * Constructor
	 * 
	 * @param model model to load
	 * @param tunables tunables to load
	 * @param status status to determine if file loading is possible
	 */
	public LoadAction(final TSPModel model, final List<TSPAlgorithmTunable> tunables, final TSPAlgorithmStatus status) {
		if (model == null || tunables == null || status == null) {
			throw new NullPointerException();
		}
		this.model = model;
		this.tunables = tunables;
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {

		boolean entered = false;
		synchronized (status) {
			if (status.getStatus().equals(Status.NO_ALGORITHM_RUNNING)) {
				entered = true;

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				chooser.setFileFilter(new FileNameExtensionFilter("TSP files", "tsp"));

				boolean loaded = false;
				while (!loaded && chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					if (!chooser.getSelectedFile().exists()) {
						JOptionPane.showMessageDialog(null, "Selected file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						try {
							BufferedReader in = new BufferedReader(new FileReader(chooser.getSelectedFile()));

							String str;
							int lineCounter = 0;

							// TODO synchronize application with algorithm status

							// Remove all locations
							synchronized (model) {
								for (Location loc : new ArrayList<Location>(model.getLocations())) {
									model.removeLocation(loc);
								}
							}

							// Reads the locations
							while ((str = in.readLine()) != null && !str.equals("")) {
								lineCounter++;

								String[] line = str.split(":");
								if (line.length != 3) {
									throw new IOException("Error reading line " + lineCounter);
								}

								int x = Integer.parseInt(line[0]);
								int y = Integer.parseInt(line[1]);
								String label = line[2];

								model.addLocation(new ImmutableLocation(label, new ImmutablePoint(x, y)));
							}

							// Read tunables
							while (str != null) {
								String tunableClass = in.readLine();
								lineCounter++;
								String algorithmClass = in.readLine();
								lineCounter++;

								// Find a matching tunable
								TSPAlgorithmTunable tunable = null;
								for (TSPAlgorithmTunable tun : tunables) {
									if (tun.getClass().getName().equals(tunableClass)
											&& tun.getAlgorithm().getClass().getName().equals(algorithmClass)) {
										tunable = tun;
									}
								}

								// Read the settings
								while ((str = in.readLine()) != null && !str.equals("")) {
									lineCounter++;
									if (tunable != null) {
										String[] line = str.split(":");
										if (line.length != 2) {
											throw new IOException("Error reading line " + lineCounter);
										}

										String attribute = line[0];
										String value = line[1];
										tunable.valueChanged(attribute, value);
									}
								}
							}

							in.close();
							loaded = true;
						}
						catch (final IOException ex) {
							JOptionPane.showMessageDialog(null, "Error reading file! " + ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}

		if (!entered) {
			JOptionPane.showMessageDialog(null, "Cannot load a file while an algorithm is running", "Error Loading File",
					JOptionPane.WARNING_MESSAGE);
		}

	}

}
