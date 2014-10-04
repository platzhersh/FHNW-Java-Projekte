package ch.fhnw.edu.efficientalgorithms.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.Point;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModelListener;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution;

/**
 * Implementation of the TSP model.
 * 
 * @author Martin Schaub
 */
public final class TSPModelImpl implements TSPModel {

	/**
	 * Stores all locations.
	 */
	private final Set<Location> locations = new HashSet<Location>();
	/**
	 * Stores a immutable version of the locations, so it does not need to be created every time.
	 */
	private final Set<Location> locationsImmutable = Collections.unmodifiableSet(locations);
	/**
	 * Stores all listeners
	 */
	private final Set<TSPModelListener> listeners = new HashSet<TSPModelListener>();

	/**
	 * Current solution.
	 */
	private TSPSolution solution;

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.aco.TSPModel#addLocation(ch.fhnw.edu.efficientalgorithms.
	 * geneticalgorithms.aco.Location)
	 */
	@Override
	public synchronized void addLocation(final Location location) {
		if (location == null) {
			throw new NullPointerException();
		}
		locations.add(location);

		for (TSPModelListener listener : listeners) {
			listener.locationAdded(location);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.aco.TSPModel#addTSPModelLister(ch.fhnw.edu.efficientalgorithms
	 * .geneticalgorithms.aco.TSPModelListener)
	 */
	@Override
	public synchronized void addTSPModelLister(final TSPModelListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.aco.TSPModel#getLocations()
	 */
	@Override
	public Set<Location> getLocations() {
		return locationsImmutable;
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.aco.TSPModel#removeLocation(ch.fhnw.edu.efficientalgorithms.
	 * geneticalgorithms.aco.Location)
	 */
	@Override
	public synchronized void removeLocation(final Location location) {
		if (location == null) {
			throw new NullPointerException();
		}
		locations.remove(location);

		for (TSPModelListener listener : listeners) {
			listener.locationRemoved(location);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.aco.TSPModel#removeTSPModelListener(ch.fhnw.edu.efficientalgorithms
	 * .geneticalgorithms.aco.TSPModelListener)
	 */
	@Override
	public synchronized void removeTSPModelListener(final TSPModelListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.aco.TSPModel#getLocationAt(ch.fhnw.edu.efficientalgorithms.
	 * geneticalgorithms.aco.Point, int)
	 */
	@Override
	public synchronized Location getLocationAt(final Point coordinate, final int tolerance) {
		if (coordinate == null) {
			throw new NullPointerException();
		}
		if (tolerance < 0) {
			throw new IllegalArgumentException();
		}

		int yMin = coordinate.getY() - tolerance;
		int yMax = coordinate.getY() + tolerance;
		int xMin = coordinate.getX() - tolerance;
		int xMax = coordinate.getX() + tolerance;

		for (Location loc : getLocations()) {
			Point p = loc.getCoordinate();
			if (p.getY() <= yMax && p.getY() >= yMin && p.getX() <= xMax && p.getX() >= xMin) {
				return loc;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPModel#getSolution()
	 */
	@Override
	public synchronized TSPSolution getSolution() {
		return solution;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPModel#setSolution(ch.fhnw.edu.efficientalgorithms.tsp.Solution)
	 */
	@Override
	public synchronized void setSolution(final TSPSolution solution) {
		if (solution == null) {
			throw new NullPointerException();
		}
		this.solution = solution;

		for (TSPModelListener listener : listeners) {
			listener.newSolution(solution);
		}
	}

}
