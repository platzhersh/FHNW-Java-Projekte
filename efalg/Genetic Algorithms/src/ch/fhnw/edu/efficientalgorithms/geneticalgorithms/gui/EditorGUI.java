package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithm;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.impl.GeneticProcess;

/**
 * Starts a graphical user interface.
 * 
 * @author Martin Schaub
 */
public final class EditorGUI {

	/**
	 * Constructor - Creates the graphical user interface
	 * 
	 * @param status program status.
	 */
	public EditorGUI(final ProgramStatus status) {

		JFrame mainWindow = new JFrame("Genetic Algorithms");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(1024, 768);
		mainWindow.setLayout(new BorderLayout());

		mainWindow.setVisible(true);

		InterpolationAlgorithm gaProcess = new GeneticProcess();

		PlotPanel plotPanel = new PlotPanel();
		mainWindow.add(plotPanel, BorderLayout.CENTER);
		plotPanel.setBackground(Color.WHITE);
		plotPanel.addMouseListener(new ClickRecorder(status, plotPanel));

		PlotManager plotManager = new PlotManager(plotPanel);
		status.addProgramStatusListener(plotManager);
		gaProcess.addAlgorithmListener(plotManager);

		JLabel statusLabel = new JLabel("Status:");
		mainWindow.add(statusLabel, BorderLayout.SOUTH);
		StatusUpdater statusUpdater = new StatusUpdater(statusLabel, status);
		gaProcess.addAlgorithmListener(statusUpdater);

		mainWindow.setJMenuBar(createMenuBar(status, gaProcess));
	}

	/**
	 * Creates a jmenubar for various actions.
	 * 
	 * @param status program status
	 * @param gaProcess ga process for the action
	 * @return created menubar
	 */
	private JMenuBar createMenuBar(final ProgramStatus status, final InterpolationAlgorithm gaProcess) {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);

		JMenuItem exit = new JMenuItem(new ExitAction());
		exit.setText("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		file.add(exit);

		JMenu algorithms = new JMenu("Algorithms");
		algorithms.setMnemonic(KeyEvent.VK_A);
		menuBar.add(algorithms);

		JMenuItem ga = new JMenuItem(new ExecuteAlgorithmAction(gaProcess, status));
		ga.setMnemonic(KeyEvent.VK_G);
		ga.setText("Genetic Algorithm");
		algorithms.add(ga);

		return menuBar;
	}

}
