package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;


/***
 * Cycle Detection based on DFS
 * @author chregi
 *
 * @param <V>
 * @param <E>
 */
public class CycleDetection<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E>  {

	public CycleDetection() {
		super("Cycle Detection", true);
	}

	/***
	 * Runs DFS from every vertex which has not been visited before
	 * checks for back-edges every time it is visiting a vertex to make cycle detection
	 * stops at first detected back-edge
	 */
	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		// Stack to terminate the order of nodes to visit.
		Stack<V> toVisit = new Stack<V>();
		// Stores all already seen vertices
		Set<V> visited = new HashSet<V>();
		// Stores the edges that led to a node
		Map<V, E> usedEdge = new HashMap<V, E>();
		
		darkenOtherEdges(data, new ArrayList<E>());
		
		// start cycle detection from every starting vertex possible if it has not been visited before
		// this makes sure to even find cycles if there are multiple unconnected graphs
		for (V v : data.getGraph().getVertices()) {
			if (!visited.contains(v) && data.getGraph().getOutgoingEdges(v).size() != 0){
				data.getColorMapper().setVertexColor(v, Color.RED);
				toVisit.add(v);
				
				while (!toVisit.isEmpty()) {
					V cur = toVisit.pop();
					if (!visited.contains(cur)) {
						visited.add(cur);
						for (E e : data.getGraph().getOutgoingEdges(cur)) {
							V dst = otherEndpoint(data, e, cur);
							
							// check for back-edge
							if (visited.contains(dst) && !usedEdge.containsValue(e)) {

								// Stores the used edges
								List<E> edges = new LinkedList<E>(usedEdge.values());

								highlightEdges(data, edges, Color.BLACK);

								// highlight the back-edge that is creating the cycle
								data.getColorMapper().setEdgeColor(e, Color.RED);
								
								return "Cyclic";

							}

							if (!visited.contains(dst)) {
								usedEdge.put(dst, e);
								toVisit.push(dst);
							}
						}
					}
				}
			}
				
		}
		
		
		
		// Stores the used edges
		List<E> edges = new LinkedList<E>(usedEdge.values());

		highlightEdges(data, edges);
		darkenOtherEdges(data, edges);
				
		return "Acyclic";

	}

	
	/**
	 * This algorithm works with all graph implementations
	 */
	@Override
	public boolean worksWith(Graph<V, E> graph) {
		return true;
	}

}
