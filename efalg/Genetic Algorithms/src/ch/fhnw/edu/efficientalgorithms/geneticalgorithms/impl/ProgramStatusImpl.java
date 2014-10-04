package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Point;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatusListener;

/**
 * Implementation of the ProgramStatus interface. This is programmed with thread safety in mind, because the algorithm
 * might run in another thread.
 * 
 * @author Martin Schaub
 */
public final class ProgramStatusImpl implements ProgramStatus {

	/**
	 * Stores all listeners.
	 */
	private final Set<ProgramStatusListener> listeners = new HashSet<ProgramStatusListener>();

	/**
	 * Saved points
	 */
	private final List<Point> points = new LinkedList<Point>();
	/**
	 * Current application status
	 */
	private Status status = Status.Recording;

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus#addPoint(ch.fhnw.edu.efficientalgorithms.
	 * geneticalgorithms.Point)
	 */
	@Override
	public synchronized void addPoint(final Point point) {
		if (point == null) {
			throw new NullPointerException();
		}
		if (!status.equals(Status.Recording)) {
			throw new IllegalStateException();
		}
		points.add(point);

		for (ProgramStatusListener listener : listeners) {
			listener.pointAdded(point);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus#clearPoints()
	 */
	@Override
	public synchronized void clearPoints() {
		if (!status.equals(Status.Recording)) {
			throw new IllegalStateException();
		}

		List<Point> oldPoints = new ArrayList<Point>(points);
		points.clear();

		for (ProgramStatusListener listener : listeners) {
			listener.pointsCleared(oldPoints);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus#addProgramStatusListener(ch.fhnw.edu.
	 * efficientalgorithms.geneticalgorithms.ProgramStatusListener)
	 */
	@Override
	public void addProgramStatusListener(final ProgramStatusListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus#removeProgramStatusListener(ch.fhnw.edu.
	 * efficientalgorithms.geneticalgorithms.ProgramStatusListener)
	 */
	@Override
	public void removeProgramStatusListener(final ProgramStatusListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus#getStatus()
	 */
	@Override
	public Status getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus#setStatus(ch.fhnw.edu.efficientalgorithms.
	 * geneticalgorithms.ProgramStatus.Status)
	 */
	@Override
	public void setStatus(final Status status) {
		if (status == null) {
			throw new NullPointerException();
		}
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus#getPoints()
	 */
	@Override
	public synchronized List<Point> getPoints() {
		return Collections.unmodifiableList(points);
	}
}
