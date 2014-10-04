package ch.fhnw.edu.efficientalgorithms.impl.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ch.fhnw.edu.efficientalgorithms.impl.ImmutableLocation;
import ch.fhnw.edu.efficientalgorithms.impl.ImmutablePoint;
import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.Point;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus.Status;

/**
 * Records clicks in the panel to create locations.
 * 
 * @author Martin Schaub
 */
public final class ClickRecorder extends MouseAdapter {

	/**
	 * Defines the tolerance of the delete operation.
	 */
	private final static int TOLERANCE = 6;

	/**
	 * TSP model to create the locations.
	 */
	private final TSPModel model;
	/**
	 * Algorithm status to decide whether to record the points or not.
	 */
	private final TSPAlgorithmStatus status;

	/**
	 * Constructor
	 * 
	 * @param model model to add the locations
	 * @param status current status.
	 */
	public ClickRecorder(final TSPModel model, final TSPAlgorithmStatus status) {
		if (model == null || status == null) {
			throw new NullPointerException();
		}
		this.model = model;
		this.status = status;
	}

	/**
	 * Creates or deletes locations, depending on which mouse button was pressed.
	 */
	@Override
	public void mousePressed(final MouseEvent event) {
		if (status.getStatus().equals(Status.NO_ALGORITHM_RUNNING)
				|| status.getCurrentlyRunningAlgorithm().allowsModification()) {

			Point point = new ImmutablePoint(event.getX(), event.getY());
			switch (event.getButton()) {

			// Left click
			case MouseEvent.BUTTON1:
				model.addLocation(new ImmutableLocation(point));
			break;

			// Right click
			case MouseEvent.BUTTON3:
				synchronized (model) {
					Location loc = model.getLocationAt(point, TOLERANCE);
					if (loc != null) {
						model.removeLocation(loc);
					}
				}
			break;

			default:
			}
		}
	}
}
