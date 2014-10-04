package ch.fhnw.edu.efficientalgorithms.graph;

import java.util.List;
import java.util.Set;

/**
 * Defines the interface of a graph.
 * 
 * @author Martin Schaub
 * 
 * @param <V> Vertex type
 * @param <E> Edge type
 */
public interface Graph<V extends Vertex, E extends Edge> extends Cloneable {

	/**
	 * Defines whether the graph is directed or undirected. This might have implication to other functions.
	 * 
	 * @return true, if it is directed and false otherwise
	 */
	boolean isDirected();

	/**
	 * Adds a vertex to the graph.
	 * 
	 * @param v vertex to add.
	 * @return true, if the vertex wasn't already in the graph and false otherwise.
	 */
	boolean addVertex(V v);

	/**
	 * Removes a vertex from the graph.
	 * 
	 * @param v vertex to remove
	 * @return true, if the vertex was in the graph and false otherwise.
	 */
	boolean removeVertex(V v);

	/**
	 * Returns all vertices. This set must be considered read only.
	 * 
	 * @return set containing all vertices.
	 */
	Set<V> getVertices();

	/**
	 * Returns the number of vertices in the graph.
	 * 
	 * @return number of vertices.
	 */
	int getNumOfVertices();

	/**
	 * Adds a new edge to the graph. If the graph is undirected the edge will also be added in the other direction. Self
	 * loops are not allowed.
	 * 
	 * @param from source vertex.
	 * @param to destination vertex.
	 * @param e edge to add.
	 * @return true, if the edge wasn't already in the graph. If there was already a different edge in this direction, the
	 *         answer is still true and this edge will be removed.
	 */
	boolean addEdge(V from, V to, E e);

	/**
	 * Removes an edge from the graph.
	 * 
	 * @param e edge to remove.
	 * @return true, if the edge was in the graph and false otherwise.
	 */
	boolean removeEdge(E e);

	/**
	 * Returns the number of edges in the graph.
	 * @return number of edges in the graph.
	 */
	int getNumOfEdges();

	/**
	 * Returns all edges in the graph. This set has to be considered read only.
	 * 
	 * @return all edges in the graph.
	 */
	Set<E> getEdges();

	/**
	 * Returns the edge between two vertices or null if it does not exist. In case of an undirected graph, both cases are
	 * considered.
	 * 
	 * @param from source vertex
	 * @param to destination vertex
	 * @return the edge contained in it or null if it does not exist
	 */
	E getEdge(V from, V to);

	/**
	 * Gets the endpoints of an edge. Position 0 is the source and position 1 the destination.
	 * @param e whose endpoints must be collected
	 * @return the endpoints
	 */
	List<V> getEndpoints(E e);

	/**
	 * Gets all outgoing edges in case of a directed graph or all edges connected to v otherwise. The set must be
	 * considered read only.
	 * 
	 * @param v vertex to get the outgoing edges from
	 * @return all outgoing edges of a vertex
	 */
	Set<E> getOutgoingEdges(V v);

	/**
	 * Gets all incoming edges in case of a directed graph or all edges connected to v otherwise. The set must be
	 * considered read only.
	 * 
	 * @param v vertex to get the incoming edges from
	 * @return all incoming edges
	 */
	Set<E> getIncomingEdges(V v);

	/**
	 * Gets all neighbors of an vertex, to which he has an outgoing edge in case of a directed graph or is connected to it
	 * form an undirected graph. The set must be considered read only.
	 * @param v vertex
	 * @return a set of all neighbors
	 */
	Set<V> getOutgoingAdjacence(V v);

	/**
	 * Gets all neighbors of an vertex, to which he has an incoming edge in case of a directed graph or is connected to it
	 * form an undirected graph. The set must be considered read only.
	 * @param v vertex
	 * @return a set of all neighbors
	 */
	Set<V> getIncomingAdjacence(V v);

	/**
	 * Registers a graph listener. A listener can only registered once.
	 * 
	 * @param listener graph listener to register
	 */
	void addGraphListener(GraphListener<V, E> listener);

	/**
	 * Removes a graphlistener.
	 * 
	 * @param listener graphlistener to remove.
	 */
	void removeGraphListener(GraphListener<V, E> listener);

	/**
	 * Returns all listeners.
	 * 
	 * @return all registered listeners.
	 */
	Set<GraphListener<V, E>> getListeners();

	/**
	 * Returns the type of the edge class.
	 * 
	 * @return edge class type
	 */
	Class<? extends Edge> edgeClass();

	/**
	 * Gets the vertex class (this info cannot be obtained from the generic instantiation.
	 * 
	 * @return vertex class type
	 */
	Class<? extends Vertex> vertexClass();

	/**
	 * Creates a deep-copy of the graph.
	 * @return new copy
	 */
	Graph<V, E> clone();
}
