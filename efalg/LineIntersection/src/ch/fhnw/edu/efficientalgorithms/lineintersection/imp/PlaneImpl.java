package ch.fhnw.edu.efficientalgorithms.lineintersection.imp;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Line;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.PlaneListener;

/**
 * Implementation of a Euclidean plane. All methods are thread safe, because it might be accessed from multiple threads
 * (Main, AWT, ..)
 * 
 * @author Martin Schaub
 */
public final class PlaneImpl implements Plane {

	/**
	 * Stores the lines.
	 */
	private final Set<Line> lines = new HashSet<Line>();
	/**
	 * Stores all registered listeners
	 */
	private final List<PlaneListener> listeners = new LinkedList<PlaneListener>();

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.Plane#addLine(ch.fhnw.edu.efficientalgorithms.lineintersection
	 * .Line)
	 */
	public synchronized void addLine(final Line line) {
		if (line == null) {
			throw new NullPointerException();
		}

		if (!lines.contains(line)) {
			lines.add(line);
		}

		for (PlaneListener listener : listeners) {
			listener.added(line);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Plane#clear()
	 */
	public synchronized void clear() {
		for (final Line line : new HashSet<Line>(lines)) {
			removeLine(line);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Plane#getLines()
	 */
	public Set<Line> getLines() {
		return Collections.unmodifiableSet(lines);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.Plane#removeLine(ch.fhnw.edu.efficientalgorithms.lineintersection
	 * .Line)
	 */
	@Override
	public synchronized void removeLine(final Line line) {
		if (line == null) {
			throw new NullPointerException();
		}

		lines.remove(line);

		for (PlaneListener listener : listeners) {
			listener.removed(line);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.lineintersection.Plane#addPlaneListener(ch.fhnw.edu.efficientalgorithms.
	 * lineintersection.PlaneListener)
	 */
	@Override
	public synchronized void addPlaneListener(final PlaneListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.lineintersection.Plane#removePlaneListener(ch.fhnw.edu.efficientalgorithms.
	 * lineintersection.PlaneListener)
	 */
	@Override
	public synchronized void removePlaneListener(final PlaneListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.remove(listener);
	}

}
