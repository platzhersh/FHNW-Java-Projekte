package ch.fhnw.edu.efficientalgorithms.geneticalgorithms;

import java.util.List;

/**
 * 
 * @author Martin Schaub
 */
public interface ProgramStatus {

	/**
	 * Represents the possible program status.
	 */
	public enum Status {
		/**
		 * An algorithm is running
		 */
		AlgorithmRunning,
		/**
		 * No algorithm is running an the graphical user interface may record new points.
		 */
		Recording
	}

	/**
	 * Adds an new point (record it). This is only possible if the state is "Recording".
	 * 
	 * @param point point to add
	 */
	void addPoint(Point point);

	/**
	 * Gets an immutable list of all recorded points.
	 * 
	 * @return immutable list containing the points.
	 */
	List<Point> getPoints();

	/**
	 * Clears all recorded points. This is only possible in the "Recording" state.
	 */
	void clearPoints();

	/**
	 * Gets the current status of the application.
	 * 
	 * @return current status.
	 */
	Status getStatus();

	/**
	 * Sets the current status of the application.
	 * 
	 * @param status status to set
	 */
	void setStatus(Status status);

	/**
	 * Adds an observer.
	 * 
	 * @param listener observer to add
	 */
	void addProgramStatusListener(ProgramStatusListener listener);

	/**
	 * Deregisters an observer.
	 * 
	 * @param listener observer to deregister
	 */
	void removeProgramStatusListener(ProgramStatusListener listener);
}
