package ch.fhnw.edu.efficientalgorithms.graph.gui.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapperListener;
import ch.fhnw.edu.efficientalgorithms.graph.gui.LocationListener;
import ch.fhnw.edu.efficientalgorithms.graph.gui.LocationMapper;
import ch.fhnw.edu.efficientalgorithms.graph.gui.Node;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramStateListener;

/**
 * Displays the graph graphically.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public class GraphPanel<V extends Vertex, E extends Edge> extends JPanel implements ProgramStateListener<V, E>,
		ColorMapperListener<V, E>, LocationListener<V> {

	/**
	 * Stores the diameter of the nodes.
	 */
	private static final int NODE_SIZE = 30;
	/**
	 * Length of the arrow head.
	 */
	private static final int ARROW_HEAD_LENGTH = 10;

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 3533051136204568846L;

	/**
	 * Current state of the program.
	 */
	private final ProgramState<V, E> programState;

	/**
	 * Constructor
	 * 
	 * @param programState current program state
	 */
	public GraphPanel(final ProgramState<V, E> programState) {
		if (programState == null) {
			throw new NullPointerException();
		}
		this.programState = programState;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.ProgramStateListener#newGraphLoaded(ch.fhnw.edu.efficientalgorithms.graph
	 * .Graph, ch.fhnw.edu.efficientalgorithms.graph.Graph)
	 */
	@Override
	public void newGraphLoaded(final Graph<V, E> currentGraph, final Graph<V, E> oldGraph) {
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramStateListener#graphChanged()
	 */
	@Override
	public void graphChanged() {
		repaint();
	}

	/**
	 * Draws the graph
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void paint(final Graphics g) {
		synchronized (programState) {
			super.paint(g);
			Graphics2D graphics = (Graphics2D) g;

			Graph<V, E> graph = programState.getGraph();
			LocationMapper<V> mapper = programState.getLocationMapper();
			Color original = g.getColor();

			// Draw edges (first, so they can be overwritten by the vertices)
			for (E edge : graph.getEdges()) {
				List<V> vertices = graph.getEndpoints(edge);
				Node<V> from = mapper.getLocation(vertices.get(0));
				Node<V> to = mapper.getLocation(vertices.get(1));

				graphics.setColor(programState.getColorMapper().getEdgeColor(edge));
				graphics.drawLine(from.getX(), from.getY(), to.getX(), to.getY());

				int deltaW = from.getX() - to.getX();
				int deltaH = from.getY() - to.getY();
				// draw an arrow
				if (graph.isDirected()) {
					int[] xpoints = new int[4];
					int[] ypoints = new int[4];

					// At the border of the destination vertex
					double length = Math.sqrt(deltaW * deltaW + deltaH * deltaH);
					xpoints[0] = (int) (to.getX() + (deltaW / length) * NODE_SIZE / 2);
					xpoints[3] = (int) (to.getX() + (deltaW / length) * NODE_SIZE / 2);
					ypoints[0] = (int) (to.getY() + (deltaH / length) * NODE_SIZE / 2);
					ypoints[3] = (int) (to.getY() + (deltaH / length) * NODE_SIZE / 2);

					// intersection of the line with the arrow head
					double abW = to.getX() + (deltaW / length) * (NODE_SIZE + ARROW_HEAD_LENGTH);
					double abH = to.getY() + (deltaH / length) * (NODE_SIZE + ARROW_HEAD_LENGTH);

					// Orthogonal vector to (deltaW,deltaH) is (recW,-recH) or (-recW,recH)
					double recW = deltaH;
					double recH = deltaW;
					double recLength = Math.sqrt(recW * recW + recH * recH);

					xpoints[1] = (int) (recW / recLength * ARROW_HEAD_LENGTH / 2 + abW);
					ypoints[1] = (int) (-recH / recLength * ARROW_HEAD_LENGTH / 2 + abH);
					xpoints[2] = (int) (-recW / recLength * ARROW_HEAD_LENGTH / 2 + abW);
					ypoints[2] = (int) (recH / recLength * ARROW_HEAD_LENGTH / 2 + abH);

					graphics.fillPolygon(xpoints, ypoints, 4);
				}

				// draw text in the middle of the line
				graphics.setColor(Color.BLACK);
				graphics.drawString(edge.getLabel(), to.getX() + deltaW / 3, to.getY() + deltaH / 3);
			}

			// Draw nodes
			for (V vertex : graph.getVertices()) {
				Node<V> node = mapper.getLocation(vertex);
				graphics.setColor(programState.getColorMapper().getVertexColor(vertex));
				graphics.fillOval(node.getX() - NODE_SIZE / 2, node.getY() - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
				graphics.setColor(Color.BLACK);
				graphics.drawOval(node.getX() - NODE_SIZE / 2, node.getY() - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
				graphics.drawString(vertex.toString(), node.getX() - 3, node.getY() + 4);
			}

			graphics.setColor(original);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapperListener#newEdgeColor(ch.fhnw.edu.efficientalgorithms.graph
	 * .Edge, java.awt.Color)
	 */
	@Override
	public void newEdgeColor(final E e, final Color newColor) {
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapperListener#newVertexColor(ch.fhnw.edu.efficientalgorithms.graph
	 * .Vertex, java.awt.Color)
	 */
	@Override
	public void newVertexColor(final V v, final Color newColor) {
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.gui.LocationListener#locationChanged(ch.fhnw.edu.efficientalgorithms.graph
	 * .Vertex)
	 */
	@Override
	public void locationChanged(final V v) {
		repaint();
	}
}
