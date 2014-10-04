package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder;
import ch.fhnw.edu.efficientalgorithms.chessboard.algorithm.KnightsTourBacktrackSolver;
import ch.fhnw.edu.efficientalgorithms.chessboard.algorithm.KnightsTourWarnsdorf;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.GraphicalBoardImpl;

/**
 * Creates a new GUI for a ChessBoard.
 * 
 * @author Martin Schaub
 */
public final class ChessBoardGUI {

	/**
	 * Constructor
	 * 
	 * @param holder status holder for various actions
	 */
	public ChessBoardGUI(final StatusHolder holder) {

		JFrame mainWindow = new JFrame("KnightsTour");
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setSize(1024, 768);
		mainWindow.setBackground(Color.WHITE);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setJMenuBar(createMenuBar(holder));
		mainWindow.setVisible(true);

		GraphicalBoardImpl chessPanel = new GraphicalBoardImpl(holder);
		chessPanel.setBackground(Color.WHITE);
		mainWindow.add(chessPanel, BorderLayout.CENTER);

		KnightsTourLineDrawer lineDrawer = new KnightsTourLineDrawer(chessPanel);
		holder.addStatusHolderListener(lineDrawer);

		JLabel status = new JLabel("");
		status.setOpaque(true);
		status.setBackground(Color.WHITE);
		mainWindow.add(status, BorderLayout.SOUTH);
		StatusUpdater updater = new StatusUpdater(status);
		holder.addStatusHolderListener(updater);

		ChessBoardLoader loader = new ChessBoardLoader(chessPanel);
		holder.addStatusHolderListener(loader);
	}

	/**
	 * Private helper method that creates the menu bar.
	 * 
	 * @param holder the holder, needed for some actions
	 * @return created menubar
	 */
	private JMenuBar createMenuBar(final StatusHolder holder) {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);

		JMenu newMenu = new JMenu("New");
		newMenu.setMnemonic(KeyEvent.VK_N);
		file.add(newMenu);

		for (int size : new int[] { 4, 6, 8, 10, 12, 18, 32, 64 }) {
			JMenuItem item = new JMenuItem(new NewAction(holder, size, size));
			item.setText(size + "/" + size);
			newMenu.add(item);
		}
		newMenu.addSeparator();
		int[] rowSizes = new int[] { 4, 8, 20 };
		int[] columnSizes = new int[] { 6, 10, 30 };
		for (int i = 0; i < rowSizes.length; ++i) {
			JMenuItem item = new JMenuItem(new NewAction(holder, rowSizes[i], columnSizes[i]));
			item.setText(rowSizes[i] + "/" + columnSizes[i]);
			newMenu.add(item);
		}

		file.addSeparator();

		JMenuItem exit = new JMenuItem(new ExitAction());
		exit.setText("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		file.add(exit);

		JMenu algorithms = new JMenu("Algorithms");
		algorithms.setMnemonic(KeyEvent.VK_A);
		menuBar.add(algorithms);

		StopAction stopA = new StopAction();
		holder.addStatusHolderListener(stopA);
		JMenuItem stop = new JMenuItem(stopA);
		stop.setText("Stop");
		stop.setMnemonic(KeyEvent.VK_S);
		algorithms.add(stop);
		algorithms.addSeparator();

		JMenuItem backtrack = new JMenuItem(new AlgorithmExecutionAction(holder, KnightsTourBacktrackSolver.class));
		backtrack.setText("Knight's Tour Backtracking");
		backtrack.setMnemonic(KeyEvent.VK_B);
		algorithms.add(backtrack);

		JMenuItem warnsdorf = new JMenuItem(new AlgorithmExecutionAction(holder, KnightsTourWarnsdorf.class));
		warnsdorf.setText("Knight's Tour Warnsdorf");
		warnsdorf.setMnemonic(KeyEvent.VK_W);
		algorithms.add(warnsdorf);

		return menuBar;
	}
}
