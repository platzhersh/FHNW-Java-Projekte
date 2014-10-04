package ch.fhnw.edu.efficientalgorithms.graph.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphListener;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

/**
 * A graph implementation using HashMaps and HashSets to store the needed information. This graph can be used with
 * either directed or undirected edges.
 * 
 * @author Martin Schaub
 * 
 * @param <V> Vertex type
 * @param <E> Edge type
 */
public class UniversalGraph<V extends Vertex, E extends Edge> implements Graph<V, E> {

	/**
	 * Defines whether the graph is directed or not.
	 */
	private final boolean directed;
	/**
	 * Instantiated vertex class
	 */
	private final Class<? extends Vertex> vertexClass;
	/**
	 * Instantiated edge class
	 */
	private final Class<? extends Edge> edgeClass;

	/**
	 * Outgoing Edge list.
	 */
	private Map<V, Set<E>> outgoingEdges = new HashMap<V, Set<E>>();
	/**
	 * Incoming Edge list.
	 */
	private Map<V, Set<E>> incomingEdges = new HashMap<V, Set<E>>();
	/**
	 * Outgoing Vertex list.
	 */
	private Map<V, Set<V>> outgoingVertices = new HashMap<V, Set<V>>();
	/**
	 * Incoming Vertex list.
	 */
	private Map<V, Set<V>> incomingVertices = new HashMap<V, Set<V>>();
	/**
	 * Saves the source of a edge.
	 */
	private Map<E, V> sources = new HashMap<E, V>();
	/**
	 * Stores the destination of a edge.
	 */
	private Map<E, V> destination = new HashMap<E, V>();

	/**
	 * For faster access this set stores all vertices
	 */
	private Set<V> vertices = new HashSet<V>();
	/**
	 * For faster access to all edges this set was introduced.
	 */
	private Set<E> edges = new HashSet<E>();
	/**
	 * Stores all registered graph listeners.
	 */
	private Set<GraphListener<V, E>> listeners = new HashSet<GraphListener<V, E>>();

	/**
	 * Constructor
	 * 
	 * @param directed true, if the graph should be directed or false otherwise
	 * @param edgeClass edge class
	 * @param vertexClass vertex class
	 */
	public UniversalGraph(final boolean directed, final Class<? extends Edge> edgeClass,
			final Class<? extends Vertex> vertexClass) {
		if (edgeClass == null || vertexClass == null) {
			throw new NullPointerException();
		}
		this.directed = directed;
		this.edgeClass = edgeClass;
		this.vertexClass = vertexClass;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#addEdge(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public synchronized boolean addEdge(final V from, final V to, final E e) {
		if (!vertices.contains(from) || !vertices.contains(to)) {
			throw new IllegalArgumentException("Vertices must be in graph");
		}
		if (e == null) {
			throw new NullPointerException("Edge mustn't be null");
		}
		if (from.equals(to)) {
			throw new IllegalArgumentException("Self loops are not allowed");
		}
		if (edges.contains(e)) {
			return false;
		}

		E prevEdge = getEdge(from, to);
		if (prevEdge != null) {
			removeEdge(prevEdge);
		}

		edges.add(e);
		checkRetNull(sources.put(e, from));
		checkRetNull(destination.put(e, to));

		addEdgeInternal(from, to, e);
		if (!directed) {
			addEdgeInternal(to, from, e);
		}

		// Notify listeners
		for (GraphListener<V, E> listener : listeners) {
			listener.edgeAdded(e);
		}

		assert getEdge(from, to).equals(e);
		assert getEndpoints(e).get(0).equals(from);
		assert getEndpoints(e).get(1).equals(to);
		assert directed || getEdge(to, from).equals(e);
		assert getEdges().contains(e);

		return true;
	}

	/**
	 * Adds an edge to the various data structures.
	 * 
	 * @param from source
	 * @param to destination
	 * @param e edge to add
	 */
	private void addEdgeInternal(final V from, final V to, final E e) {
		checkRetTrue(incomingEdges.get(to).add(e));
		checkRetTrue(incomingVertices.get(to).add(from));
		checkRetTrue(outgoingEdges.get(from).add(e));
		checkRetTrue(outgoingVertices.get(from).add(to));
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#addVertex(java.lang.Object)
	 */
	@Override
	public synchronized boolean addVertex(final V v) {
		if (v == null) {
			throw new NullPointerException("Vertex mustn't be null");
		}

		if (vertices.contains(v)) {
			return false;
		}

		checkRetNull(incomingEdges.put(v, new HashSet<E>()));
		checkRetNull(incomingVertices.put(v, new HashSet<V>()));
		checkRetNull(outgoingEdges.put(v, new HashSet<E>()));
		checkRetNull(outgoingVertices.put(v, new HashSet<V>()));
		checkRetTrue(vertices.add(v));

		// Notify listeners
		for (GraphListener<V, E> listener : listeners) {
			listener.vertexAdded(v);
		}

		assert getOutgoingAdjacence(v).size() == 0;
		assert getIncomingAdjacence(v).size() == 0;
		assert getVertices().contains(v);

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getEdge(java.lang.Object, java.lang.Object)
	 */
	@Override
	public synchronized E getEdge(final V from, final V to) {
		if (!vertices.contains(from) || !vertices.contains(to)) {
			throw new IllegalArgumentException("Vertices must be in graph");
		}

		for (E e : outgoingEdges.get(from)) {
			// Considers both cases, directed and undirected.
			if (destination.get(e).equals(to) || sources.get(e).equals(to)) {
				return e;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getEdges()
	 */
	@Override
	public Set<E> getEdges() {
		return Collections.unmodifiableSet(edges);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getEndpoints(java.lang.Object)
	 */
	@Override
	public synchronized List<V> getEndpoints(final E e) {
		if (!edges.contains(e)) {
			throw new IllegalArgumentException("Edge must be in the graph");
		}

		ArrayList<V> list = new ArrayList<V>();
		list.add(sources.get(e));
		list.add(destination.get(e));

		assert list.size() == 2;
		assert getEdge(list.get(0), list.get(1)).equals(e);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getIncomingAdjacence(java.lang.Object)
	 */
	@Override
	public synchronized Set<V> getIncomingAdjacence(final V v) {
		if (!vertices.contains(v)) {
			throw new IllegalArgumentException("Vertex must be in graph");
		}
		return Collections.unmodifiableSet(incomingVertices.get(v));
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getNumOfEdges()
	 */
	@Override
	public int getNumOfEdges() {
		return edges.size();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getNumOfVertices()
	 */
	@Override
	public int getNumOfVertices() {
		return vertices.size();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getOutgoingAdjacence(java.lang.Object)
	 */
	@Override
	public synchronized Set<V> getOutgoingAdjacence(final V v) {
		if (!vertices.contains(v)) {
			throw new IllegalArgumentException("Vertex must be in graph");
		}
		return Collections.unmodifiableSet(outgoingVertices.get(v));
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getVertices()
	 */
	@Override
	public Set<V> getVertices() {
		return Collections.unmodifiableSet(vertices);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#isDirected()
	 */
	@Override
	public boolean isDirected() {
		return directed;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#removeEdge(java.lang.Object)
	 */
	@Override
	public synchronized boolean removeEdge(final E e) {
		if (e == null) {
			throw new NullPointerException();
		}

		if (!edges.contains(e)) {
			return false;
		}

		V src = sources.get(e);
		V dst = destination.get(e);

		checkRetNotNull(incomingVertices.get(dst).remove(src));
		checkRetNotNull(outgoingVertices.get(src).remove(dst));
		checkRetNotNull(incomingEdges.get(dst).remove(e));
		checkRetNotNull(outgoingEdges.get(src).remove(e));

		if (!directed) {
			checkRetNotNull(incomingVertices.get(src).remove(dst));
			checkRetNotNull(outgoingVertices.get(dst).remove(src));
			checkRetNotNull(incomingEdges.get(src).remove(e));
			checkRetNotNull(outgoingEdges.get(dst).remove(e));
		}

		checkRetTrue(edges.remove(e));
		checkRetNotNull(sources.remove(e));
		checkRetNotNull(destination.remove(e));

		// Notify listeners
		for (GraphListener<V, E> listener : listeners) {
			listener.edgeAdded(e);
		}

		assert !getEdges().contains(e);

		return true;
	}

	/**
	 * Checks that the object is not null and throws a exception otherwise.
	 * 
	 * @param obj object to test
	 * @throws InternalError if the object is null
	 */
	private void checkRetNotNull(final Object obj) {
		checkRetTrue(obj != null);
	}

	/**
	 * Checks that a object is null and throws a exception in the other case.
	 * 
	 * @param obj object to test
	 * @throws InternalError if the object is not null
	 */
	private void checkRetNull(final Object obj) {
		checkRetTrue(obj == null);
	}

	/**
	 * Checks whether a bool is true or not. This method is called to verify return values.
	 * 
	 * @param bool boolean to check
	 * @throws InternalError if the boolean is not true
	 */
	private void checkRetTrue(final boolean bool) {
		if (!bool) {
			throw new InternalError();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#removeVertex(java.lang.Object)
	 */
	@Override
	public synchronized boolean removeVertex(final V v) {
		if (v == null) {
			throw new NullPointerException();
		}

		if (!vertices.contains(v)) {
			return false;
		}

		// Cascade removal
		for (E e : new LinkedList<E>(incomingEdges.get(v))) {
			checkRetTrue(removeEdge(e));
		}
		for (E e : new LinkedList<E>(outgoingEdges.get(v))) {
			checkRetTrue(removeEdge(e));
		}

		// Actually remove vertex
		checkRetNotNull(incomingEdges.remove(v));
		checkRetNotNull(outgoingEdges.remove(v));
		checkRetTrue(vertices.remove(v));

		// Notify listeners
		for (GraphListener<V, E> listener : listeners) {
			listener.vertexRemoved(v);
		}

		assert !vertices.contains(v);

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getIncomingEdges(java.lang.Object)
	 */
	@Override
	public synchronized Set<E> getIncomingEdges(final V v) {
		if (v == null) {
			throw new NullPointerException();
		}
		if (!vertices.contains(v)) {
			throw new IllegalArgumentException("Vertex must be in graph");
		}
		return Collections.unmodifiableSet(incomingEdges.get(v));
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getOutgoingEdges(java.lang.Object)
	 */
	@Override
	public synchronized Set<E> getOutgoingEdges(final V v) {
		if (v == null) {
			throw new NullPointerException();
		}
		if (!vertices.contains(v)) {
			throw new IllegalArgumentException("Vertex must be in graph");
		}
		return Collections.unmodifiableSet(outgoingEdges.get(v));
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized UniversalGraph<V, E> clone() {
		try {
			UniversalGraph<V, E> ret = (UniversalGraph<V, E>) super.clone();

			ret.edges = new HashSet<E>(edges);
			ret.vertices = new HashSet<V>(vertices);

			ret.incomingEdges = new HashMap<V, Set<E>>(incomingEdges.size() * 2);
			for (Entry<V, Set<E>> e : incomingEdges.entrySet()) {
				ret.incomingEdges.put(e.getKey(), new HashSet<E>(e.getValue()));
			}
			ret.incomingVertices = new HashMap<V, Set<V>>(incomingVertices.size() * 2);
			for (Entry<V, Set<V>> e : incomingVertices.entrySet()) {
				ret.incomingVertices.put(e.getKey(), new HashSet<V>(e.getValue()));
			}
			ret.outgoingEdges = new HashMap<V, Set<E>>(outgoingEdges.size() * 2);
			for (Entry<V, Set<E>> e : outgoingEdges.entrySet()) {
				ret.outgoingEdges.put(e.getKey(), new HashSet<E>(e.getValue()));
			}
			ret.outgoingVertices = new HashMap<V, Set<V>>(outgoingVertices.size() * 2);
			for (Entry<V, Set<V>> e : outgoingVertices.entrySet()) {
				ret.outgoingVertices.put(e.getKey(), new HashSet<V>(e.getValue()));
			}

			ret.destination = new HashMap<E, V>(destination);
			ret.sources = new HashMap<E, V>(sources);

			ret.listeners = new HashSet<GraphListener<V, E>>(listeners);

			return ret;
		}
		catch (final CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.Graph#addGraphListener(ch.fhnw.edu.efficientalgorithms.graph.GraphListener)
	 */
	@Override
	public void addGraphListener(final GraphListener<V, E> listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.Graph#removeGraphListener(ch.fhnw.edu.efficientalgorithms.graph.GraphListener
	 * )
	 */
	@Override
	public void removeGraphListener(final GraphListener<V, E> listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#getListeners()
	 */
	@Override
	public Set<GraphListener<V, E>> getListeners() {
		return Collections.unmodifiableSet(listeners);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#edgeClass()
	 */
	@Override
	public Class<? extends Edge> edgeClass() {
		return edgeClass;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Graph#vertexClass()
	 */
	@Override
	public Class<? extends Vertex> vertexClass() {
		return vertexClass;
	}
}
