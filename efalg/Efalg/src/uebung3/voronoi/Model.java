package uebung3.voronoi;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uebung3.voronoi.helpers.Edge;
import uebung3.voronoi.helpers.Point;

public class Model {
	public ArrayList<Point> points;
	public ArrayList<Point> regions;
	public EdgeList edgeList;
	public Map<Point, List<Edge>> voronoiRegions;
	
	public Model() {
		points = new ArrayList<Point>();
		regions =  new ArrayList<Point>();
		edgeList = new EdgeList();
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public LinkedList<Edge> getEdges() {
		return edgeList.edges;
	}
	
}
