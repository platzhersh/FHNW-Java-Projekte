package ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;

/**
 * Resets the graph.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public class ResetAction<V extends Vertex, E extends Edge> extends AbstractAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -5599812128300196578L;

	/**
	 * Program state to change
	 */
	private final ProgramState<V, E> programState;

	/**
	 * Constructor
	 * 
	 * @param programState program state
	 */
	public ResetAction(final ProgramState<V, E> programState) {
		if (programState == null) {
			throw new NullPointerException();
		}
		this.programState = programState;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		Graph<V, E> graph = programState.getGraph();
		for (V v : new ArrayList<V>(graph.getVertices())) {
			graph.removeVertex(v);
		}
	}
}
