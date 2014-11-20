package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;

public class MSTHeuristik<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E>  {

	public MSTHeuristik() {
		super("MST Heuristik", true);
	}

	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		
		// make kruskal and then use the found edges for MST
		
		/* --- Start Kruskal --- */
		
		// List containing all the Edges sorted by Weight (if weighted)
		// --> seems like there are no weighted edges included in the framework
		List<E> edges = new LinkedList<E>();
		edges.addAll(data.getGraph().getEdges());
		
		// sort if edges are weighted
		if (edges.get(0).getClass().isInstance(IntegerEdge.class)) {
			edges.sort(new Comparator<E>() {

				@Override
				public int compare(E o1, E o2) {
					return Integer.parseInt(o1.getLabel()) - Integer.parseInt(o2.getLabel());
				}
				
			});
		}
		
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
		
		/* --- End Kruskal --- */
		
		// create MST graph
		Graph<V, E> newGraph = data.getGraph().clone();
		for (E e : edges) {
			if (!usedEdges.contains(e)) newGraph.removeEdge(e);
		}
		
		List<V> visited = new LinkedList<V>();
		
		// get starting point
		V start = getFirstVertex(data);
		if (start == null) {
			return "Empty graph,\nnothing to do";
		}
		data.getColorMapper().setVertexColor(start, Color.RED);
		
		int hamiltonCost = 0;
		V cur = start;
		
		//if (edges.get(0).getClass().isInstance(IntegerEdge.class)) {
		for (Edge e : newGraph.getEdges()) {
			hamiltonCost += Integer.parseInt(e.getLabel()) * 2;
		}	
		
		return Integer.toString(hamiltonCost);
		
	}

	@Override
	public boolean worksWith(Graph<V, E> graph) {
		return !graph.isDirected();
	}

}
