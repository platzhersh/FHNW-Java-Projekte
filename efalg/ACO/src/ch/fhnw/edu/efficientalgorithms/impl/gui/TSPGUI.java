package ch.fhnw.edu.efficientalgorithms.impl.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import ch.fhnw.edu.efficientalgorithms.algorithms.aco.acs.ACSAlgorithm;
import ch.fhnw.edu.efficientalgorithms.algorithms.aco.acs.ACSTunable;
import ch.fhnw.edu.efficientalgorithms.algorithms.aco.as.ASAlgorithm;
import ch.fhnw.edu.efficientalgorithms.algorithms.aco.as.ASTunable;
import ch.fhnw.edu.efficientalgorithms.algorithms.mstheuristic.TwoComparativeAlgorithm;
import ch.fhnw.edu.efficientalgorithms.algorithms.mstheuristic.TwoComparativeTunable;
import ch.fhnw.edu.efficientalgorithms.impl.TSPAlgorithmStatusImpl;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;

/**
 * Builds up the graphical user interface for the application.
 *
 * @author Martin Schaub
 */
public final class TSPGUI {

	/**
	 * Constructor
	 *
	 * Creates a graphical user interface
	 *
	 * @param model TSP model to display
	 */
	public TSPGUI(final TSPModel model) {
		JFrame mainWindow = new JFrame("Ant Colony Optimization");
		mainWindow.setSize(800, 600);
		mainWindow.setLayout(new BorderLayout());

		TSPAlgorithmStatus status = new TSPAlgorithmStatusImpl();

		JTabbedPane tunablePanel = new JTabbedPane();
		List<TSPAlgorithmTunable> tunables = addTunables(tunablePanel, model, status);

		mainWindow.add(tunablePanel, BorderLayout.WEST);

		JLabel statusLabel = new JLabel("not running");
		StatusUpdater updater = new StatusUpdater(statusLabel);
		model.addTSPModelLister(updater);
		mainWindow.add(statusLabel, BorderLayout.SOUTH);

		TSPDrawer drawer = new TSPDrawer(model);
		drawer.setBackground(Color.WHITE);
		drawer.addMouseListener(new ClickRecorder(model, status));

		model.addTSPModelLister(drawer);

		createMenuBar(mainWindow, model, tunables, status);

		mainWindow.add(drawer, BorderLayout.CENTER);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
	}

	/**
	 * Adds all tunables to the panel and returns a list of this tunables.
	 *
	 * @param tunablePanel where the grphical tunables are added
	 * @param model needed for the graphical tunable
	 * @param status needed for the graphical tunable
	 *
	 * @return a list of all tunables
	 */
	private List<TSPAlgorithmTunable> addTunables(final JTabbedPane tunablePanel, final TSPModel model,
			final TSPAlgorithmStatus status) {
		List<TSPAlgorithmTunable> tunables = new LinkedList<TSPAlgorithmTunable>();

		TSPAlgorithmTunable acsTunable = new ACSTunable(new ACSAlgorithm());
		tunablePanel.addTab("Ant Colony System", getGraphicalTunable(acsTunable, model, status));
		tunables.add(acsTunable);

		TSPAlgorithmTunable asTunable = new ASTunable(new ASAlgorithm());
		tunablePanel.addTab("Ant System", getGraphicalTunable(asTunable, model, status));
		tunables.add(asTunable);

		TSPAlgorithmTunable twoComp = new TwoComparativeTunable(new TwoComparativeAlgorithm());
		tunablePanel.addTab("MST heuristic", getGraphicalTunable(twoComp, model, status));
		tunables.add(twoComp);

		return tunables;
	}

	/**
	 * Creates a graphical panel for changing the tunables of algorithms.
	 *
	 * @param tunable tunable to tune
	 * @param model model for starting an action if the start button is hit
	 * @param status status for starting an action if the start button is hit
	 *
	 * @return the panel
	 */
	private JComponent getGraphicalTunable(final TSPAlgorithmTunable tunable, final TSPModel model,
			final TSPAlgorithmStatus status) {

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		List<String> attributes = tunable.getAttributes();
		JPanel attributePanel = new JPanel();
		attributePanel.setLayout(new GridLayout(attributes.size(), 2));
		for (String attribute : attributes) {
			attributePanel.add(new JLabel(attribute));

			JTextField field = new JTextField(tunable.getValue(attribute));
			AlgorithmAttributeUpdate update = new AlgorithmAttributeUpdate(tunable, attribute, field);
			field.addFocusListener(update);
			field.addActionListener(update);

			TunableUpdater tunUpdater = new TunableUpdater(attribute, field, tunable);
			tunable.addTSPAlgorithmTunableListener(tunUpdater);

			attributePanel.add(field);
		}
		panel.add(attributePanel, BorderLayout.NORTH);

		JButton start = new JButton("Start");
		start.addActionListener(new StartAlgorithmAction(tunable.getAlgorithm(), model, status));
		panel.add(start, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Creates a menubar for the submitted frame.
	 *
	 * @param frame frame to add the menubar
	 * @param model tsp model for various actions
	 * @param tunables all tunables
	 * @param status algorithm status for actions
	 */
	private void createMenuBar(final JFrame frame, final TSPModel model, final List<TSPAlgorithmTunable> tunables,
			final TSPAlgorithmStatus status) {
		JMenuBar menu = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem save = new JMenuItem("Save");
		save.setMnemonic(KeyEvent.VK_S);
		save.addActionListener(new SaveAction(model, tunables));
		file.add(save);

		JMenuItem load = new JMenuItem("Load");
		load.setMnemonic(KeyEvent.VK_L);
		load.addActionListener(new LoadAction(model, tunables, status));
		file.add(load);

		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		exit.addActionListener(new ExitAction(frame));
		file.add(exit);

		menu.add(file);

		frame.setJMenuBar(menu);
	}
}
