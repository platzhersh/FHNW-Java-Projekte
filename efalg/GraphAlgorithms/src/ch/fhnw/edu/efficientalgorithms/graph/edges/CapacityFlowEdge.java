package ch.fhnw.edu.efficientalgorithms.graph.edges;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;

/**
 * Edge implementation for the max flow algorithm. Cannot use StandardEdge as base class, because it needs a modifiable
 * label.
 * 
 * @author Martin Schaub
 */
public class CapacityFlowEdge implements Edge {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 5981516618903415181L;

	/**
	 * Predefined capacity
	 */
	private final int capacity;
	/**
	 * Current network flow
	 */
	private int flow;

	/**
	 * Constructor
	 * 
	 * @param capacity predefined capacity of the edge
	 */
	public CapacityFlowEdge(final int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Getter method for the flow property.
	 * 
	 * @return the flow property's value
	 */
	public int getFlow() {
		return flow;
	}

	/**
	 * Setter method for the flow property.
	 * 
	 * @param flow the new flow to set
	 */
	public void setFlow(final int flow) {
		if (flow < 0) {
			throw new IllegalArgumentException();
		}
		if (flow > capacity) {
			throw new IllegalArgumentException();
		}
		this.flow = flow;
	}

	/**
	 * Getter method for the capacity property.
	 * 
	 * @return the capacity property's value
	 */
	public int getCapacity() {
		return capacity;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Edge#getLabel()
	 */
	@Override
	public String getLabel() {
		return capacity + "/" + flow;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getLabel();
	}

}
