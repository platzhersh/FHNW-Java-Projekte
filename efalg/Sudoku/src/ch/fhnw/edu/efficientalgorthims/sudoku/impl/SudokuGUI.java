package ch.fhnw.edu.efficientalgorthims.sudoku.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.AbstractSudokuGUIAction;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.BoardListModel;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.ExitAction;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.LabelUpdater;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.LoadAction;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.SolveAction;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.StatusUpdater;

/**
 * Displays a window with the sudoku game inside.
 * 
 * @author Martin Schaub
 */
public final class SudokuGUI {

	/**
	 * 
	 * Constructor
	 * 
	 * Builds up a sudoku game GUI
	 * 
	 */
	public SudokuGUI() {

		// Actions for listener registration
		Object lock = new Object();
		AbstractSudokuGUIAction load = new LoadAction(lock);
		AbstractSudokuGUIAction solve = new SolveAction(lock);
		load.addSudokuGUIListener(solve);

		BoardListModel listModel = new BoardListModel();
		solve.addSudokuGUIListener(listModel);
		load.addSudokuGUIListener(listModel);

		// Merge it together in a window
		JFrame frame = new JFrame("Sudoku");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.add(createRightSide(solve, load, listModel), BorderLayout.EAST);
		frame.add(createSudokuBoard(listModel, load), BorderLayout.CENTER);
		frame.setJMenuBar(createMenuBar(solve, load));
		frame.setVisible(true);

	}

	/**
	 * Creates the right side
	 * 
	 * @param solveA solve action for registering listeners
	 * @param loadA load action for registering listeners
	 * @param listModel list model
	 * @return right side components on a panel
	 */
	private JPanel createRightSide(final AbstractSudokuGUIAction solveA, final AbstractSudokuGUIAction loadA,
			final BoardListModel listModel) {
		// Selectionlist

		JList selection = new JList(listModel);
		selection.addListSelectionListener(listModel);
		selection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selection.setSelectedIndex(0);

		// Status field
		JLabel status = new JLabel("status: new");
		StatusUpdater statusUpdater = new StatusUpdater(status);
		solveA.addSudokuGUIListener(statusUpdater);
		loadA.addSudokuGUIListener(statusUpdater);

		// Merged together in a panel
		JPanel rightside = new JPanel();
		rightside.setLayout(new BorderLayout());
		rightside.add(new JScrollPane(selection), BorderLayout.NORTH);
		rightside.add(status, BorderLayout.SOUTH);

		return rightside;
	}

	/**
	 * Creates the menubar.
	 * 
	 * @param solveA solve action for registering listeners
	 * @param loadA load action for registering listeners
	 * @return jmenubar for the application
	 */
	private JMenuBar createMenuBar(final AbstractSudokuGUIAction solveA, final AbstractSudokuGUIAction loadA) {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem load = new JMenuItem(loadA);
		load.setText("Open");
		load.setMnemonic(KeyEvent.VK_O);
		file.add(load);

		file.addSeparator();

		JMenuItem exit = new JMenuItem(new ExitAction());
		exit.setText("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		file.add(exit);

		menuBar.add(file);

		JMenuItem solve = new JMenuItem(solveA);
		solve.setText("Solve");
		solve.setMnemonic(KeyEvent.VK_S);
		menuBar.add(solve);

		// otherwise the button will be until the right end..
		menuBar.add(new JMenuItem());

		return menuBar;
	}

	/**
	 * Private helper method to create a graphical sudoku board.
	 * 
	 * @param loadA load action for registering listeners
	 * @param listModel selection notifier for registering listeners
	 * @return sudoku board
	 */
	private JPanel createSudokuBoard(final BoardListModel listModel, final AbstractSudokuGUIAction loadA) {
		// Panel that stores the sudoku board
		JPanel leftside = new JPanel();
		leftside.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		leftside.setLayout(new GridLayout(3, 3));

		// Add all pieces of the board
		for (int i = 0; i < 9; ++i) {
			JPanel pan = new JPanel();
			pan.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			pan.setLayout(new GridLayout(3, 3));
			for (int j = 0; j < 9; ++j) {
				JLabel label = new JLabel(" ");
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				pan.add(label);

				LabelUpdater updater = new LabelUpdater(label, new Position(i, j));
				listModel.addSudokuGUIListener(updater);
				loadA.addSudokuGUIListener(updater);
			}
			leftside.add(pan);
		}

		return leftside;
	}
}
