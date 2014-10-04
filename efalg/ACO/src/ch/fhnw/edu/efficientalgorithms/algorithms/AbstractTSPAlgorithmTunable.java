package ch.fhnw.edu.efficientalgorithms.algorithms;

import java.util.HashSet;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunableListener;

/**
 * Creates a panel of tunable parameters.
 * 
 * @author Martin Schaub
 * 
 * @param <T> concrete algorithm so the method getAlgorithm returns the correct type.
 */
public abstract class AbstractTSPAlgorithmTunable<T extends TSPAlgorithm> implements TSPAlgorithmTunable {

	/**
	 * Saves the algorithm.
	 */
	private final T algorithm;
	/**
	 * Stores all listeners
	 */
	private final Set<TSPAlgorithmTunableListener> listeners = new HashSet<TSPAlgorithmTunableListener>();

	/**
	 * Constructor
	 * 
	 * @param algorithm algorithm to execute
	 */
	public AbstractTSPAlgorithmTunable(final T algorithm) {
		if (algorithm == null) {
			throw new NullPointerException();
		}
		this.algorithm = algorithm;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmGraphicalTunable#getAlgorithm()
	 */
	@Override
	public final T getAlgorithm() {
		return algorithm;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable#addTSPAlgorithmTunableListener(ch.fhnw.edu.efficientalgorithms
	 * .tsp.TSPAlgorithmTunableListener)
	 */
	@Override
	public synchronized final void addTSPAlgorithmTunableListener(final TSPAlgorithmTunableListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable#removeTSPAlgorithmTunableListener(ch.fhnw.edu.
	 * efficientalgorithms.tsp.TSPAlgorithmTunableListener)
	 */
	@Override
	public synchronized final void removeTSPAlgorithmTunableListener(final TSPAlgorithmTunableListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.remove(listener);
	}

	/**
	 * Notifies all observers that the attribute has been changed. This method must be called after setting the attribute!
	 * 
	 * @param attribute changed attribute
	 */
	protected synchronized final void notifyObserversChange(final String attribute) {
		for (TSPAlgorithmTunableListener listener : listeners) {
			listener.tunableChanged(attribute);
		}
	}
}
