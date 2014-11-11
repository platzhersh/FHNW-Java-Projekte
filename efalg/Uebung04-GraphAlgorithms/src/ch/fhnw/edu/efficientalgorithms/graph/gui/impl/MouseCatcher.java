package ch.fhnw.edu.efficientalgorithms.graph.gui.impl;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;

/**
 * Catches the mouse movements.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public final class MouseCatcher<V extends Vertex, E extends Edge> implements MouseListener, MouseMotionListener {

	/**
	 * Saves references to some needed data structures.
	 */
	private final ProgramState<V, E> state;
	/**
	 * Last selected vertex.
	 */
	private V lastVertex;
	/**
	 * Original color of the vertex;
	 */
	private Color lastColor;

	/**
	 * Constructor
	 * 
	 * @param state needed references
	 */
	public MouseCatcher(final ProgramState<V, E> state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(final MouseEvent e) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(final MouseEvent e) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(final MouseEvent e) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(final MouseEvent e) {

		if (lastVertex != null) {
			state.getColorMapper().setVertexColor(lastVertex, lastColor);
		}

		V cur = state.getLocationMapper().getAtPosition(e.getX(), e.getY());
		switch (state.getState()) {

		case Selection:
			if (cur == null) {
				lastVertex = null;
				lastColor = null;
			}
			else {
				lastVertex = cur;
				lastColor = state.getColorMapper().getVertexColor(cur);
			}
		break;

		case NodeDelete:
			lastVertex = null;
			if (cur != null) {
				state.getGraph().removeVertex(cur);
			}
		break;

		case NodeInsert:
			lastVertex = null;
			V newV = state.getVertexFactory().newVertex();
			state.getLocationMapper().setLocation(newV, e.getX(), e.getY());
			state.getGraph().addVertex(newV);
		break;

		case EdgeInsert:
			if (lastVertex == null) {
				if (cur != null) {
					lastVertex = cur;
					lastColor = state.getColorMapper().getVertexColor(cur);
				}
			}
			else {
				if (cur != null) {
					if (cur.equals(lastVertex)) {
						lastVertex = null;
						lastColor = null;
					}
					else {
						state.getGraph().addEdge(lastVertex, cur, state.getEdgeFactory().newEdge());
						lastVertex = null;
						lastColor = null;
					}
				}
			}
		break;

		case EdgeDelete:
			if (lastVertex == null) {
				if (cur != null) {
					lastVertex = cur;
					lastColor = state.getColorMapper().getVertexColor(cur);
				}
			}
			else {
				if (cur != null) {
					E edge = state.getGraph().getEdge(lastVertex, cur);
					if (edge != null) {
						state.getGraph().removeEdge(edge);
					}
					lastVertex = null;
				}
			}
		break;

		default:
			throw new InternalError();
		}

		if (lastVertex != null) {
			state.getColorMapper().setVertexColor(lastVertex, Color.GREEN);
		}

		state.setCurrentlySelectedVertex(lastVertex);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(final MouseEvent e) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(final MouseEvent e) {
		switch (state.getState()) {

		case Selection:
			if (lastVertex != null) {
				state.getLocationMapper().setLocation(lastVertex, e.getX(), e.getY());
			}
		break;

		default: // nothing to do

		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(final MouseEvent e) {
		// nothing to do
	}

}
