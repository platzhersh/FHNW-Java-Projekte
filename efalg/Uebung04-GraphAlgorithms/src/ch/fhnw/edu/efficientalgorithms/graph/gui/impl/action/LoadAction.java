package ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.VertexFactory;
import ch.fhnw.edu.efficientalgorithms.graph.gui.Node;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;
import ch.fhnw.edu.efficientalgorithms.graph.impl.UniversalGraph;

/**
 * Loads a previously saved file into the current state.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge types
 */
public class LoadAction<V extends Vertex, E extends Edge> extends AbstractAction {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 3851015192873372712L;

	/**
	 * Program status.
	 */
	private final ProgramState<V, E> programState;

	/**
	 * Constructor
	 * 
	 * @param programState current program state
	 */
	public LoadAction(final ProgramState<V, E> programState) {
		if (programState == null) {
			throw new NullPointerException();
		}
		this.programState = programState;
	}

	/**
	 * Displays a file chooser dialog and guides the user trough the process.
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		synchronized (programState) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			chooser.setFileFilter(new FileNameExtensionFilter("graph files", "graph"));

			boolean loaded = false;
			while (!loaded && chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				if (!chooser.getSelectedFile().exists()) {
					JOptionPane.showMessageDialog(null, "Selected file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						loadFileContent(chooser.getSelectedFile());
						loaded = true;
					}
					catch (final EOFException ex) {
						// OK
						loaded = true;
					}
					catch (final IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error reading file! " + ex.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					catch (ClassNotFoundException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error reading file! " + ex.getMessage(), "Internal Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		}
	}

	/**
	 * Loads the file content into the program state.
	 * 
	 * @param toLoad file to load
	 * @throws IOException IO error occurred (EOFException is actually fine)
	 * @throws ClassNotFoundException class incompatibility
	 */
	@SuppressWarnings("unchecked")
	private void loadFileContent(final File toLoad) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(toLoad));
		programState.setEdgeFactory((EdgeFactory<E>) in.readObject());
		programState.setVertexFactory((VertexFactory<V>) in.readObject());

		boolean directed = in.readBoolean();
		Class<V> vertexClass = (Class<V>) in.readObject();
		Class<E> edgeClass = (Class<E>) in.readObject();
		Graph<V, E> graph = new UniversalGraph<V, E>(directed, edgeClass, vertexClass);

		Object o = in.readObject();
		while (o != null && o instanceof Vertex) {
			graph.addVertex((V) o);
			o = in.readObject();
		}

		while (o != null && o instanceof Edge) {
			V src = (V) in.readObject();
			V dst = (V) in.readObject();
			graph.addEdge(src, dst, (E) o);
			o = in.readObject();
		}

		programState.setGraph(graph);
		programState.getColorMapper().reset();
		programState.getLocationMapper().reset();

		while (o != null && o instanceof Vertex) {
			Vertex v = (Vertex) o;
			programState.getColorMapper().setVertexColor((V) v, (Color) in.readObject());
			Node<V> node = (Node<V>) in.readObject();
			programState.getLocationMapper().setLocation((V) v, node.getX(), node.getY());
			o = in.readObject();
		}

		while (o != null && o instanceof Edge) {
			Edge e = (Edge) o;
			programState.getColorMapper().setEdgeColor((E) e, (Color) in.readObject());
			o = in.readObject();
		}

		in.close();
	}

}
