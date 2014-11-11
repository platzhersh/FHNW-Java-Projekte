package ch.fhnw.edu.efficientalgorithms.graph.edges;

/**
 * 
 * @author Martin Schaub
 */
public class CostCapacityFlowEdge extends CapacityFlowEdge {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -735637639967961108L;

	/**
	 * Edges cost.
	 */
	private final int cost;

	/**
	 * Constructor
	 * 
	 * @param capacity edge's capacity
	 * @param cost edge's cost
	 */
	public CostCapacityFlowEdge(final int capacity, final int cost) {
		super(capacity);
		this.cost = cost;
	}

	/**
	 * Getter method for the cost property.
	 * 
	 * @return the cost property's value
	 */
	public int getCost() {
		return cost;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.edges.CapacityFlowEdge#getLabel()
	 */
	@Override
	public String getLabel() {
		return super.getLabel() + "/" + cost;
	}
}
