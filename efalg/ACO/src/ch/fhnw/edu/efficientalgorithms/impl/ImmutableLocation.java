package ch.fhnw.edu.efficientalgorithms.impl;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.Point;

/**
 * Immutable implementation of a location.
 * 
 * @author Martin Schaub
 */
public final class ImmutableLocation implements Location {

	/**
	 * Label of the location
	 */
	private final String label;
	/**
	 * Coordinate of the location
	 */
	private final Point coordinate;

	/**
	 * Constructor
	 * 
	 * @param label location's label
	 * @param coordinate location's coordinate
	 */
	public ImmutableLocation(final String label, final Point coordinate) {
		if (label == null || coordinate == null) {
			throw new NullPointerException();
		}
		if (label.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.label = label;
		this.coordinate = coordinate;
	}

	/**
	 * Constructor
	 * 
	 * @param coordinate location's coordinate
	 */
	public ImmutableLocation(final Point coordinate) {
		if (coordinate == null) {
			throw new NullPointerException();
		}
		this.coordinate = coordinate;
		this.label = " ";
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.aco.Location#getCoordinate()
	 */
	@Override
	public Point getCoordinate() {
		return coordinate;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.aco.Location#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (!label.isEmpty() && !label.equals(" ")) {
			return label;
		}
		return coordinate.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		ImmutableLocation other = (ImmutableLocation) obj;
		if (coordinate == null) {
			if (other.coordinate != null) {
				return false;
			}
		}
		else if (!coordinate.equals(other.coordinate)) {
			return false;
		}
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		}
		else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

}
