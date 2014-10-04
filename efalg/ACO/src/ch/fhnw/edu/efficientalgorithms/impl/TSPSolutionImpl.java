package ch.fhnw.edu.efficientalgorithms.impl;

import java.util.List;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution;

/**
 * Implementation of a simple solution that takes all parameters as constructor input.
 * 
 * @author Martin Schaub
 */
public final class TSPSolutionImpl implements TSPSolution {

	/**
	 * The tour's solution.
	 */
	private final List<Location> tour;
	/**
	 * Length of this tour
	 */
	private final double length;
	/**
	 * The how many-th solution this is.
	 */
	private final int solutionNumber;

	/**
	 * Constructor
	 * 
	 * @param tour solution (including the start node in the end!)
	 * @param length length of the solution
	 * @param solutionNumber the current solution number
	 */
	public TSPSolutionImpl(final List<Location> tour, final double length, final int solutionNumber) {
		if (tour == null) {
			throw new NullPointerException();
		}
		if (length < 0) {
			throw new IllegalArgumentException();
		}
		if (solutionNumber < 0) {
			throw new IllegalArgumentException();
		}
		this.tour = tour;
		this.length = length;
		this.solutionNumber = solutionNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution#getTour()
	 */
	@Override
	public List<Location> getTour() {
		return tour;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution#getLength()
	 */
	@Override
	public double getLength() {
		return length;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution#currentSolutionNumber()
	 */
	@Override
	public int getCurrentSolutionNumber() {
		return solutionNumber;
	}
}
