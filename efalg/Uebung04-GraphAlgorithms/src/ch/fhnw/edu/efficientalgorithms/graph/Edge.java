package ch.fhnw.edu.efficientalgorithms.graph;

import java.io.Serializable;

/**
 * Interface for edge implementation.
 * 
 * @author Martin Schaub
 */
public interface Edge extends Serializable {

	/**
	 * Returns the label of the edge.
	 * 
	 * @return edge's label
	 */
	String getLabel();
}
