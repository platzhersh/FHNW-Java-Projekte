package ch.fhnw.edu.efficientalgorithms.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;

/**
 * Abstract class with commonly used methods.
 * 
 * @author Martin Schaub
 */
public abstract class AbstractTSPAlgorithm implements TSPAlgorithm {

	/**
	 * Creates a fully connected graph using the pair class as edge.
	 * 
	 * @param model model that is used as underline data structure
	 * @return fully connected graph
	 */
	protected final List<Edge> createFullyConnectedGraph(final TSPModel model) {
		int number = model.getLocations().size();
		List<Edge> fullyConnected = new ArrayList<Edge>((number * number - number) / 2);
		for (Location loc1 : model.getLocations()) {
			for (Location loc2 : model.getLocations()) {
				if (!loc1.equals(loc2)) {
					fullyConnected.add(new Edge(loc1, loc2));
				}
			}
		}
		return fullyConnected;
	}

	/**
	 * Calculates the length of a tour
	 * 
	 * @param tour tour to calculate length of
	 * @return the length
	 */
	protected final double calcualteLength(final List<Location> tour) {
		double length = 0;
		Iterator<Location> it = tour.iterator();
		if (it.hasNext()) {
			Location old = it.next();
			while (it.hasNext()) {
				Location cur = it.next();
				length += Edge.getDistance(old, cur);
				old = cur;
			}
		}
		return length;
	}
}
