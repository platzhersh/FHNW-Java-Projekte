package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;

/***
 * Builds an MST using Kruskal algorithm
 * 
 * @author chregi
 *
 * @param <V> vertex type
 * @param <E> edge type
 */
public class Kruskal<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E>  {

	public Kruskal() {
		super("Kruskal", true);
	}

	/***
	 * Puts all the vertices in separate sets, then iterates through the list of edges (sorted ascending by weight)
	 * if endpoints of the edge are in the same set it will be ignored
	 * if endpoints of the edge are in different sets, it will be added to the spanning tree and the sets containing
	 * the two endpoints will be unified
	 * 
	 * The MST will be highlighted in RED
	 */
	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		
		// List containing all the Edges sorted by Weight (if weighted)
		List<E> edges = new LinkedList<E>();
		edges.addAll(data.getGraph().getEdges());
		
		// sort if edges are weighted
		//Comparator<E> byWeightDESC = (e1, e2) -> Integer.parseInt(e2.getLabel()) - Integer.parseInt(e1.getLabel());
		Comparator<E> byWeightASC = (e1, e2) -> Integer.parseInt(e1.getLabel()) - Integer.parseInt(e2.getLabel());
		boolean isWeighted = data.getGraph().edgeClass().equals(IntegerEdge.class); 

		if (isWeighted) {
			edges.sort(byWeightASC);
		}
		
		// List where to add all the used Edges
		List<E> usedEdges = new LinkedList<E>();
		
		HashMap<V,Integer> uf = new HashMap<V, Integer>();
		
		// initialize uf2
		Object vArr[] = data.getGraph().getVertices().toArray();
		for (int i = 0; i < vArr.length; i++) {
			uf.put((V) vArr[i], i);
		}
		
		// build MST with Kruskal and new uf
		for (E e : edges) {
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
			}
		}
		
		highlightEdges(data, usedEdges);
		darkenOtherEdges(data, usedEdges);
		
		return "finished";
		
	}

	/***
	 * only works with undirected graphs
	 */
	@Override
	public boolean worksWith(Graph<V, E> graph) {
		return !graph.isDirected();
	}

}
