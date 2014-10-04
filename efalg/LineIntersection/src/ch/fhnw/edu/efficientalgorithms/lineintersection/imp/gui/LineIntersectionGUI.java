package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Algorithm;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.IntersectionDrawer;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.PlaneImpl;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.TrivialIntersectionDetection;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.scanline.ScanLineAlgorithm;

/**
 * Builds up the graphical user interface and assembles the application together.
 * 
 * @author Martin Schaub
 */
public final class LineIntersectionGUI {

	/**
	 * Constructor
	 */
	public LineIntersectionGUI() {

		Plane plane = new PlaneImpl();

		Algorithm scanLine = new ScanLineAlgorithm(plane);
		Algorithm trivial = new TrivialIntersectionDetection(plane);

		DrawPanel drawPanel = new DrawPanel(plane);
		plane.addPlaneListener(drawPanel);

		IntersectionDrawer drawer = new IntersectionDrawer(drawPanel);
		scanLine.addProgressListener(drawer);
		trivial.addProgressListener(drawer);

		MouseClickRecorder recorder = new MouseClickRecorder(plane, drawPanel);
		drawPanel.addMouseMotionListener(recorder);
		drawPanel.addMouseListener(recorder);
		scanLine.addProgressListener(recorder);
		trivial.addProgressListener(recorder);

		JFrame frame = new JFrame("Line Intersection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(createMenuBar(plane, drawPanel, scanLine, trivial));
		frame.setSize(800, 600);
		frame.add(drawPanel);
		frame.setVisible(true);

	}

	/**
	 * Creates a menubar with all needed commands.
	 * 
	 * @param plane needed for some actions
	 * @param drawer needed for some actions
	 * @param scanlineA needed for some actions
	 * @param trivialA needed for some actions
	 * @return created menubar
	 */
	private JMenuBar createMenuBar(final Plane plane, final Drawer drawer, final Algorithm scanlineA,
			final Algorithm trivialA) {
		JMenuBar menuBar = new JMenuBar();

		Object lock = new Object();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);

		JMenuItem load = new JMenuItem(new LoadAction(plane, drawer, lock));
		load.setText("Load");
		load.setMnemonic(KeyEvent.VK_L);
		file.add(load);

		JMenuItem save = new JMenuItem(new SaveAction(plane));
		save.setText("Save");
		save.setMnemonic(KeyEvent.VK_S);
		file.add(save);

		file.addSeparator();

		JMenuItem reset = new JMenuItem(new ResetAction(plane, drawer, lock));
		reset.setText("Reset");
		reset.setMnemonic(KeyEvent.VK_R);
		file.add(reset);

		file.addSeparator();

		JMenuItem exit = new JMenuItem(new ExitAction());
		exit.setText("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		file.add(exit);

		JMenu algorithms = new JMenu("Algorithms");
		algorithms.setMnemonic(KeyEvent.VK_L);
		menuBar.add(algorithms);

		JMenuItem trivial = new JMenuItem(new ExecuteAlgorithmAction(trivialA, drawer, lock));
		trivial.setText("Intersections Trivial");
		trivial.setMnemonic(KeyEvent.VK_T);
		algorithms.add(trivial);

		JMenuItem find = new JMenuItem(new ExecuteAlgorithmAction(scanlineA, drawer, lock));
		find.setText("Intersections ScanLine");
		find.setMnemonic(KeyEvent.VK_S);
		algorithms.add(find);

		JMenuItem status = new JMenuItem();
		StatusAction statusA = new StatusAction(status);
		trivialA.addProgressListener(statusA);
		scanlineA.addProgressListener(statusA);

		status.setEnabled(false);
		status.setAction(statusA);
		menuBar.add(status);

		return menuBar;
	}
}
