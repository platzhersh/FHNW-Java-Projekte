package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.impl;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Point;

/**
 * Simple and immutable point implementation.
 * 
 * @author Martin Schaub
 */
public final class PointImpl implements Point {

	/**
	 * X coordinate
	 */
	private final int x;
	/**
	 * Y coordinate
	 */
	private final int y;

	/**
	 * Constructor
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public PointImpl(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Point#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Point#getY()
	 */
	@Override
	public int getY() {
		return y;
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
		PointImpl other = (PointImpl) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return x + "/" + y;
	}
}
