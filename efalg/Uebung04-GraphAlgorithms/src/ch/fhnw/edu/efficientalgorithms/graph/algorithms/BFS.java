package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

/**
 * Implementation on the bredth-first-search (BFS) algorithm.
 * 
 * @author chregi
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public final class BFS<V extends Vertex, E extends Edge> extends AbstractAlgorithm<V, E> {

	/**
	 * Constructor
	 */
	public BFS() {
		super("Bredth First Search", true);
	}

	/**
	 * This algorithm works with all graph implementations, however a start vertex needs to be selected.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean worksWith(final Graph<V, E> graph) {
		return true;
	}

	/**
	 * Non-recursive implementation of the BFS algorithm.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String execute(final GraphAlgorithmData<V, E> data) {

		// Queue to terminate the order of nodes to visit.
		Queue<V> toVisit = new LinkedList<V>();
		// Stores all already seen vertices
		Set<V> visited = new HashSet<V>();
		// Stores the edges that led to a node
		Map<V, E> usedEdge = new HashMap<V, E>();

		V start = getFirstVertex(data);
		if (start == null) {
			return "Empty graph,\nnothing to do";
		}
		data.getColorMapper().setVertexColor(start, Color.RED);
		toVisit.add(start);

		while (!toVisit.isEmpty()) {
			V cur = toVisit.poll();
			if (!visited.contains(cur)) {
				visited.add(cur);
				for (E e : data.getGraph().getOutgoingEdges(cur)) {
					V dst = otherEndpoint(data, e, cur);
					if (!visited.contains(dst) && !toVisit.contains(dst)) {
							usedEdge.put(dst, e);
							toVisit.add(dst);
					}
				}
			}
		}

		// Stores the used edges
		List<E> edges = new LinkedList<E>(usedEdge.values());

		highlightEdges(data, edges);
		darkenOtherEdges(data, edges);

		return "Finished";
	}
}
