package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.edges.CapacityFlowEdge;

/**
 * Abstract base class for max flow implementations.
 * 
 * The virtual edges are emulated using several methods which do their job with respect to a source edge (event when in
 * the graph this edge is not a source one).
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public abstract class AbstractMaxFlowAlgorithm<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E> {

	/**
	 * Constructor
	 * 
	 * @param name algorithm name
	 */
	public AbstractMaxFlowAlgorithm(final String name) {
		super(name, true);
	}

	/**
	 * Calculate the maximum flow from source to sink. The source is red and the sink is marked orange.
	 */
	@Override
	public String execute(final GraphAlgorithmData<V, E> data) {
		// Get source and sink node
		V source = getFirstVertex(data);
		if (source == null) {
			return "Graph is empty\nnothing to do";
		}
		data.getColorMapper().setVertexColor(source, Color.RED);

		V sink = getDiffernetVertex(data, source);
		if (sink == null) {
			return "Graph needs at\nleast two nodes";
		}
		data.getColorMapper().setVertexColor(sink, Color.ORANGE);

		resetEdgeFlows(data);
		calculateMaxFlow(data, source, sink);

		return "Total Flow " + Integer.toString(calculateFlow(data, source));
	}

	/**
	 * Calculates the maximal flow.
	 * 
	 * YOUR IMPLEMENTATION OVERRIDES THIS METHOD
	 * 
	 * @param data algorithm data
	 * @param source source vertex
	 * @param sink sink vertex
	 */
	protected abstract void calculateMaxFlow(final GraphAlgorithmData<V, E> data, final V source, final V sink);

	/**
	 * Sets the flow of all edges to zero.
	 * 
	 * @param data algorithm data for obtaining the graph
	 */
	protected void resetEdgeFlows(final GraphAlgorithmData<V, E> data) {
		for (E e : data.getGraph().getEdges()) {
			((CapacityFlowEdge) e).setFlow(0);
		}
	}

	/**
	 * Calculates the flow of the network (all outgoing nodes)
	 * 
	 * @param data algorithm data for obtaining the graph
	 * @param source source vertex
	 * @return maximal flow
	 */
	protected int calculateFlow(final GraphAlgorithmData<V, E> data, final V source) {
		int flow = 0;
		for (Edge e : data.getGraph().getOutgoingEdges(source)) {
			flow += ((CapacityFlowEdge) e).getFlow();
		}
		return flow;
	}

	/**
	 * This algorithm works only with edges with flow and capacity.
	 */
	@Override
	public boolean worksWith(final Graph<V, E> graph) {
		return graph.isDirected() && CapacityFlowEdge.class.isAssignableFrom(graph.edgeClass());
	}
}
