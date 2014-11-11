package ch.fhnw.edu.efficientalgorithms.graph;

import ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper;
import ch.fhnw.edu.efficientalgorithms.graph.gui.LocationMapper;

/**
 * Needed data for a graph algorithm. This is
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public interface GraphAlgorithmData<V extends Vertex, E extends Edge> {

	/**
	 * The currently selected graph.
	 * 
	 * @return the graph
	 */
	Graph<V, E> getGraph();

	/**
	 * Returns the location mapper
	 * 
	 * @return the location mapper
	 */
	LocationMapper<V> getLocationMapper();

	/**
	 * Returns the used color mapper.
	 * 
	 * @return color mapper
	 */
	ColorMapper<V, E> getColorMapper();

	/**
	 * Returns the currently used edge factory.
	 * 
	 * @return currently used edge factory
	 */
	EdgeFactory<E> getEdgeFactory();

	/**
	 * Gets the currently selected vertex.
	 * 
	 * @return currently selected vertex or null if no vertex is selected
	 */
	V getSelectedVertex();

}
