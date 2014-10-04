package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.gui;

import java.util.List;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Function;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithmListener;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Plot;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Point;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatusListener;

/**
 * Manages a plot. That means it updates the plot according to changes of the algorithm or data structure.
 *
 * @author Martin Schaub
 */
public final class PlotManager implements ProgramStatusListener, InterpolationAlgorithmListener {

	/**
	 * Panel to manage
	 */
	private final Plot plot;

	/**
	 * The last added function.
	 */
	private Function lastFunction;

	/**
	 * Constructor
	 *
	 * @param plot for managing
	 */
	public PlotManager(final Plot plot) {
		if (plot == null) {
			throw new NullPointerException();
		}
		this.plot = plot;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatusListener#pointAdded(ch.fhnw.edu.efficientalgorithms
	 * .geneticalgorithms.Point)
	 */
	@Override
	public void pointAdded(final Point point) {
		if (point == null) {
			throw new NullPointerException();
		}
		plot.addPoint(point);
		removeOldFunction();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatusListener#pointsCleared(java.util.List)
	 */
	@Override
	public void pointsCleared(final List<Point> deletedPoints) {
		if (deletedPoints == null) {
			throw new NullPointerException();
		}
		for (Point point : deletedPoints) {
			plot.removePoint(point);
		}
		removeOldFunction();
	}

	/**
	 * Private helper method to remove an previously reported function from the plot, it there is one.
	 */
	private synchronized void removeOldFunction() {
		if (lastFunction != null) {
			plot.removeFunction(lastFunction);
			lastFunction = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.AlgorithmListener#algorithmStarted()
	 */
	@Override
	public void algorithmStarted() {
		removeOldFunction();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithmListener#algorithmFinished(ch.fhnw.edu.
	 * efficientalgorithms.geneticalgorithms.Function)
	 */
	@Override
	public synchronized void algorithmFinished(final Function endResult) {
		if (endResult == null) {
			throw new NullPointerException();
		}
		removeOldFunction();
		plot.addFunction(endResult);
		lastFunction = endResult;
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithmListener#newResult(ch.fhnw.edu.
	 * efficientalgorithms.geneticalgorithms.Function)
	 */
	@Override
	public synchronized void newResult(final Function intermediateResult) {
		if (intermediateResult == null) {
			throw new NullPointerException();
		}
		removeOldFunction();
		plot.addFunction(intermediateResult);
		lastFunction = intermediateResult;
	}

}
