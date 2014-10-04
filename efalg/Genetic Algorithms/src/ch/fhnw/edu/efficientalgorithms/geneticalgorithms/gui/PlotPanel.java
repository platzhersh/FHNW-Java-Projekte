package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.gui;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Function;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Plot;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Point;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.impl.PointImpl;

/**
 * Displays a coordinate system on a JPanel.
 *
 * @author Martin Schaub
 */
public final class PlotPanel extends JPanel implements Plot {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -8983635212968479021L;
	/**
	 * How often will a distance marking be added.
	 */
	private static final int AXIS_MARKING = 50;
	/**
	 * Length of a axis distance marking
	 */
	private static final int AXIS_MARKING_LENGTH = 10;
	/**
	 * Length of an arc on the axis
	 */
	private static final int ARC_LENGTH = 10;
	/**
	 * How much a point is in diameter
	 */
	private static final int POINT_SIZE = 6;
	/**
	 * How much space is between the window and coordinate system
	 */
	private static final int WINDOW_OFFSET = 10;

	/**
	 * Points to plot
	 */
	private final List<Point> points = new LinkedList<Point>();
	/**
	 * Functions to plot.
	 */
	private final List<Function> functions = new LinkedList<Function>();

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(final Graphics graphics) {
		super.paint(graphics);

		int middleX = getMiddleX();
		int startX = WINDOW_OFFSET;
		int endX = getWidth() - WINDOW_OFFSET;

		int middleY = getMiddleY();
		int startY = WINDOW_OFFSET;
		int endY = getHeight() - WINDOW_OFFSET;

		// Draw X axis
		graphics.drawLine(startX, middleY, endX, middleY);
		// Draw arcs
		graphics.drawLine(startX, middleY, startX + ARC_LENGTH, middleY + ARC_LENGTH);
		graphics.drawLine(startX, middleY, startX + ARC_LENGTH, middleY - ARC_LENGTH);
		graphics.drawLine(endX, middleY, endX - ARC_LENGTH, middleY + ARC_LENGTH);
		graphics.drawLine(endX, middleY, endX - ARC_LENGTH, middleY - ARC_LENGTH);
		// Draw positive X markings
		for (int i = middleX + AXIS_MARKING; i < endX; i += AXIS_MARKING) {
			graphics.drawLine(i, middleY + AXIS_MARKING_LENGTH / 2, i, middleY - AXIS_MARKING_LENGTH / 2);
			graphics.drawString(Integer.toString(i - middleX), i - 10, middleY + AXIS_MARKING_LENGTH * 2);
		}
		// Draw negative X markings
		for (int i = middleX - AXIS_MARKING; i >= startX; i -= AXIS_MARKING) {
			graphics.drawLine(i, middleY + AXIS_MARKING_LENGTH / 2, i, middleY - AXIS_MARKING_LENGTH / 2);
			graphics.drawString(Integer.toString(-(middleX - i)), i - 10, middleY + AXIS_MARKING_LENGTH * 2);
		}

		// Draw Y axis
		graphics.drawLine(middleX, startY, middleX, endY);
		// Draw arcs
		graphics.drawLine(middleX, endY, middleX + ARC_LENGTH, endY - ARC_LENGTH);
		graphics.drawLine(middleX, endY, middleX - ARC_LENGTH, endY - ARC_LENGTH);
		graphics.drawLine(middleX, startY, middleX + ARC_LENGTH, startY + ARC_LENGTH);
		graphics.drawLine(middleX, startY, middleX - ARC_LENGTH, startY + ARC_LENGTH);
		// Positive Y markings
		for (int i = middleY + AXIS_MARKING; i < endY; i += AXIS_MARKING) {
			graphics.drawLine(middleX + AXIS_MARKING_LENGTH / 2, i, middleX - AXIS_MARKING_LENGTH / 2, i);
			graphics.drawString(Integer.toString(-(i - middleY)), middleX - AXIS_MARKING_LENGTH * 3, i + 5);
		}
		// Negative Y markings
		for (int i = middleY - AXIS_MARKING; i >= startY; i -= AXIS_MARKING) {
			graphics.drawLine(middleX + AXIS_MARKING_LENGTH / 2, i, middleX - AXIS_MARKING_LENGTH / 2, i);
			graphics.drawString(Integer.toString((middleY - i)), middleX - AXIS_MARKING_LENGTH * 3, i + 5);
		}

		synchronized (this) {
			for (Point point : points) {
				int xDraw = translateXToJava(point.getX());
				int yDraw = translateYToJava(point.getY());
				graphics.fillOval(xDraw - POINT_SIZE / 2, yDraw - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
			}
			for (Function function : functions) {
				// cannot use polygon method, because it closes the last line
				int lastY = translateYToJava((int) function.getValueAt(translateXFromJava(startX)));
				for (int i = startX + 1; i < endX; ++i) {
					int curY = translateYToJava((int) function.getValueAt(translateXFromJava(i)));
					graphics.drawLine(i - 1, lastY, i - 1, curY);
					lastY = curY;
				}
			}
		}
	}

	/**
	 * Gets the origin's X coordinate
	 *
	 * @return x coordinate
	 */
	private int getMiddleX() {
		return getWidth() / 2;
	}

	/**
	 * Gets the origin's Y coordinate
	 *
	 * @return y coordinate
	 */
	private int getMiddleY() {
		return getHeight() / 2;
	}

	/**
	 * Translates a X coordinate from the coordinate system to java.
	 *
	 * @param xToTanslate x coordinate to translate
	 * @return translated x coordinate
	 */
	private int translateXToJava(final int xToTanslate) {
		return xToTanslate + getMiddleX();
	}

	/**
	 * Translates a Y coordinate from the coordinate system to java.
	 *
	 * @param yToTranslate y coordinate to translate
	 * @return translated y coordinate
	 */
	private int translateYToJava(final int yToTranslate) {
		return -yToTranslate + getMiddleY();
	}

	/**
	 * Translates a X coordinate from java to the coordinate system.
	 *
	 * @param xToTranslate x coordinate to translate
	 * @return translated x coordinate
	 */
	private int translateXFromJava(final int xToTranslate) {
		return xToTranslate - getMiddleX();
	}

	/**
	 * Translates a Y coordinate from java to the coordinate system.
	 *
	 * @param yToTranslate y coordinate to translate
	 * @return translated y coordinate
	 */
	private int translateYFromJava(final int yToTranslate) {
		return getMiddleY() - yToTranslate;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Plot#addPoint(ch.fhnw.edu.efficientalgorithms.geneticalgorithms
	 * .Point)
	 */
	@Override
	public synchronized void addPoint(final Point point) {
		if (point == null) {
			throw new NullPointerException();
		}
		points.add(point);
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Plot#removePoint(ch.fhnw.edu.efficientalgorithms.geneticalgorithms
	 * .Point)
	 */
	@Override
	public synchronized void removePoint(final Point point) {
		if (point == null) {
			throw new NullPointerException();
		}
		points.remove(point);
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.Plot#getCoordinateOf(ch.fhnw.edu.efficientalgorithms.
	 * geneticalgorithms.Point)
	 */
	@Override
	public Point getCoordinateOf(final Point point) {
		if (point == null) {
			throw new NullPointerException();
		}

		// Java:
		// ------------ x
		// |
		// |
		// |
		// | y

		// Plot:
		// ,,,,,,,,,,|
		// ,,,,,,,,,,|
		// ,,,,,,,,,,|
		// ------------------------- x
		// ,,,,,,,,,,|
		// ,,,,,,,,,,| y

		int x = translateXFromJava(point.getX());
		int y = translateYFromJava(point.getY());
		return new PointImpl(x, y);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Plot#addFunction(ch.fhnw.edu.efficientalgorithms.geneticalgorithms
	 * .Function)
	 */
	@Override
	public synchronized void addFunction(final Function function) {
		if (function == null) {
			throw new NullPointerException();
		}
		functions.add(function);
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Plot#removeFunction(ch.fhnw.edu.efficientalgorithms.geneticalgorithms
	 * .Function)
	 */
	@Override
	public synchronized void removeFunction(final Function function) {
		if (function == null) {
			throw new NullPointerException();
		}
		functions.remove(function);
		repaint();
	}
}
