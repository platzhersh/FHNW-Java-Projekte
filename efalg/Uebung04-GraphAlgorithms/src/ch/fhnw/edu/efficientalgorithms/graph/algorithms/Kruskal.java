package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.auxiliary.UnionFind;

public class Kruskal<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E>  {

	public Kruskal() {
		super("Kruskal", false);
	}

	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		
		// List containing all the Edges sorted by Weight (if weighted)
		// --> seems like there are no weighted edges included in the framework
		List<E> edges = new LinkedList<E>();
		
		//HashMap<V,Integer> uf2 = new HashMap(); 
		
		// List where to add all the used Edges
		List<E> usedEdges = new LinkedList<E>();
		
		// UnionFind containing all the Vertices (all unconnected at first)
		UnionFind<V> uf = new UnionFind<V>();
		
		// get all the edges
		edges.addAll(data.getGraph().getEdges());
		
		// add all the vertices (unconnected)
		for (V v : data.getGraph().getVertices()) {
			uf.add(v);
		}
		
		/*
		if (IntegerVertex.class.isInstance(edges.get(0))) {
			//Collections.sort(edges);
		}*/
		
		// build MST with Kruskal
		for (E e : data.getGraph().getEdges()) {
			System.out.println(e.toString());
			List<V> ep = data.getGraph().getEndpoints(e);
			if(!uf.connected(ep.get(0), ep.get(1))) {
				usedEdges.add(e);
				uf.connect(ep.get(0), ep.get(1));
			} else System.out.println("Skip edge");
		}
		
		highlightEdges(data, usedEdges);
		darkenOtherEdges(data, usedEdges);
		
		
		return "finished";
		
	}

	@Override
	public boolean worksWith(Graph<V, E> graph) {
		// TODO Auto-generated method stub
		return true;
	}

}
