package ch.fhnw.edu.efficientalgorithms.algorithms.mstheuristic;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.algorithms.AbstractTSPAlgorithm;
import ch.fhnw.edu.efficientalgorithms.algorithms.DistanceComparator;
import ch.fhnw.edu.efficientalgorithms.algorithms.Edge;
import ch.fhnw.edu.efficientalgorithms.impl.TSPSolutionImpl;
import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;

/**
 * Two comparative ratio heuristics.
 * 
 * @author Martin Schaub
 */
public final class TwoComparativeAlgorithm extends AbstractTSPAlgorithm {

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm#allowsModification()
	 */
	@Override
	public boolean allowsModification() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm#start(ch.fhnw.edu.efficientalgorithms.tsp.TSPModel)
	 */
	@Override
	public void start(final TSPModel model) {
		if (model == null) {
			throw new NullPointerException();
		}

		List<Edge> spanningTree = doKruskal(model);
		assert spanningTree.size() + 1 == model.getLocations().size();

		List<Location> tour = doHeuristic(spanningTree, model);
		assert tour.size() == model.getLocations().size() + 1;

		model.setSolution(new TSPSolutionImpl(tour, calcualteLength(tour), 1));
	}

	/**
	 * Calculates a solution by the following heuristic:
	 * 
	 * - Double each edge in the spanning tree (add an edge in the reverse direction if the graph is directed)
	 * 
	 * - Follow the path and extract a tour. If a location is visited twice, it is not inserted.
	 * 
	 * @param spanningTree spanning tree to calculate the solution.
	 * @param model TSP data structure
	 * 
	 * @return the heuristic solution.
	 */
	private List<Location> doHeuristic(final List<Edge> spanningTree, final TSPModel model) {

		// Build up a better lookup structure for the edges and add reverse edges
		Location start = null;
		Map<Location, Set<Location>> outgoing = new HashMap<Location, Set<Location>>(spanningTree.size() * 2);
		for (Location loc : model.getLocations()) {
			start = loc;
			outgoing.put(loc, new HashSet<Location>());
		}
		for (Edge edge : spanningTree) {
			Location src = edge.getLoc1();
			Location dst = edge.getLoc2();
			outgoing.get(src).add(dst);
			outgoing.get(dst).add(src);
		}

		// Start following the path
		List<Location> path = new LinkedList<Location>();
		path.add(start);
		Set<Location> visited = new HashSet<Location>(model.getLocations().size() * 2);
		Set<Edge> usedEdges = new HashSet<Edge>(model.getLocations().size() * 2);

		Location cur = start;
		while (cur != null) {
			visited.add(cur);

			// Try to select an an unused edge
			Location selected = null;
			for (Location next : outgoing.get(cur)) {
				Edge e = new Edge(cur, next);
				if (!usedEdges.contains(e)) {
					if (selected == null || !visited.contains(e.getLoc2())) {
						selected = e.getLoc2();
					}
				}
			}

			// Select an edge if possible
			if (selected != null) {
				if (!visited.contains(selected)) {
					path.add(selected);
				}
				usedEdges.add(new Edge(cur, selected));
				cur = selected;
			}
			else {
				cur = null;
			}
		}

		path.add(start);

		return path;
	}

	/**
	 * Creates a spanning tree using the Kruskal method.
	 * 
	 * @param model model to calculate the Kruskal model of
	 * @return spanning tree
	 */
	private List<Edge> doKruskal(final TSPModel model) {
		List<Edge> fullyConnected = createFullyConnectedGraph(model);
		Collections.sort(fullyConnected, new DistanceComparator());

		// makeset
		Map<Location, UnionFindNode> mapping = new HashMap<Location, UnionFindNode>(fullyConnected.size() * 2);
		for (Location loc : model.getLocations()) {
			mapping.put(loc, new UnionFindNode(loc));
		}

		// Connect two spanning trees if possible
		List<Edge> spanningTree = new LinkedList<Edge>();
		for (Edge p : fullyConnected) {
			UnionFindNode n1 = mapping.get(p.getLoc1());
			UnionFindNode n2 = mapping.get(p.getLoc2());
			if (!canonicalElement(n1).equals(canonicalElement(n2))) {
				union(canonicalElement(n1), canonicalElement(n2));
				spanningTree.add(p);
			}
		}

		return spanningTree;
	}

	/**
	 * Merges together the sets (makes a union of two sets)
	 * 
	 * @param set1 first set
	 * @param set2 second set
	 */
	private void union(final UnionFindNode set1, final UnionFindNode set2) {
		if (set1 == null || set2 == null) {
			throw new NullPointerException();
		}
		if (set1.getParent() != null || set2.getParent() != null) {
			throw new IllegalArgumentException("Only canonical elements can be merged");
		}

		if (set1.getNumberOfLevels() < set2.getNumberOfLevels()) {
			set1.setParent(set2);
		}
		else if (set1.getNumberOfLevels() == set2.getNumberOfLevels()) {
			set1.setParent(set2);
			set2.setNumberOfLevels(set2.getNumberOfLevels() + 1);
		}
		else {
			set2.setParent(set1);
		}
	}

	/**
	 * Returns the canonical element, which represents the set.
	 * 
	 * @param node whose canonical element needs to be found
	 * @return the canonical element
	 */
	private UnionFindNode canonicalElement(final UnionFindNode node) {
		if (node == null) {
			throw new NullPointerException();
		}

		UnionFindNode cur = node;
		while (cur.getParent() != null) {
			cur = cur.getParent();
		}

		return cur;
	}
}
