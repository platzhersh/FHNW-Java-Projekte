package ch.fhnw.edu.efficientalgorithms.graph.edges;

import java.util.Random;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;

/**
 * Produces new edges with a random flow.
 * 
 * @author Martin Schaub
 */
public final class CapacityFlowEdgeFactory extends AbstractEdgeFactory<CapacityFlowEdge> {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 5700182492806614697L;

	/**
	 * For creating new edges.
	 */
	private final Random rand = new Random();

	/**
	 * Constructor
	 */
	public CapacityFlowEdgeFactory() {
		super("Max Flow Edge");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory#newEdge()
	 */
	@Override
	public CapacityFlowEdge newEdge() {
		return new CapacityFlowEdge(rand.nextInt(20) + 1);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory#getEdgeClass()
	 */
	@Override
	public Class<? extends Edge> getEdgeClass() {
		return CapacityFlowEdge.class;
	}

}
