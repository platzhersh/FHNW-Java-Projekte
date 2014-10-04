package ch.fhnw.edu.efficientalgorithms.lineintersection.imp;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Point;

/**
 * Draws found intersections from intersection finding algorithms.
 *
 * @author Martin Schaub
 */
public final class IntersectionDrawer implements AlgorithmListener {

	/**
	 * Defines how long the thread waits to continue.
	 */
	private static final int SLEEP_PERIOD = 100;
	/**
	 * How big is the drawn circle around points.
	 */
	private static final int CIRCLE_DIAMETER = 20;

	/**
	 * To draw the reported intersections
	 */
	private final Drawer drawer;
	/**
	 * Last drawn scan line.
	 */
	private Shape lastScanLine;

	/**
	 * Constructor
	 *
	 * @param drawer where the reported intersections are written.
	 */
	public IntersectionDrawer(final Drawer drawer) {
		if (drawer == null) {
			throw new NullPointerException();
		}
		this.drawer = drawer;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#currentCoordinate(ch.fhnw.edu.efficientalgorithms
	 * .lineintersection.Point)
	 */
	@Override
	public void currentCoordinate(final Point point) {
		if (lastScanLine != null) {
			drawer.removeDrawable(lastScanLine);
		}
		lastScanLine = new Line2D.Double(new Point2D.Double(point.getX(), point.getY()),
				new Point2D.Double(point.getX(), 0));
		drawer.addDrawable(lastScanLine, Color.BLUE);

		// So the user can actually see something from the algorithm
		try {
			Thread.sleep(SLEEP_PERIOD);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#processEnded()
	 */
	@Override
	public void processEnded() {
		if (lastScanLine != null) {
			drawer.removeDrawable(lastScanLine);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#processStarted()
	 */
	@Override
	public void processStarted() {
		drawer.clearAllDrawables();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#reportObject(java.lang.Object)
	 */
	@Override
	public void reportObject(final Object object) {
		if (object instanceof Point) {
			Point point = (Point) object;
			drawer.addDrawable(new Ellipse2D.Double(point.getX() - CIRCLE_DIAMETER / 2, point.getY() - CIRCLE_DIAMETER / 2,
					CIRCLE_DIAMETER, CIRCLE_DIAMETER), Color.RED);

			// So the user can actually see something from the algorithm
			try {
				Thread.sleep(SLEEP_PERIOD);
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
