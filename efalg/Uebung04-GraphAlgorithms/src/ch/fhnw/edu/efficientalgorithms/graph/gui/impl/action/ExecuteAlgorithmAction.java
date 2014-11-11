package ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithm;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramStateListener;

/**
 * Executes a graph algorithm.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public class ExecuteAlgorithmAction<V extends Vertex, E extends Edge> extends AbstractAction implements
		ProgramStateListener<V, E> {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 7582200860159170155L;

	/**
	 * Algorithm to execute.
	 */
	private final GraphAlgorithm<V, E> algorithm;
	/**
	 * Current program state.
	 */
	private final ProgramState<V, E> state;
	/**
	 * MenuItem that displays this entry.
	 */
	private final JMenuItem item;
	/**
	 * Label to update.
	 */
	private final JLabel labelToUpdate;

	/**
	 * Constructor
	 * 
	 * @param algorithm algorithm to execute
	 * @param state current program state
	 * @param item jmenuitem that displays this action
	 * @param labelToUpdate label that is updated with the return value.
	 */
	public ExecuteAlgorithmAction(final GraphAlgorithm<V, E> algorithm, final ProgramState<V, E> state,
			final JMenuItem item, final JLabel labelToUpdate) {
		if (algorithm == null || state == null || item == null || labelToUpdate == null) {
			throw new NullPointerException();
		}
		this.algorithm = algorithm;
		this.state = state;
		this.item = item;
		this.labelToUpdate = labelToUpdate;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		synchronized (state) {
			if (algorithm.worksWith(state.getGraph())) {
				if (algorithm.isThreadReady()) {
					new Thread(new ExecutorThread()).start();
				}
				else {
					execute();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "The " + algorithm.getName()
						+ " algorithm does not work with this type of graph.", "Algorithm does not work with this graph type",
						JOptionPane.OK_OPTION);
			}
		}
	}

	/**
	 * Check whether the current operation is supported with the new graph.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void newGraphLoaded(final Graph<V, E> currentGraph, final Graph<V, E> oldGraph) {
		synchronized (state) {
			item.setEnabled(algorithm.worksWith(currentGraph));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramStateListener#graphChanged()
	 */
	@Override
	public void graphChanged() {
		// nothing to do
	}

	/**
	 * Executes the algorithm.
	 */
	private void execute() {
		state.getColorMapper().reset();
		String ret = algorithm.execute(new GraphAlgorithmDataImpl<V, E>(state));
		if (ret != null) {
			// Support for multiline statements
			labelToUpdate.setText("<html><body>" + ret.replaceAll("\n", "<br>") + "</body></html>");
		}
	}

	/**
	 * Executes the algorithm so the AWT process can resume to repaint or do other stuff.
	 */
	private final class ExecutorThread implements Runnable {

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			execute();
		}
	}
}
