package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Line;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Plane;
import ch.fhnw.edu.efficientalgorithms.lineintersection.PlaneListener;

/**
 * A panel that is designed to draw the lines of the plane.
 * 
 * @author Martin Schaub
 */
public final class DrawPanel extends JPanel implements PlaneListener, Drawer {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 8254568503082792692L;
	/**
	 * Plane that is used as data source.
	 */
	private final Plane plane;
	/**
	 * Color of the shape to draw.
	 */
	private final Map<Shape, Color> toDraw = new HashMap<Shape, Color>();

	/**
	 * 
	 * Constructor
	 * 
	 * @param plane plane for data source
	 */
	public DrawPanel(final Plane plane) {
		if (plane == null) {
			throw new NullPointerException();
		}
		this.plane = plane;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(final Graphics graph) {
		super.paint(graph);
		if (!(graph instanceof Graphics2D)) {
			throw new InternalError();
		}

		Graphics2D graphics = (Graphics2D) graph;

		synchronized (plane) {
			for (Line line : plane.getLines()) {
				graphics.drawLine(line.getStartPoint().getX(), line.getStartPoint().getY(), line.getEndPoint().getX(), line
						.getEndPoint().getY());
			}
		}

		synchronized (toDraw) {
			Color orig = graphics.getColor();
			for (Entry<Shape, Color> entry : toDraw.entrySet()) {
				graphics.setColor(entry.getValue());
				graphics.draw(entry.getKey());
			}
			graphics.setColor(orig);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.PlaneListener#added(ch.fhnw.edu.efficientalgorithms.lineintersection
	 * .Line)
	 */
	@Override
	public void added(final Line line) {
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.PlaneListener#removed(ch.fhnw.edu.efficientalgorithms.lineintersection
	 * .Line)
	 */
	@Override
	public void removed(final Line line) {
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer#addDrawable(java.awt.Shape, java.awt.Color)
	 */
	@Override
	public void addDrawable(final Shape shape, final Color color) {
		if (shape == null || color == null) {
			throw new NullPointerException();
		}
		synchronized (toDraw) {
			if (toDraw.put(shape, color) == null) {
				repaint();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Drawable#addDrawable(java.awt.Shape)
	 */
	@Override
	public void addDrawable(final Shape shape) {
		addDrawable(shape, Color.BLACK);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Drawable#removeDrawable(java.awt.Shape)
	 */
	@Override
	public void removeDrawable(final Shape shape) {
		if (shape == null) {
			throw new NullPointerException();
		}
		synchronized (toDraw) {
			if (toDraw.remove(shape) != null) {
				repaint();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer#clearAllDrawables()
	 */
	@Override
	public void clearAllDrawables() {
		synchronized (toDraw) {
			toDraw.clear();
			repaint();
		}
	}
}
