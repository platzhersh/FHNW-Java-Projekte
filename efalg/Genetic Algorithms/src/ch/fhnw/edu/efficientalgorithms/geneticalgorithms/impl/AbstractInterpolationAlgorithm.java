package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.impl;

import java.util.HashSet;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Function;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithm;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithmListener;

/**
 * Abstract base class for handling common sense tasks of the algorithms.
 * 
 * @author Martin Schaub
 */
public abstract class AbstractInterpolationAlgorithm implements InterpolationAlgorithm {

	/**
	 * Stores all registered listeners.
	 */
	private final Set<InterpolationAlgorithmListener> listeners = new HashSet<InterpolationAlgorithmListener>();

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Algorithm#addAlgorithmListener(ch.fhnw.edu.efficientalgorithms
	 * .geneticalgorithms.AlgorithmListener)
	 */
	@Override
	public synchronized final void addAlgorithmListener(final InterpolationAlgorithmListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Algorithm#removeAlgorithmListener(ch.fhnw.edu.efficientalgorithms
	 * .geneticalgorithms.AlgorithmListener)
	 */
	@Override
	public synchronized final void removeAlgorithmListener(final InterpolationAlgorithmListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.remove(listener);
	}

	/**
	 * Notifies the listeners that the algorithm has been started.
	 */
	protected synchronized final void fireAlgorithmStarted() {
		for (InterpolationAlgorithmListener listener : listeners) {
			listener.algorithmStarted();
		}
	}

	/**
	 * A new intermediate result was produced and the observers need to be notified.
	 * 
	 * @param function evaluated function to report.
	 */
	protected synchronized final void fireNewResults(final Function function) {
		if (function == null) {
			throw new NullPointerException();
		}

		try {
			// Wait to visualize the result!
			Thread.sleep(10);
		}
		catch (InterruptedException e) {
			throw new InternalError();
		}

		for (InterpolationAlgorithmListener listener : listeners) {
			listener.newResult(function);
		}
	}

	/**
	 * Notifies observers that the algorithm calculation was finished.
	 * 
	 * @param function evaluated function to report
	 */
	protected synchronized final void fireAlgorithmEnded(final Function function) {
		if (function == null) {
			throw new NullPointerException();
		}
		for (InterpolationAlgorithmListener listener : listeners) {
			listener.newResult(function);
		}
	}
}
