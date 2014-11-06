package uebung3.voronoi;


import geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class VoronoiRegion {
	List<Edge> edges;
	Point p;
	
	public VoronoiRegion(Point p) {
		this.p = p;
		edges = new ArrayList<Edge>();
	}
}
