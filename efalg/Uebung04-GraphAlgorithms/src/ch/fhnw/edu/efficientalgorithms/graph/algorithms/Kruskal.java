package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

public class Kruskal<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E>  {

	public Kruskal() {
		super("Kruskal", true);
	}

	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		// TODO Auto-generated method stub
		return "to be implemented";
	}

	@Override
	public boolean worksWith(Graph<V, E> graph) {
		// TODO Auto-generated method stub
		return true;
	}

}
