package uebung3.voronoi;

import geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class Model {
	public ArrayList<Point> points;
	public ArrayList<VoronoiRegion> regions;
	
	public Model() {
		points = new ArrayList<Point>();
		regions =  new ArrayList<VoronoiRegion>();
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
}
