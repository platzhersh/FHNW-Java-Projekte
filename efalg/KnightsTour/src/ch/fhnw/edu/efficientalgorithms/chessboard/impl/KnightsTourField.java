package ch.fhnw.edu.efficientalgorithms.chessboard.impl;

import ch.fhnw.edu.efficientalgorithms.chessboard.Field;

/**
 * Simple and immutable implementation.
 * 
 * @author Martin Schaub
 */
public final class KnightsTourField implements Field, Comparable<KnightsTourField> {

	/**
	 * Stores the represented value.
	 */
	private final int value;

	/**
	 * Stores the visited status.
	 */
	private final boolean visited;

	/**
	 * Constructor For a not visited field.
	 */
	public KnightsTourField() {
		this.value = -1;
		this.visited = false;
	}

	/**
	 * Constructor
	 * 
	 * @param value value to assign. Field will also be marked as visited.
	 */
	public KnightsTourField(final int value) {
		this.value = value;
		this.visited = true;
	}

	/**
	 * Returns the currently saved value. This is only valid, if the field was visited.
	 * 
	 * @return the saved value
	 */
	public int getValue() {
		if (!visited) {
			throw new IllegalStateException();
		}
		return value;
	}

	/**
	 * Wheather the field was visited or not.
	 * 
	 * @return true, if the field was visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.Field#getLabel()
	 */
	@Override
	public String getLabel() {
		return visited ? Integer.toString(value) : "";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getLabel();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		result = prime * result + (visited ? 1231 : 1237);
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
		KnightsTourField other = (KnightsTourField) obj;
		if (value != other.value) {
			return false;
		}
		if (visited != other.visited) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final KnightsTourField o) {
		return new Integer(value).compareTo(o.value);
	}

}
