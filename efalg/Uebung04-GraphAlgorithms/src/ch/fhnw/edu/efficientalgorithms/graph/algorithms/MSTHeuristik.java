package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

/***
 * 
 * Uses Kruskal algorithm to span a MST and create a Hamilton circle from that
 * 
 * 
 * @author chregi
 *
 * @param <V> vertex type
 * @param <E> edge type
 */
public class MSTHeuristik<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E>  {

	public MSTHeuristik() {
		super("MST Heuristik", true);
	}
	/***
	 * run Kruskal algorithm first and then apply MST-heuristic
	 */
	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		
		// make kruskal and then use the found edges for MST
		
		/* --- Start Kruskal --- */
		
		// List containing all the Edges sorted by Weight (if weighted)
		List<E> edges = new LinkedList<E>();
		edges.addAll(data.getGraph().getEdges());
		
		// sort if edges are weighted
		Comparator<E> byWeightDESC = (e1, e2) -> Integer.parseInt(e2.getLabel()) - Integer.parseInt(e1.getLabel());
		Comparator<E> byWeightASC = (e1, e2) -> Integer.parseInt(e1.getLabel()) - Integer.parseInt(e2.getLabel());
		boolean isWeighted = data.getGraph().edgeClass().equals(IntegerEdge.class); 
		
		if (isWeighted) {
			edges.sort(byWeightASC);
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
		
		/* --- End Kruskal --- */
		
		// create MST graph
		Graph<V, E> mstGraph = data.getGraph().clone();
		for (E e : edges) {
			if (!usedEdges.contains(e)) mstGraph.removeEdge(e);
		}
		
		// get starting point with degree 1 
		// uncomment commented code to take smallest outgoing edge to calculate hamilton circle
		V start = null;
		while (start == null) {
			if (isWeighted) {
				int min = Integer.MAX_VALUE/2;
				for (V v : mstGraph.getVertices()) {
					if (mstGraph.getOutgoingEdges(v).size() <= 1) {
						List<E> ed = new ArrayList<E>(mstGraph.getOutgoingEdges(v));
						int weight = Integer.parseInt(ed.get(0).getLabel());
						if (weight < min) {
							min = weight;
							start = v;
						}
					}
				}
			} else {
				for (V v : mstGraph.getVertices()) {
					if (mstGraph.getOutgoingEdges(v).size() == 1) start = v;
				}
			}
		}
		
		List<V> visited = new LinkedList<V>();	
		data.getColorMapper().setVertexColor(start, Color.RED);
		
		int hamiltonCost = 0;		
		Stack<V> toVisit = new Stack<V>();
		toVisit.add(start);
		List<E> hamiltonCircle = new ArrayList<E>(usedEdges);
		V deadEnd = null;
		V lastDeadEnd = null;
		
		while (!toVisit.empty()) {
			V cur = toVisit.pop();
			visited.add(cur);
			//List<E> outgoing = new ArrayList<E>(mstGraph.getOutgoingEdges(cur).stream().filter(e -> !visited.contains(e)).collect(Collectors.toList()));
			List<E> outgoing = new ArrayList<E>(mstGraph.getOutgoingEdges(cur));
			if (isWeighted) outgoing.sort(byWeightDESC);
			
			if (outgoing.size() <= 1 && cur != start) {
				if (deadEnd == null) {
					lastDeadEnd = deadEnd = cur; 
				} else {
					hamiltonCircle.remove(outgoing.get(0));
					E edge = data.getGraph().getEdge(deadEnd, cur);
					// if there is no edge available between two following deadends the graph is invalid --> abort
					if (null == edge) {
						highlightEdges(data, hamiltonCircle, Color.BLUE);
						return "invalid graph, can't make a circle";
					}
					hamiltonCircle.add(data.getGraph().getEdge(deadEnd, cur));
					lastDeadEnd = deadEnd;
					deadEnd = null;
				}
			}
			
			for (E e : outgoing) {
				V v = otherEndpoint(data, e, cur);
				if (!visited.contains(v)) {
					toVisit.push(cur);
					toVisit.push(v);
				} 
			}
		}
		
		// if there is no edge from the last reached vertex to the start vertex, the graph is invalid --> abort
		E last = data.getGraph().getEdge(lastDeadEnd, start);
		if (null == last) {
			highlightEdges(data, hamiltonCircle, Color.BLUE);
			return "invalid graph, can't close circle";
		}
		else {
			hamiltonCircle.add(data.getGraph().getEdge(lastDeadEnd, start));
			highlightEdges(data, hamiltonCircle, Color.BLUE);
		}
		
		// if it is a weighted graph, sum up all the edge weights, otherwise count +1 for each edge
		if (isWeighted) {
			for (Edge e : hamiltonCircle) {
				hamiltonCost += Integer.parseInt(e.getLabel());
			}
		} else {
			hamiltonCost = hamiltonCircle.size();
		}
		
		return Integer.toString(hamiltonCost);
		
	}

	/***
	 * works only with undirected graphs that are not acyclic
	 */
	@Override
	public boolean worksWith(Graph<V, E> graph) {
		return !graph.isDirected();
	}
}
