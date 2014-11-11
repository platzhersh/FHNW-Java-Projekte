package ch.fhnw.edu.efficientalgorithms.graph.gui.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithm;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState.State;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action.ExecuteAlgorithmAction;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action.ExitAction;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action.LoadAction;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action.NewAction;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action.RandomGraph;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action.ResetAction;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action.SaveAction;
import ch.fhnw.edu.efficientalgorithms.graph.impl.UniversalGraph;

/**
 * Assembles the graphical components together into a graphical user interface.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public class GraphGUI<V extends Vertex, E extends Edge> {

	/**
	 * Constructor
	 * 
	 * @param state program state
	 */
	public GraphGUI(final ProgramState<V, E> state) {
		if (state == null) {
			throw new NullPointerException();
		}

		GraphPanel<V, E> graphPanel = new GraphPanel<V, E>(state);
		state.addStateListener(graphPanel);
		state.getColorMapper().addColorMapperListener(graphPanel);
		state.getLocationMapper().addLocationListener(graphPanel);
		graphPanel.setBackground(Color.white);

		MouseCatcher<V, E> mouseCatcher = new MouseCatcher<V, E>(state);
		graphPanel.addMouseListener(mouseCatcher);
		graphPanel.addMouseMotionListener(mouseCatcher);

		JLabel statusLabel = new JLabel("");

		JFrame frame = new JFrame("Graph Algorithm Visualization");
		frame.setLayout(new BorderLayout());
		frame.add(graphPanel, BorderLayout.CENTER);
		frame.add(createOptionsPanel(state, statusLabel), BorderLayout.EAST);

		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(createMenuBar(state, statusLabel));
		frame.setVisible(true);

		// for correct initialization a new graph is created
		state.setGraph(new UniversalGraph<V, E>(true, state.getEdgeFactory().getEdgeClass(), state.getVertexFactory()
				.getVertexClass()));
	}

	/**
	 * Creates a option panel on the right side of the application.
	 * 
	 * @param state needed parameter
	 * @param statusLabel statusLabel to add.
	 * @return the newly created option panel
	 */
	private JPanel createOptionsPanel(final ProgramState<V, E> state, final JLabel statusLabel) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		panel.add(createSelectionPanel(state), BorderLayout.NORTH);

		panel.add(statusLabel, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Creates a selection panel.
	 * 
	 * @param state needed parameter
	 * @return the newly created selection panel
	 */
	private JPanel createSelectionPanel(final ProgramState<V, E> state) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Mode"));
		panel.setLayout(new GridLayout(5, 1));

		ButtonGroup group = new ButtonGroup();

		JRadioButton select = new JRadioButton("Selection");
		select.setSelected(true);
		select.addActionListener(new ProgramStateChanger<V, E>(state, State.Selection));
		group.add(select);
		panel.add(select);

		JRadioButton createNode = new JRadioButton("Create Node");
		createNode.addActionListener(new ProgramStateChanger<V, E>(state, State.NodeInsert));
		group.add(createNode);
		panel.add(createNode);

		JRadioButton createEdge = new JRadioButton("Create Edge");
		createEdge.addActionListener(new ProgramStateChanger<V, E>(state, State.EdgeInsert));
		group.add(createEdge);
		panel.add(createEdge);

		JRadioButton deleteNode = new JRadioButton("Delete Node");
		deleteNode.addActionListener(new ProgramStateChanger<V, E>(state, State.NodeDelete));
		group.add(deleteNode);
		panel.add(deleteNode);

		JRadioButton deleteEdge = new JRadioButton("Delete Edge");
		deleteEdge.addActionListener(new ProgramStateChanger<V, E>(state, State.EdgeDelete));
		group.add(deleteEdge);
		panel.add(deleteEdge);

		return panel;
	}

	/**
	 * Creates a new JMenuItem with this characteristics and adds it to the parent menu bar.
	 * 
	 * @param name name of the new entry
	 * @param action action to execute
	 * @param mnemonic keyboard shortcut
	 * @param parent parent component to add
	 */
	private void addMenuItem(final String name, final Action action, final int mnemonic, final JMenu parent) {
		JMenuItem item = new JMenuItem(action);
		item.setText(name);
		item.setMnemonic(mnemonic);
		item.setEnabled(true);
		parent.add(item);
	}

	/**
	 * Creates the menu bar of the jframe.
	 * 
	 * @param state needed as parameter for the action
	 * @param statusLabel statusLabel for the algorithm actions
	 * @return created menu bar.
	 */
	private JMenuBar createMenuBar(final ProgramState<V, E> state, final JLabel statusLabel) {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);

		file.add(createNew(state));

		file.addSeparator();
		addMenuItem("Reset", new ResetAction<V, E>(state), KeyEvent.VK_R, file);
		file.add(createRandomGraphMenu(state));

		file.addSeparator();

		addMenuItem("Save", new SaveAction<V, E>(state), KeyEvent.VK_S, file);
		addMenuItem("Load", new LoadAction<V, E>(state), KeyEvent.VK_L, file);

		file.addSeparator();

		addMenuItem("Exit", new ExitAction(), KeyEvent.VK_X, file);

		JMenu algorithms = new JMenu("Algorithms");
		algorithms.setMnemonic(KeyEvent.VK_A);
		menuBar.add(algorithms);

		addAlgorithms(algorithms, state, statusLabel);

		return menuBar;
	}

	/**
	 * Creates the random graph menu.
	 * 
	 * @param state state for creating the action
	 * @return jmenu that stores the menu items.
	 */
	private JMenu createRandomGraphMenu(final ProgramState<V, E> state) {
		JMenu menu = new JMenu("Random Graph");
		menu.setMnemonic(KeyEvent.VK_A);

		JMenu sparse = new JMenu("Sparse");
		menu.add(sparse);

		JMenu dense = new JMenu("Dense");
		menu.add(dense);

		for (int i = 5; i < 50; i = i + 5) {
			addMenuItem(Integer.toString(i), new RandomGraph<V, E>(state, i, 2), 0, sparse);
			addMenuItem(Integer.toString(i), new RandomGraph<V, E>(state, i, i / 3), 0, dense);
		}
		return menu;
	}

	/**
	 * Creates the new menu.
	 * 
	 * @param state state for the newAction
	 * @return created menu
	 */
	@SuppressWarnings("unchecked")
	private JMenu createNew(final ProgramState<V, E> state) {
		JMenu menu = new JMenu("New");
		menu.setMnemonic(KeyEvent.VK_N);

		JMenu directedMenu = new JMenu("directed");
		menu.add(directedMenu);
		JMenu undirectedMenu = new JMenu("undirected");
		menu.add(undirectedMenu);

		for (Class<?> clazz : getAllClasses("ch.fhnw.edu.efficientalgorithms.graph.edges", EdgeFactory.class)) {
			try {
				EdgeFactory<E> factory = (EdgeFactory<E>) clazz.newInstance();

				JMenuItem directed = new JMenuItem(new NewAction<V, E>(state, clazz, true));
				JMenuItem undirected = new JMenuItem(new NewAction<V, E>(state, clazz, false));

				directed.setText(factory.getName());
				undirected.setText(factory.getName());

				directedMenu.add(directed);
				undirectedMenu.add(undirected);
			}
			catch (final InstantiationException e) {
				System.err.println("Error loading algorithm " + clazz.getName());
			}
			catch (final IllegalAccessException e) {
				System.err.println("Error loading algorithm " + clazz.getName());
			}
		}

		return menu;
	}

	/**
	 * Search for algorithms and adds them to the menu
	 * 
	 * @param menu menu to add the found algorithms
	 * @param state needed as parameter for the action
	 * @param statusLabel label to update from the algorithm.
	 */
	@SuppressWarnings("unchecked")
	private void addAlgorithms(final JMenu menu, final ProgramState<V, E> state, final JLabel statusLabel) {
		for (Class<?> clazz : getAllClasses("ch.fhnw.edu.efficientalgorithms.graph.algorithms", GraphAlgorithm.class)) {
			try {
				GraphAlgorithm<V, E> algorithm = (GraphAlgorithm<V, E>) clazz.newInstance();

				JMenuItem item = new JMenuItem();
				ExecuteAlgorithmAction algorithmAction = new ExecuteAlgorithmAction<V, E>(algorithm, state, item, statusLabel);
				state.addStateListener(algorithmAction);

				item.setAction(algorithmAction);
				item.setText(algorithm.getName());
				menu.add(item);
			}
			catch (final InstantiationException e) {
				System.err.println("Error loading algorithm " + clazz.getName());
			}
			catch (final IllegalAccessException e) {
				System.err.println("Error loading algorithm " + clazz.getName());
			}
		}
	}

	/**
	 * Gets all classes in the package which aren't abstract and implement the interface or base class.
	 * 
	 * @param packageName package to search
	 * @param type super type of the class, or object if this is not needed
	 * @return list of classes that satisfies this properties.
	 */
	private List<Class<?>> getAllClasses(final String packageName, final Class<?> type) {
		List<Class<?>> foundClasses = new LinkedList<Class<?>>();

		URL packageURL = getClass().getResource("/" + packageName.replaceAll("\\.", "/"));
		File packageDir = new File(packageURL.getPath());
		if (packageDir.isDirectory()) {
			for (File f : packageDir.listFiles()) {
				if (f.isFile() && f.getName().endsWith(".class")) {
					try {
						String className = packageName + "." + f.getName().replaceAll(".class$", "");
						Class<?> clazz = Class.forName(className);
						if ((clazz.getModifiers() & Modifier.ABSTRACT) == 0 && type.isAssignableFrom(clazz)) {
							foundClasses.add(clazz);
						}
					}
					catch (final ClassNotFoundException e) {
						// do nothing, because then the class just cannot be loaded.
					}
				}
			}
		}

		return foundClasses;
	}
}
