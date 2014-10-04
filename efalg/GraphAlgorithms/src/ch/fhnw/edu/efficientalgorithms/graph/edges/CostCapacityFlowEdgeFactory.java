package ch.fhnw.edu.efficientalgorithms.graph.edges;

import java.util.Random;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;

/**
 * Produces new edges for mincost maxflow algorithm. Random numbers are used.
 * 
 * @author Martin Schaub
 */
public final class CostCapacityFlowEdgeFactory extends AbstractEdgeFactory<CostCapacityFlowEdge> {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -4504291936686734074L;

	/**
	 * Random number generator for capacity and cost.
	 */
	private final Random rand = new Random();

	/**
	 * Constructor
	 */
	public CostCapacityFlowEdgeFactory() {
		super("Mincost Maxflow Edge");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory#getEdgeClass()
	 */
	@Override
	public Class<? extends Edge> getEdgeClass() {
		return CostCapacityFlowEdge.class;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory#newEdge()
	 */
	@Override
	public CostCapacityFlowEdge newEdge() {
		return new CostCapacityFlowEdge(rand.nextInt(20), rand.nextInt(40));
	}

}
