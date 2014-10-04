package ch.fhnw.edu.efficientalgorithms.graph.gui;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.VertexFactory;

/**
 * Stores all relevant information of the graphical user interface at a central point.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type.
 * @param <E> edge type.
 */
public interface ProgramState<V extends Vertex, E extends Edge> {

	/**
	 * Possible program states.
	 */
	enum State {
		/**
		 * New nodes will be inserted.
		 */
		NodeInsert,
		/**
		 * Edges are inserted
		 */
		EdgeInsert,
		/**
		 * Selection mode. (move around)
		 */
		Selection,
		/**
		 * Delete selected nodes
		 */
		NodeDelete,
		/**
		 * Remove a edge.
		 */
		EdgeDelete
	}

	/**
	 * Gets the current program state.
	 * 
	 * @return current program state.
	 */
	State getState();

	/**
	 * Returns the currently selected vertex in the graphical user interface
	 * 
	 * @return currently selected vertex or null if no one is selected
	 */
	V getCurrentlySelectedVertex();

	/**
	 * Updates the currently selected vertex
	 * 
	 * @param v currently selected vertex or null if no vertex is selected.
	 */
	void setCurrentlySelectedVertex(V v);

	/**
	 * Sets a new state.
	 * 
	 * @param state new state
	 */
	void setState(State state);

	/**
	 * Returns the currently used graph.
	 * 
	 * @return currently used graph.
	 */
	Graph<V, E> getGraph();

	/**
	 * Sets a new graph as currently used one.
	 * 
	 * @param graph new graph to set.
	 */
	void setGraph(Graph<V, E> graph);

	/**
	 * Gets the Edgefactory to produce new edges.
	 * 
	 * @return currently set edge factory.
	 */
	EdgeFactory<E> getEdgeFactory();

	/**
	 * Sets a new factory.
	 * 
	 * @param factory new edge factory.
	 */
	void setEdgeFactory(EdgeFactory<E> factory);

	/**
	 * Gets the location mapper.
	 * 
	 * @return location mapper.
	 */
	LocationMapper<V> getLocationMapper();

	/**
	 * Returns the currently used vertex factory.
	 * 
	 * @return vertex factory
	 */
	VertexFactory<V> getVertexFactory();

	/**
	 * Sets a new vertex factory
	 * 
	 * @param factory factory to set.
	 */
	void setVertexFactory(VertexFactory<V> factory);

	/**
	 * Gets the color mapper.
	 * 
	 * @return color mapper
	 */
	ColorMapper<V, E> getColorMapper();

	/**
	 * Adds a listener.
	 * 
	 * @param listener listener to add.
	 */
	void addStateListener(ProgramStateListener<V, E> listener);

	/**
	 * Removes a listener.
	 * 
	 * @param listener listener to remove.
	 */
	void removeStateListener(ProgramStateListener<V, E> listener);

}
