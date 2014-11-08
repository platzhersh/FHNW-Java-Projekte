package uebung3.voronoi;

import java.awt.Graphics;

import geometry.Point;

public class Edge {
	Point start;
	Point end;
	Point regionLeft;
	Point regionRight;
	Edge nextLeft;
	Edge nextRight;
	boolean cut;
	
	public Edge(Point start, Point end, Point left, Point right, Edge leftEdge, Edge rightEdge) {
		this.start=start;
		this.end=end;
		regionLeft=left;
		regionRight=right;
		nextLeft=leftEdge;
		nextRight=rightEdge;
		cut = false;
	}
	
	public Edge(Edge e) {
		this.start = new Point(e.start.x, e.start.y);
		this.end = new Point(e.end.x, e.end.y);
		this.regionLeft = e.regionLeft;
		this.regionRight = e.regionRight;
		this.nextLeft = null;
		this.nextRight = null;
		cut = false;
	}
	
	public Point getStart() {
		return start;
	}
	public Point getEnd() {
		return end;
	}
	
	// Remember: coordinates root is top-left window corner
	
	public Point getRightEnd() {
		return end.x > start.x ? end : start;
	}
	public Point getLeftEnd() {
		return end.x < start.x ? end : start;
	}
	
	public Point getUpperEnd() {
		return end.y > start.y ? start : end;
	}
	public Point getLowerEnd() {
		return end.y > start.y ? end : start;
	}
	
	public void setRightEnd(Point p) {
		if (end.x > start.x) end = p;
		else start = p;
	}
	public void setLeftEnd(Point p) {
		if (end.x < start.x) end = p;
		else start = p;
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
		
		// special case: one edge goes through start / end vertex of the other
		double ye1_1 = a1*x3 + b1;
		double ye1_2 = a1*x4 + b1;
		double ye2_1 = a2*x1 + b2;
		double ye2_2 = a2*x1 + b2;
		if (ye2_1 == y1 || ye2_2 == y1) return new Point(x1,y1); 
		if (ye2_1 == y2 || ye2_2 == y2) return new Point(x2,y2);
		if (ye1_1 == y3 || ye1_2 == y3) return new Point(x3,y3);
		if (ye1_1 == y4 || ye1_2 == y4) return new Point(x4,y4);

		
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
	
	public void drawEdge(Graphics g) {
		g.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
	}
	
	public String toString() {
		return this.start.toString() + " -> " + this.end.toString();
	}
}
