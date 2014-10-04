package ch.fhnw.edu.efficientalgorithms.lineintersection.imp;

import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Algorithm;
import ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Point;

/**
 * Defines commonly used functionality for algorithms.
 * 
 * @author Martin Schaub
 */
public abstract class AbstractAlgorithm implements Algorithm {

	/**
	 * Plane for calculation.
	 */
	private final Plane plane;
	/**
	 * Registered listeners.
	 */
	private final List<AlgorithmListener> listeners = new LinkedList<AlgorithmListener>();

	/**
	 * Constructor
	 * 
	 * @param plane needed plane for calculation
	 */
	public AbstractAlgorithm(final Plane plane) {
		if (plane == null) {
			throw new NullPointerException();
		}
		this.plane = plane;
	}

	/**
	 * Getter method for the plane property.
	 * 
	 * @return the plane property's value
	 */
	protected final Plane getPlane() {
		return plane;
	}

	/**
	 * Getter method for the listeners property.
	 * 
	 * @return the listeners property's value
	 */
	protected final List<AlgorithmListener> getListeners() {
		return listeners;
	}

	/**
	 * Notifies all listeners that the algorithm has started.
	 */
	protected final void notifyListenersProcessStarted() {
		for (final AlgorithmListener listener : listeners) {
			listener.processStarted();
		}
	}

	/**
	 * The algorithm advanced to the next point.
	 * 
	 * @param point current position
	 */
	protected final void notifyListenersCurrentXCoordinate(final Point point) {
		for (final AlgorithmListener listener : listeners) {
			listener.currentCoordinate(point);
		}
	}

	/**
	 * Notifies listeners about some results.
	 * 
	 * @param obj results
	 */
	protected final void notifyListenersReportObject(final Object obj) {
		for (final AlgorithmListener listener : listeners) {
			listener.reportObject(obj);
		}
	}

	/**
	 * The processing has been finished and this event is reported to the listeners.
	 */
	protected final void notifyListenersProcessEnded() {
		for (final AlgorithmListener listener : listeners) {
			listener.processEnded();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.Algorithm#addProgressListener(ch.fhnw.edu.efficientalgorithms.
	 * lineintersection.AlgorithmListener)
	 */
	@Override
	public final void addProgressListener(final AlgorithmListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.Algorithm#removeProgressListener(ch.fhnw.edu.efficientalgorithms
	 * .lineintersection.AlgorithmListener)
	 */
	@Override
	public final void removeProgressListener(final AlgorithmListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.remove(listener);
	}
}
