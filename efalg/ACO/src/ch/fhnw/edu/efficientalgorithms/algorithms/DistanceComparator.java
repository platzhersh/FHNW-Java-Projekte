package ch.fhnw.edu.efficientalgorithms.algorithms;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compares locations based on distance.
 */
public final class DistanceComparator implements Comparator<Edge>, Serializable {

	/**
	 * Serial Version UID (Serializable since its a Comparator)
	 */
	private static final long serialVersionUID = -2243536074534796662L;

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final Edge o1, final Edge o2) {
		return Double.compare(o1.getDistance(), o2.getDistance());
	}

}
