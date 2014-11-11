package ch.fhnw.edu.efficientalgorithms.graph;

/**
 * Listeners for changes in the graph data structure.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public interface GraphListener<V extends Vertex, E extends Edge> {

	/**
	 * A new vertex was added to the graph.
	 * 
	 * @param v new vertex
	 */
	void vertexAdded(V v);

	/**
	 * Is called after a vertex was removed.
	 * 
	 * @param v vertex which was removed
	 */
	void vertexRemoved(V v);

	/**
	 * A new Edge was added to the graph structure.
	 * 
	 * @param e newly added edge
	 */
	void edgeAdded(E e);

	/**
	 * A edge was removed from the graph.
	 * 
	 * @param e removed edge
	 */
	void edgeRemoved(E e);
}
