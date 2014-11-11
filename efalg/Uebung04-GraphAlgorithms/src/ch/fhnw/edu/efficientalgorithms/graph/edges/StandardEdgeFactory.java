package ch.fhnw.edu.efficientalgorithms.graph.edges;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;

/**
 * Produces Edges labeled with a string.
 * 
 * @author Martin Schaub
 */
public class StandardEdgeFactory extends AbstractEdgeFactory<Edge> {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 3376297948443939978L;

	/**
	 * Constructor
	 */
	public StandardEdgeFactory() {
		super("Standard Edge");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.edges.EdgeFactory#newEdge()
	 */
	@Override
	public Edge newEdge() {
		return new StandardEdge("");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory#getEdgeClass()
	 */
	@Override
	public Class<StandardEdge> getEdgeClass() {
		return StandardEdge.class;
	}
}
