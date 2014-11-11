package ch.fhnw.edu.efficientalgorithms.graph.gui;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.VertexFactory;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.ColorMapperImpl;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.GraphGUI;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.LocatonMapperImpl;
import ch.fhnw.edu.efficientalgorithms.graph.gui.impl.ProgramStateImpl;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertexFactory;

/**
 * Main class.
 * 
 * @author Martin Schaub
 */
public class GraphMain {

	/**
	 * Main method.
	 * 
	 * @param args command line arguments
	 */
	public static void main(final String[] args) {
		LocatonMapperImpl<Vertex, Edge> locationMapper = new LocatonMapperImpl<Vertex, Edge>();
		ColorMapperImpl<Vertex, Edge> colorMapper = new ColorMapperImpl<Vertex, Edge>();
		VertexFactory<Vertex> vertexFactory = new IntegerVertexFactory();

		ProgramStateImpl<Vertex, Edge> state = new ProgramStateImpl<Vertex, Edge>(locationMapper, colorMapper,
				vertexFactory);

		state.getGraph().addGraphListener(locationMapper);
		state.getGraph().addGraphListener(colorMapper);

		new GraphGUI<Vertex, Edge>(state);
	}

}
