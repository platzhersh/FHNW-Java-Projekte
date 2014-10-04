package ch.fhnw.edu.efficientalgorithms.algorithms.aco;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.algorithms.Edge;
import ch.fhnw.edu.efficientalgorithms.tsp.Location;

/**
 * Represents the accumulated data that a ant produces.
 * 
 * @author Martin Schaub
 */
public final class Ant {

	/**
	 * Startnode
	 */
	private final Location start;
	/**
	 * A list of
	 */
	private final List<Location> path = new LinkedList<Location>();
	/**
	 * List of nodes to visit.
	 */
	private final Set<Location> remaining = new HashSet<Location>();

	/**
	 * Current location.
	 */
	private Location currentLocation;
	/**
	 * Length of this tour
	 */
	private double length;

	/**
	 * Constructor
	 * 
	 * @param start start location
	 * @param allLocations stores all locations
	 */
	public Ant(final Location start, final Collection<Location> allLocations) {
		if (start == null || allLocations == null) {
			throw new NullPointerException();
		}
		this.start = start;
		currentLocation = start;
		path.add(start);
		remaining.addAll(allLocations);
		remaining.remove(start);
	}

	/**
	 * Has this ant finished its tour.
	 * 
	 * @return true if it has finished.
	 */
	public boolean isFinished() {
		return remaining.isEmpty() && start.equals(currentLocation);
	}

	/**
	 * Returns the path of the ant.
	 * 
	 * @return the path
	 */
	public List<Location> getPath() {
		return path;
	}

	/**
	 * Returns the start location.
	 * 
	 * @return start location
	 */
	public Location getStart() {
		return start;
	}

	/**
	 * Adds a new location to the ants path. If the ant has already finished its run, then no more locations can be added.
	 * 
	 * @param newLoc location to add.
	 */
	public void addLocation(final Location newLoc) {
		if (newLoc == null) {
			throw new NullPointerException();
		}
		assert hasNotVisited(newLoc) || (!isFinished() && newLoc.equals(start));

		length += Edge.getDistance(currentLocation, newLoc);
		path.add(newLoc);
		remaining.remove(newLoc);
		currentLocation = newLoc;
	}

	/**
	 * Looks if an ant has already visited
	 * 
	 * @param loc location to check
	 * @return true if the ant hasn't visited this location yet.
	 */
	public boolean hasNotVisited(final Location loc) {
		if (loc == null) {
			throw new NullPointerException();
		}
		return remaining.contains(loc);
	}

	/**
	 * Returns all remaining locations.
	 * 
	 * @return all remaining locations
	 */
	public Set<Location> getRemainingLocations() {
		return Collections.unmodifiableSet(remaining);
	}

	/**
	 * Gets the current location.
	 * 
	 * @return the current location.
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Gets the length of the path.
	 * 
	 * @return total length of this path
	 */
	public double getPathLength() {
		return length;
	}
}
