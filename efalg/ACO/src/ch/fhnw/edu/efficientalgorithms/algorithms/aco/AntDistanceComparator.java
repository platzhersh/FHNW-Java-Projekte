package ch.fhnw.edu.efficientalgorithms.algorithms.aco;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compares ANT based on their evaluated path length.
 * 
 * @author Martin Schaub
 */
public final class AntDistanceComparator implements Comparator<Ant>, Serializable {

	/**
	 * Serial Version UID (must be serializable since it is comparable)
	 */
	private static final long serialVersionUID = -1581743538360466787L;

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final Ant o1, final Ant o2) {
		int val = Double.compare(o1.getPathLength(), o2.getPathLength());
		if (val != 0) {
			return val;
		}
		if (o1.equals(o2)) {
			return 0;
		}
		return Integer.valueOf(System.identityHashCode(o1)).compareTo(System.identityHashCode(o2));
	}
}
