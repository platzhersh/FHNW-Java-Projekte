package ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;
import ch.fhnw.edu.efficientalgorithms.graph.impl.UniversalGraph;

/**
 * Creates a new graph with the selected properties.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public class NewAction<V extends Vertex, E extends Edge> extends AbstractAction {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1453863401230928174L;

	/**
	 * Class of the edge factory for instantiation
	 */
	private final Class<?> edgeFactory;
	/**
	 * Program state to change
	 */
	private final ProgramState<V, E> programState;
	/**
	 * Whether the new graph must be directed or not.
	 */
	private final boolean directed;

	/**
	 * Constructor
	 * 
	 * @param programState program state to change
	 * @param edgeFactory edge factory to set
	 * @param directed if the new graph should be directed or not.
	 */
	public NewAction(final ProgramState<V, E> programState, final Class<?> edgeFactory, final boolean directed) {
		if (edgeFactory == null || programState == null) {
			throw new NullPointerException();
		}
		this.edgeFactory = edgeFactory;
		this.programState = programState;
		this.directed = directed;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(final ActionEvent event) {
		synchronized (programState) {
			try {
				EdgeFactory<E> factory = (EdgeFactory<E>) edgeFactory.newInstance();
				programState.getColorMapper().reset();
				programState.getLocationMapper().reset();
				programState.setEdgeFactory(factory);
				programState.setGraph(new UniversalGraph<V, E>(directed, factory.getEdgeClass(), programState
						.getVertexFactory().getVertexClass()));
			}
			catch (final InstantiationException e) {
				System.err.println("Error loading " + edgeFactory.getName());
			}
			catch (final IllegalAccessException e) {
				System.err.println("Error loading " + edgeFactory.getName());
			}
		}
	}
}
