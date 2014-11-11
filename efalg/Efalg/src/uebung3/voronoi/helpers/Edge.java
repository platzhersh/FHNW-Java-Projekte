package uebung3.voronoi.helpers;

import java.awt.Graphics;

import uebung3.voronoi.helpers.Point;

public class Edge {
	private Point start, end, regionLeft, regionRight;
	public Edge nextLeft;
	public Edge nextRight;
	
	public boolean cut;
	
	
	public Edge(Point p1, Point p2) {
		if ( null != p1 && null != p2) {
			if (p2.y < p1.y) {
				this.start = new Point(p2);
				this.end = new Point(p1);
			} else {
				this.start = new Point(p1);
				this.end = new Point(p2);
			}
		}
	}
	
	// always set start as the point with the lower y coordinate
	public Edge(Point p1, Point p2, Point n1, Point n2) {
		
		this(p1,p2);
		
		regionLeft = n1;
		regionRight = n2;
		checkRegions();
		
		cut = false;
	}
	
	public Edge(Edge e) {
		this(e.start, e.end, e.regionLeft, e.regionRight);
		cut = false;
	}
	
	
	/* --------- Setter & Getter --------------------- */
	
	public Point getStart() {
		return start;
	}
	public Point getEnd() {
		return end;
	}
	
	public void setStart(Point p) {
		if (p.y < end.y) start = new Point(p);
		else {
			start = new Point(end);
			end = new Point(p);
		}
		// check if regions are still correct
		checkRegions();
	}
	
	public void setEnd(Point p) {
		if (p.y > end.y) end = new Point(p);
		else {
			end = new Point(start);
			start = new Point(p);
		}
		// check if regions are still correct
		checkRegions();
	}
	
	public Point getRegionLeft() {
		return this.regionLeft;
	}
	
	public Point getRegionRight() {
		return this.regionRight;
	}
	
	public void setNeighbours(Point n1, Point n2) {
		regionLeft = n1;
		regionRight = n2;
		checkRegions();
	}
	
	
	
	// Remember: coordinates root is top-left window corner
	
	public Point getRightEnd() {
		return end.x > start.x ? end : start;
	}
	public Point getLeftEnd() {
		return end.x < start.x ? end : start;
	}
	
	public void setRightEnd(Point p) {
		if (end.x > start.x) setEnd(new Point(p));
		else setStart(new Point(p));
		
		// check if regions are still correct
		checkRegions();
	}
	public void setLeftEnd(Point p) {
		if (end.x < start.x) setEnd(new Point(p));
		else setStart(new Point(p));
		
		// check if regions are still correct
		checkRegions();
	}
	
	// check regions
	private void checkRegions() {
		if (null != regionLeft && null != regionRight) {
			Point n1 = new Point(regionLeft);
			Point n2 = new Point(regionRight);
			
			if (Vector.ccw(start, end, n1) == 1) {
				this.regionLeft = n1;
				this.regionRight = n2;
			} else if (Vector.ccw(start, end, n1) == -1){
				this.regionLeft = n1;
				this.regionRight = n2;
			} else {
				System.out.println("Point is ON the edge!");
			}
		}
	}
	
	// source: http://stackoverflow.com/questions/16314069/calculation-of-intersections-between-line-segments
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
		
		// equations
		double a1 = (y2-y1)/(x2-x1);
		double b1 = y1 - a1*x1;
		double a2 = (y4-y3)/(x4-x3);
		double b2 = y3 - a2*x3;
		
		// check if vertical
		boolean e1Vert = e1.getStart().x == e1.getEnd().x;
		boolean e2Vert = e2.getStart().x == e2.getEnd().x;
		if (e1Vert || e2Vert) {
			double y0 = 0, x0 = 0;
			if (e1Vert) y0 = a2*x1+b2; x0 = x1;
			if (e2Vert) y0 = a1*x3+b1; x0 = x3;
			
			// check if y0 is element of both edges
			if (Math.min(y1, y2) < y0 && y0 < Math.max(y1, y2) &&
					Math.min(y3, y4) < y0 && y0 < Math.max(y3, y4)
				) {
				return new Point(x0,y0);
			} else {
				return null;
			}
		}
		
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
		return this.start.toString() + " -> " + this.end.toString() + " rLeft: " + this.regionLeft + " rRight: " + this.regionRight;
	}
}
