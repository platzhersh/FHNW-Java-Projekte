package ch.fhnw.edu.efficientalgorithms.impl.gui;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.Point;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModelListener;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution;

/**
 * Draws a TSP model.
 * 
 * @author Martin Schaub
 */
public final class TSPDrawer extends JPanel implements TSPModelListener {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2274495013472394997L;
	/**
	 * Radius of a circle.
	 */
	private static final int CIRC_RADIUS = 4;

	/**
	 * Model to draw
	 */
	private final TSPModel model;

	/**
	 * 
	 * Constructor
	 * 
	 * @param model model to draw
	 */
	public TSPDrawer(final TSPModel model) {
		if (model == null) {
			throw new NullPointerException();
		}
		this.model = model;
	}

	/**
	 * Repaint the model so the new point appears.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void locationAdded(final Location location) {
		repaint();
	}

	/**
	 * Repaint the model so the old point does not appear any more.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void locationRemoved(final Location location) {
		repaint();
	}

	/**
	 * Draws the different points in the model.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void paint(final Graphics g) {
		super.paint(g);

		synchronized (model) {
			for (Location location : model.getLocations()) {
				Point point = location.getCoordinate();
				g.fillOval(point.getX() - CIRC_RADIUS, point.getY() - CIRC_RADIUS, CIRC_RADIUS * 2, CIRC_RADIUS * 2);
				g.drawString(location.getLabel(), point.getX(), point.getY());
			}

			TSPSolution solution = model.getSolution();
			if (solution != null) {
				Iterator<Location> it = solution.getTour().iterator();
				if (it.hasNext()) {
					Point old = it.next().getCoordinate();
					while (it.hasNext()) {
						Point cur = it.next().getCoordinate();
						g.drawLine(old.getX(), old.getY(), cur.getX(), cur.getY());
						old = cur;
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.tsp.TSPModelListener#newSolution(ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution)
	 */
	@Override
	public void newSolution(final TSPSolution solution) {
		repaint();
	}
}
