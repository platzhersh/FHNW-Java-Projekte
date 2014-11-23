package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

public class MincostMaxflow<V extends Vertex, E extends Edge> extends AbstractMaxFlowAlgorithm<V, E> {

	public MincostMaxflow() {
		super("Mincost Maxflow");
	}


	@Override
	protected void calculateMaxFlow(GraphAlgorithmData<V, E> data, V source, V sink) {

	}

}
