package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
		
		// List containing all the Edges sorted by Weight (if weighted)
		// --> seems like there are no weighted edges included in the framework
		List<E> edges = new LinkedList<E>();
		edges.addAll(data.getGraph().getEdges());
		
		// List where to add all the used Edges
		List<E> usedEdges = new LinkedList<E>();
		
		HashMap<V,Integer> uf = new HashMap();
		
		// initialize uf2
		Object vArr[] = data.getGraph().getVertices().toArray();
		for (int i = 0; i < vArr.length; i++) {
			uf.put((V) vArr[i], i);
		}
		
		// build MST with Kruskal and new uf
		for (E e : edges) {
			System.out.println(e.toString());
			List<V> ep = data.getGraph().getEndpoints(e);
			if(uf.get(ep.get(0)).intValue() != uf.get(ep.get(1)).intValue()) {
				usedEdges.add(e);
				int ref = uf.get(ep.get(0));
				int trgt = uf.get(ep.get(1));
				for (V v : data.getGraph().getVertices()) {
					if (uf.get(v) == ref) {
						uf.put(v, trgt);
					}
				}
			} else System.out.println("Skip edge");
		}
		
		highlightEdges(data, usedEdges);
		darkenOtherEdges(data, usedEdges);
		
		return "finished";
		
	}

	@Override
	public boolean worksWith(Graph<V, E> graph) {
		return !graph.isDirected();
	}

}
