package ch.fhnw.christian.glatthard.algd2.graph;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Graph<String> graph = new Graph<String>();
		
		graph.addVertex("Ich bin Vertex 0");
		graph.addVertex("Ich bin Vertex 1");
		graph.addVertex("Ich bin Vertex 2");
		graph.addVertex("Ich bin Vertex 3");
		
		Vertex<String> v0, v1, v2, v3, v4;
		v0 = graph.getVertexByID(0);
		v1 = graph.getVertexByID(1);
		v2 = graph.getVertexByID(2);
		v3 = graph.getVertexByID(3);
		v4 = graph.getVertexByID(4);
		
		v0.addEdge(v2);
		v1.addEdge(v2);
		v2.addEdge(v2);
		v3.addEdge(v2);
		
		v0.addEdge(v3);
		v1.addEdge(v3);
		v2.addEdge(v3);
		v3.addEdge(v3);
		
		//v0.removeEdge(v2);
			
		
		System.out.println(graph.toString());
		
	}

}
