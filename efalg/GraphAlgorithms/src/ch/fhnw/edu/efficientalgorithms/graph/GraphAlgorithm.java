package ch.fhnw.edu.efficientalgorithms.graph;

/**
 * Generic interface for all graph implementations.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public interface GraphAlgorithm<V extends Vertex, E extends Edge> {

	/**
	 * Executes the algorithm. The input graph needs to be considered read only.
	 * 
	 * @param data needed data for the algorithms
	 * @return status message or result.
	 */
	String execute(GraphAlgorithmData<V, E> data);

	/**
	 * If this algorithm does not change the graph, it can be run in a separate thread. Hence if the algorithm is started
	 * over a GUI the AWT repaint process can continue its work. If this behavior is requested but the algorithm changes
	 * the graph, synchronizing on the program state does work.
	 * 
	 * @return true if this thread is ready for running in a separate thread.
	 */
	boolean isThreadReady();

	/**
	 * Does it work with this graph implementation.
	 * 
	 * @param graph to check
	 * @return true, when this algorithm works on this graph.
	 */
	boolean worksWith(Graph<V, E> graph);

	/**
	 * Returns the name of the algorithm.
	 * @return Algorithm's name
	 */
	String getName();
}
