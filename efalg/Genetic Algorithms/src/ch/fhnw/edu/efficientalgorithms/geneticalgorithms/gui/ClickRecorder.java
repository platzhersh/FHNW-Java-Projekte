package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Plot;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus.Status;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.impl.PointImpl;

/**
 * Records mouse clicks on the plot and records it to the data structure.
 * 
 * @author Martin Schaub
 */
public final class ClickRecorder extends MouseAdapter {

	/**
	 * Program status to add the mouse clicks.
	 */
	private final ProgramStatus status;
	/**
	 * For translating the (x,y) points of the screen to coordinates.
	 */
	private final Plot plot;

	/**
	 * Constructor
	 * 
	 * @param status program status to add the mouse clicks
	 * @param plot plot for translating the coordinates
	 */
	public ClickRecorder(final ProgramStatus status, final Plot plot) {
		if (status == null || plot == null) {
			throw new NullPointerException();
		}
		this.status = status;
		this.plot = plot;
	}

	/**
	 * Adds mouse clicks to the program status.
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
		synchronized (status) {
			if (status.getStatus().equals(Status.Recording)) {
				status.addPoint(plot.getCoordinateOf(new PointImpl(e.getX(), e.getY())));
			}
		}
	}
}
