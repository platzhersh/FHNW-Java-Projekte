package ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;
import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.Position;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.SudokuBoardImpl;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.Value;

/**
 * Loads a new board from a file.
 *
 * @author Martin Schaub
 */
public final class LoadAction extends AbstractSudokuGUIAction {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -2103858342734772815L;

	/**
	 * To protect it from other GUI actions
	 */
	private final Object lock;

	/**
	 *
	 * Constructor
	 *
	 * @param lock lock to prevent other actions to influence this one.
	 */
	public LoadAction(final Object lock) {
		this.lock = lock;
	}

	/**
	 * Prompts for a dialog box and the open the selected file.
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		synchronized (lock) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			chooser.setFileFilter(new FileNameExtensionFilter("sudoku files", "sudoku"));

			boolean loaded = false;
			while (!loaded && chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				if (!chooser.getSelectedFile().exists()) {
					JOptionPane.showMessageDialog(null, "Selected file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						SudokuBoard board = new SudokuBoardImpl();
						BufferedReader in = new BufferedReader(new FileReader(chooser.getSelectedFile()));
						String str = null;
						int i = 0;
						while ((str = in.readLine()) != null) {
							String[] line = str.split("\\s+");
							for (int j = 0; j < line.length; ++j) {
								if (line[j].length() != 1) {
									throw new IOException("Error on line " + i);
								}

								if (Character.isDigit(line[j].charAt(0))) {
									board.setValue(new Position(j / 3 + (i / 3) * 3, j % 3 + (i % 3) * 3), new Value(Integer
											.parseInt(line[j])));
								}
							}
							i++;
						}
						in.close();

						// Inform the listeners
						for (final SudokuGUIListener listner : listeners) {
							listner.boardLoaded(board);
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
