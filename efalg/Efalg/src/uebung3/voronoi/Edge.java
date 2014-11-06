package uebung3.voronoi;

import geometry.Point;

public class Edge {
	Point start;
	Point end;
	VoronoiRegion regionLeft;
	VoronoiRegion regionRight;
	Edge nextLeft;
	Edge nextRight;
	
	public Edge(Point start, Point end, VoronoiRegion left, VoronoiRegion right, Edge leftEdge, Edge rightEdge) {
		this.start=start;
		this.end=end;
		regionLeft=left;
		regionRight=right;
		nextLeft=leftEdge;
		nextRight=rightEdge;
	}
	
	public Point getStart() {
		return start;
	}
	public Point getEnd() {
		return end;
	}
	
	public static Point interceptionPoint(Edge e1, Edge e2) {
		
		double x1,x2,x3,x4;
		double y1,y2,y3,y4;
		
		x1 = e1.getStart().x;
		x2 = e1.getEnd().x;
		x3 = e2.getStart().x;
		x4 = e2.getEnd().x;
		
		y1 = e1.getStart().y;
		y2 = e1.getEnd().y;
		y3 = e2.getStart().y;
		y4 = e2.getEnd().y;
		
		// check if vertical
		if (e1.getStart().x == e1.getEnd().x) {
			System.out.println("e1 vertical!");
		}
		if (e2.getStart().x == e2.getEnd().x) {
			System.out.println("e2 vertical!");
		}
		
		// equations
		double a1 = (y2-y1)/(x2-x1);
		double b1 = y1 - a1*x1;
		double a2 = (y4-y3)/(x4-x3);
		double b2 = y3 - a2*x3;
		
		// check if parallel
		if (a1 == a2) {
			System.out.println("parallel");
		}
		
		// solve equation system
		double x0 = -(b1-b2)/(a1-a2);
		
		// check if x0 is element of both edges
		if (Math.min(x1, x2) < x0 && x0 < Math.max(x1, x2) &&
				Math.min(x3, x4) < x0 && x0 < Math.max(x3, x4)
			) {
			return new Point(x0,a1*x0+b1);
		} else {
			return null;
		}
	}
}
