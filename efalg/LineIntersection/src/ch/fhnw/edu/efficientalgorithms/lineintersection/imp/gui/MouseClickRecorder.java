package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui;

import java.awt.Shape;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

import ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Line;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Point;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.ImmutableLine;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.ImmutablePoint;
import ch.fhnw.edu.efficientalgorithms.lineintersection.imp.PlaneAlgorithms;

/**
 * 
 * @author Martin Schaub
 */
public final class MouseClickRecorder implements MouseListener, MouseMotionListener, AlgorithmListener {

	/**
	 * Data model.
	 */
	private final Plane plane;
	/**
	 * Draws the current line.
	 */
	private final Drawer drawer;

	/**
	 * Determinates if the application can actually record or if an algorithm runs.
	 */
	private boolean canRecord = true;
	/**
	 * Position of the last click.
	 */
	private Point lastClick = null;
	/**
	 * Remembers the shape, so it can be removed afterwards.
	 */
	private Shape lastPaintedShape = null;

	/**
	 * 
	 * Constructor
	 * 
	 * @param plane data model
	 * @param drawer to draw the current line
	 */
	public MouseClickRecorder(final Plane plane, final Drawer drawer) {
		if (plane == null || drawer == null) {
			throw new NullPointerException();
		}
		this.plane = plane;
		this.drawer = drawer;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(final MouseEvent e) {
		// not implemented
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(final MouseEvent e) {
		// not implemented
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(final MouseEvent e) {
		// not implemented
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(final MouseEvent e) {
		if (canRecord) {
			// Right click
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
				// Remove the previously recorded line
				if (lastClick != null) {
					lastClick = null;

					if (lastPaintedShape != null) {
						drawer.removeDrawable(lastPaintedShape);
						lastPaintedShape = null;
					}
				}
				// Remove a line
				else {
					PlaneAlgorithms algo = new PlaneAlgorithms(plane);
					synchronized (plane) {
						for (Line line : algo.getLinesAtPoint(new ImmutablePoint(e.getX(), e.getY()), 1)) {
							plane.removeLine(line);
						}
					}
				}
			}
			// Left click
			else if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
				lastClick = new ImmutablePoint(e.getX(), e.getY());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(final MouseEvent e) {
		if (canRecord && (e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
			plane.addLine(new ImmutableLine(lastClick, new ImmutablePoint(e.getX(), e.getY())));
			lastClick = null;

			if (lastPaintedShape != null) {
				drawer.removeDrawable(lastPaintedShape);
				lastPaintedShape = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(final MouseEvent e) {
		if (canRecord) {
			// A new line is being painted
			if (lastClick != null) {
				if (lastPaintedShape != null) {
					drawer.removeDrawable(lastPaintedShape);
				}

				lastPaintedShape = new Line2D.Double(lastClick.getX(), lastClick.getY(), e.getX(), e.getY());
				drawer.addDrawable(lastPaintedShape);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(final MouseEvent e) {
		// not implemented
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#currentCoordinate(ch.fhnw.edu.efficientalgorithms
	 * .lineintersection.Point)
	 */
	@Override
	public void currentCoordinate(final Point point) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#processEnded()
	 */
	@Override
	public void processEnded() {
		canRecord = true;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#processStarted()
	 */
	@Override
	public void processStarted() {
		canRecord = false;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#reportObject(java.lang.Object)
	 */
	@Override
	public void reportObject(final Object object) {
		// nothing to do
	}
}
