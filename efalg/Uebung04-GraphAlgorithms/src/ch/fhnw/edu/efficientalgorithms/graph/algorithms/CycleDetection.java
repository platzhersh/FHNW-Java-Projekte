package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;
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
 *  Cycle Detection based on DFS
 * @author chregi
 *
 * @param <V>
 * @param <E>
 */
public class CycleDetection<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E>  {

	public CycleDetection() {
		super("Cycle Detection", true);
	}

	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		// Stack to terminate the order of nodes to visit.
		Stack<V> toVisit = new Stack<V>();
		// Stores all already seen vertices
		Set<V> visited = new HashSet<V>();
		// Stores the edges that led to a node
		Map<V, E> usedEdge = new HashMap<V, E>();

		// need to check if start Vertex has outgoing edges
		V start = getFirstVertex(data);
		if (start == null) {
			return "Empty graph,\nnothing to do";
		} else if (data.getGraph().getOutgoingEdges(start).size() == 0) {
			for (V v : data.getGraph().getVertices()) {
				if (data.getGraph().getOutgoingEdges(v).size() != 0) {
					start = v; 
					break;
				}
			}
		}
		data.getColorMapper().setVertexColor(start, Color.RED);
		toVisit.add(start);
		V directAncestor = null;
		
		while (!toVisit.isEmpty()) {
			V cur = toVisit.pop();
			if (!visited.contains(cur)) {
				visited.add(cur);
				for (E e : data.getGraph().getOutgoingEdges(cur)) {
					V dst = otherEndpoint(data, e, cur);
					
					if (visited.contains(dst) && !dst.equals(directAncestor) && !usedEdge.containsValue(e)) {
						// Stores the used edges
						List<E> edges = new LinkedList<E>(usedEdge.values());

						highlightEdges(data, edges);
						darkenOtherEdges(data, edges);
						
						return "Cyclic";

					}

					if (!visited.contains(dst)) {
						usedEdge.put(dst, e);
						toVisit.push(dst);
						directAncestor = cur;
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

	@Override
	public boolean worksWith(Graph<V, E> graph) {
		// TODO Auto-generated method stub
		return true;
	}

}
