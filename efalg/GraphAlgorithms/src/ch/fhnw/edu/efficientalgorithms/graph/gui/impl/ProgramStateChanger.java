package ch.fhnw.edu.efficientalgorithms.graph.gui.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState.State;

/**
 * Changes the global GUI state if the action is triggered.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public class ProgramStateChanger<V extends Vertex, E extends Edge> implements ActionListener {

	/**
	 * Stores the current program state.
	 */
	private final ProgramState<V, E> state;
	/**
	 * State to set.
	 */
	private final State toSet;

	/**
	 * Constructor
	 * 
	 * @param state state to change.
	 * @param toSet sets this state if the action is triggered.
	 */
	public ProgramStateChanger(final ProgramState<V, E> state, final State toSet) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
		this.toSet = toSet;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		state.setState(toSet);
	}
}
