package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Line;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;

/**
 * Saves the current plane.
 * 
 * @author Martin Schaub
 */
public final class SaveAction extends AbstractAction {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 4201901889952305263L;

	/**
	 * Plane to save.
	 */
	private final Plane plane;

	/**
	 * Constructor
	 * 
	 * @param plane plane that will be saved.
	 */
	public SaveAction(final Plane plane) {
		if (plane == null) {
			throw new NullPointerException();
		}
		this.plane = plane;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		chooser.setFileFilter(new FileNameExtensionFilter("line intersection files", "li"));

		boolean saved = false;
		while (!saved && chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

			// Add the extension, if there is no one.
			File toWrite = chooser.getSelectedFile();
			if (!toWrite.getName().endsWith(".li") && !toWrite.getName().matches(".+\\.[^.]+")) {
				toWrite = new File(toWrite.getAbsoluteFile() + ".li");
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
					synchronized (plane) {
						for (Line line : plane.getLines()) {
							out.println(line.getStartPoint().getX() + " " + line.getStartPoint().getY() + " "
									+ line.getEndPoint().getX() + " " + line.getEndPoint().getY());
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
