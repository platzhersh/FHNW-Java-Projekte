package ch.fhnw.edu.efficientalgorithms.impl;

import ch.fhnw.edu.efficientalgorithms.tsp.Point;

/**
 * Immutable point implementation.
 * 
 * @author Martin Schaub
 */
public final class ImmutablePoint implements Point {

	/**
	 * X coordinate.
	 */
	private final int x;
	/**
	 * Y coordinate.
	 */
	private final int y;

	/**
	 * 
	 * Constructor
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public ImmutablePoint(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Point#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Point#getY()
	 */
	@Override
	public int getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + x + "/" + y + ")";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		ImmutablePoint other = (ImmutablePoint) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	/**
	 * First ordered after x and then after y
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(final Point o) {
		int xComp = Integer.valueOf(x).compareTo(o.getX());
		if (xComp != 0) {
			return xComp;
		}
		return Integer.valueOf(y).compareTo(o.getY());
	}

}
