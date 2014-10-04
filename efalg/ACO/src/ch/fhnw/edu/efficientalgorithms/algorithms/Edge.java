package ch.fhnw.edu.efficientalgorithms.algorithms;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.Point;

/**
 * A pair of locations to represent a edge.
 * 
 * @author Martin Schaub
 */
public final class Edge {

	/**
	 * First location.
	 */
	private final Location loc1;
	/**
	 * Second location.
	 */
	private final Location loc2;

	/**
	 * Constructor
	 * 
	 * @param loc1 first location.
	 * @param loc2 second location.
	 */
	public Edge(final Location loc1, final Location loc2) {
		if (loc1 == null || loc2 == null) {
			throw new NullPointerException();
		}
		this.loc1 = loc1;
		this.loc2 = loc2;
	}

	/**
	 * Getter method for the loc1 property.
	 * 
	 * @return the loc1 property's value
	 */
	public Location getLoc1() {
		return loc1;
	}

	/**
	 * Getter method for the loc2 property.
	 * 
	 * @return the loc2 property's value
	 */
	public Location getLoc2() {
		return loc2;
	}

	/**
	 * Calculates the distance (Euclidean) between two locations.
	 * 
	 * @return distance
	 */
	public double getDistance() {
		return getDistance(loc1, loc2);
	}

	/**
	 * Calculates the distance (Euclidean) between two locations.
	 * 
	 * @param l1 location1
	 * @param l2 location2
	 * 
	 * @return distance
	 */
	public static double getDistance(final Location l1, final Location l2) {
		Point p1 = l1.getCoordinate();
		Point p2 = l2.getCoordinate();

		double xdiff = p1.getX() - p2.getX();
		double ydiff = p1.getY() - p2.getY();

		return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return loc1 + "|" + loc2;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loc1 == null) ? 0 : loc1.hashCode());
		result = prime * result + ((loc2 == null) ? 0 : loc2.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Edge other = (Edge) obj;
		if (loc1 == null) {
			if (other.loc1 != null) {
				return false;
			}
		}
		else if (!loc1.equals(other.loc1)) {
			return false;
		}
		if (loc2 == null) {
			if (other.loc2 != null) {
				return false;
			}
		}
		else if (!loc2.equals(other.loc2)) {
			return false;
		}
		return true;
	}
}
