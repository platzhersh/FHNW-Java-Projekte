package ch.fhnw.christian.glatthard.algd2.graph;

public class Edge<T> {
	
	private Vertex<T> end;
	private Integer weight;
	
	public Vertex<T> getEnd() {
		return this.end;
	}
	
	public Edge(Vertex<T> end) {
		this.end = end;
	}

}
