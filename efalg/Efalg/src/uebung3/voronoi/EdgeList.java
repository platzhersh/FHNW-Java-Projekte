package uebung3.voronoi;

import java.util.LinkedList;

import uebung3.voronoi.helpers.Edge;

public class EdgeList {
	Edge head;
	public LinkedList<Edge> edges;
	
	public EdgeList() {
		head = null;
		edges = new LinkedList<Edge>();
	}
	
	public EdgeList(Edge h) {
		head = h;
		edges = new LinkedList<Edge>();
		edges.add(h);
	}
	
	public int size() {
		return edges.size();
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
}
