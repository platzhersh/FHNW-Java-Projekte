package ch.fhnw.edu.efficientalgorithms.lineintersection.imp;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Line;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Point;

/**
 * Immutable line implementation.
 * 
 * @author Martin Schaub
 */
public final class ImmutableLine implements Line {
	/**
	 * Line's end point.
	 */
	private final Point endPoint;
	/**
	 * Line's start point.
	 */
	private final Point startPoint;

	/**
	 * 
	 * Constructor
	 * 
	 * @param startPoint lines startpoint
	 * @param endPoint lines endpoint
	 */
	public ImmutableLine(final Point startPoint, final Point endPoint) {
		if (endPoint == null || startPoint == null) {
			throw new NullPointerException();
		}

		// Order the points (the startpoint must be before the endpoint)
		if (startPoint.getX() < endPoint.getX()
				|| (startPoint.getX() == endPoint.getX() && startPoint.getY() < endPoint.getX())) {
			this.endPoint = endPoint;
			this.startPoint = startPoint;
		}
		else {
			this.endPoint = startPoint;
			this.startPoint = endPoint;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Line#getEndPoint()
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Line#getStartPoint()
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return startPoint + " " + endPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
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
		ImmutableLine other = (ImmutableLine) obj;
		if (endPoint == null) {
			if (other.endPoint != null) {
				return false;
			}
		}
		else if (!endPoint.equals(other.endPoint)) {
			return false;
		}
		if (startPoint == null) {
			if (other.startPoint != null) {
				return false;
			}
		}
		else if (!startPoint.equals(other.startPoint)) {
			return false;
		}
		return true;
	}

}
