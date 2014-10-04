package ch.fhnw.edu.efficientalgorithms.algorithms.mstheuristic;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;

/**
 * For the Kruskal algorithm, this class represents a node in the union-find data structure.
 * 
 * @author Martin Schaub
 */
public final class UnionFindNode {
	/**
	 * Location that is represented through this node.
	 */
	private final Location location;

	/**
	 * Number of levels under this node.
	 */
	private int numberOfLevels;
	/**
	 * Parent node.
	 */
	private UnionFindNode parent;

	/**
	 * Constructor
	 * 
	 * @param location loation that is represented by this node.
	 */
	public UnionFindNode(final Location location) {
		if (location == null) {
			throw new NullPointerException();
		}
		this.location = location;
		this.parent = null;
		this.numberOfLevels = 0;
	}

	/**
	 * Getter method for the numberOfLevels property.
	 * 
	 * @return the numberOfLevels property's value
	 */
	public int getNumberOfLevels() {
		return numberOfLevels;
	}

	/**
	 * Setter method for the numberOfLevels property.
	 * 
	 * @param numberOfLevels the new numberOfLevels to set
	 */
	public void setNumberOfLevels(final int numberOfLevels) {
		if (numberOfLevels < 0) {
			throw new IllegalArgumentException();
		}
		this.numberOfLevels = numberOfLevels;
	}

	/**
	 * Getter method for the parent property.
	 * 
	 * @return the parent property's value
	 */
	public UnionFindNode getParent() {
		return parent;
	}

	/**
	 * Setter method for the parent property.
	 * 
	 * @param parent the new parent to set
	 */
	public void setParent(final UnionFindNode parent) {
		this.parent = parent;
	}

	/**
	 * Getter method for the location property.
	 * 
	 * @return the location property's value
	 */
	public Location getLocation() {
		return location;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return location.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		UnionFindNode other = (UnionFindNode) obj;
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		}
		else if (!location.equals(other.location)) {
			return false;
		}
		return true;
	}

}
