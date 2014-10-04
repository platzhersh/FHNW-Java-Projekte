package ch.fhnw.edu.efficientalgorithms.graph.vertices;

import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.VertexFactory;

/**
 * Produces standard vertices.
 * 
 * @author Martin Schaub
 */
public final class StandardVertexFactory implements VertexFactory<Vertex> {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 7064976029572360234L;

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.VertexFactory#newVertex()
	 */
	@Override
	public Vertex newVertex() {
		return new StandardVertex("");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.VertexFactory#reset()
	 */
	@Override
	public void reset() {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.VertexFactory#getVertexClass()
	 */
	@SuppressWarnings("cast")
	@Override
	public Class<StandardVertex> getVertexClass() {
		return (Class<StandardVertex>) StandardVertex.class;
	}
}
