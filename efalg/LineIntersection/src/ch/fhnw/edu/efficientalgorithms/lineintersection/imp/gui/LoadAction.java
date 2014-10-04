package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Line;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.ImmutableLine;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.ImmutablePoint;

/**
 * Loads the content of a textfile as plane.
 * 
 * @author Martin Schaub
 */
public final class LoadAction extends AbstractAction {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -8157563290989428781L;

	/**
	 * Plane to update.
	 */
	private final Plane plane;
	/**
	 * Drawer to remove all drawings after successful loading.
	 */
	private final Drawer drawer;
	/**
	 * Object to lock before the loading can start.
	 */
	private final Object lock;

	/**
	 * 
	 * Constructor
	 * 
	 * @param plane plane that will be updated with the loaded data.
	 * @param drawer drawer to remove painting after loading
	 * @param lock locks before proceeding with the action
	 */
	public LoadAction(final Plane plane, final Drawer drawer, final Object lock) {
		if (plane == null || drawer == null || lock == null) {
			throw new NullPointerException();
		}
		this.plane = plane;
		this.drawer = drawer;
		this.lock = lock;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		synchronized (lock) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			chooser.setFileFilter(new FileNameExtensionFilter("line intersection files", "li"));

			boolean loaded = false;
			while (!loaded && chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				if (!chooser.getSelectedFile().exists()) {
					JOptionPane.showMessageDialog(null, "Selected file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						BufferedReader in = new BufferedReader(new FileReader(chooser.getSelectedFile()));
						String str = null;
						int i = 0;
						Set<Line> newLines = new HashSet<Line>();

						while ((str = in.readLine()) != null) {
							String[] line = str.split("\\s+");
							if (line.length != 4) {
								throw new IOException("Error reading line " + i);
							}

							newLines.add(new ImmutableLine(new ImmutablePoint(Integer.parseInt(line[0]), Integer.parseInt(line[1])),
									new ImmutablePoint(Integer.parseInt(line[2]), Integer.parseInt(line[3]))));
							++i;
						}
						in.close();

						synchronized (plane) {
							plane.clear();
							drawer.clearAllDrawables();
							for (Line line : newLines) {
								plane.addLine(line);
							}
						}
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
}
