package uebung3.voronoi;

import geometry.JarvisMarch;
import geometry.Point;
import geometry.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Controller {

	private View v;
	private Model model;
	
	public Controller(View view, Model model) {
		this.v = view;
		this.model = model;
		registerListeners();
	}
	
	public void registerListeners() {
		
		v.getCanvas().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				model.points.add(new Point(arg0.getX(), arg0.getY()));
				v.drawPoints(v.getCanvas().getGraphics());
				System.out.println("Median:" + getMedian(model.points));
				divideEtImpera(model.points);
			}
		});
		
	}
	
	
	
	public EdgeList divideEtImpera(List<Point> lp) {
		// base case
		if (lp.size() == 1) return new EdgeList();
		if (lp.size() == 2) {
			
			Vector diff = Vector.diff(lp.get(0), lp.get(1));
			Vector norm = Vector.norm(diff);
			// Mittelsenkrechte definiert durch Hälfte des Verbindungsvektors zwischen den Punkten und Normalenvektor darauf
			Point m = new Point(lp.get(0).x + diff.getX()/2, lp.get(0).y + diff.getY()/2);
			Point p1 = new Point(m.x-norm.getX()*1000,m.y-norm.getY()*1000);
			Point p2 = new Point(m.x+norm.getX()*1000,m.y+norm.getY()*1000);
			
			
			v.getCanvas().getGraphics().drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
			v.repaint();
			
			// TODO: check left / right maybe with ccw?
			VoronoiRegion vrLeft = new VoronoiRegion(lp.get(0));
			VoronoiRegion vrRight = new VoronoiRegion(lp.get(1));
			model.regions.add(vrLeft);
			model.regions.add(vrRight);
			
			// not really necessary
			Edge e1 = new Edge(p1, p2, vrLeft , vrRight, null, null);
			/*
			vrLeft.edges.add(e1);
			vrRight.edges.add(e1);
			*/
			
			return new EdgeList(e1);
		}
		
		// divide
		int spl = getSplitIndex(lp);
		ArrayList<Point> sub1 = new ArrayList<Point>(lp.subList(0, spl));
		ArrayList<Point> sub2 = new ArrayList<Point>(lp.subList(spl, lp.size()));
		
		EdgeList edges1 = divideEtImpera(sub1);
		EdgeList edges2 = divideEtImpera(sub2);
		
		// conquer
		/* convexHull - nicht nötig, einfach Punkt mit kleinstem y aus beiden Listen nehmen
		Point[] p = new Point[lp.size()]; 
		p = lp.toArray(p);
		int h = JarvisMarch.computeHull(p);
		
		for( int i=0; i<h; i++)
            System.out.println("Convex hull point Nr. "+(i+1)+" ("+p[i].x+" , "+p[i].y+")"); 
		*/
		
		// sort points ascending by their y-coordinate
		sub1.sort((p1,p2) -> Double.compare(p1.y,p2.y));
		sub2.sort((p1,p2) -> Double.compare(p1.y,p2.y));
		
		Graphics g = v.getCanvas().getGraphics();
		
		
		// obere Tangentialpunkte (Startpunkt um K zu berechnen)
		Point tp1_top = new Point(sub1.get(0).x, sub1.get(0).y);
		Point tp2_top = new Point(sub2.get(0).x, sub2.get(0).y);
		// untere Tangentialpunkte (Endpunkt um K zu berechnen)
		Point tp1_bottom = new Point(sub1.get(sub1.size()-1).x, sub1.get(sub1.size()-1).y);
		Point tp2_bottom = new Point(sub2.get(sub2.size()-1).x, sub2.get(sub2.size()-1).y);
					
		Vector diff = Vector.diff(tp1_top, tp2_top);
		Vector norm = Vector.norm(diff);
		
		Point k = new Point(tp1_top.x+diff.getX()/2+norm.getX()*1000, tp1_top.y+diff.getY()/2+norm.getY()*1000) ;
		Point m = new Point(tp1_top.x+diff.getX()/2, tp1_top.y+diff.getY()/2);
		Point m2 = new Point(tp1_top.x+diff.getX()/2-norm.getX()*1000, tp1_top.y+diff.getY()/2-norm.getY()*1000);
		
		Edge ke = new Edge(k,m2, new VoronoiRegion(tp2_top), new VoronoiRegion(tp1_top), null, null);
		
		g.setColor(Color.GREEN);
		g.drawLine((int) tp1_top.x, (int) tp1_top.y, (int) tp2_top.x, (int) tp2_top.y);
		g.setColor(Color.RED);
		g.drawLine((int)k.x, (int)k.y, (int)m.x, (int)m.y);
		System.out.println("Draw Line");
		
		while (tp1_top != tp1_bottom && tp2_top != tp2_bottom) {
			Point s1 = new Point(Double.MAX_VALUE,Double.MAX_VALUE);
			Point s2 = new Point(Double.MAX_VALUE,Double.MAX_VALUE);;
			
			// Todo: make more efficient
			if (edges1.head != null) {
				if (edges1.head.regionLeft.p.equals(tp1_top)) {
					Point s = Edge.interceptionPoint(edges1.head, ke);
					if (null != s) {
						s1 = s.y < s1.y ? s : s1;
					}
				}
				if (edges1.head.regionRight.p.equals(tp1_top)) {
					Point s = Edge.interceptionPoint(edges1.head, ke);
					if (null != s) {
						s1 = s.y < s1.y ? s : s1;
					}
				}

			}
			if (edges2.head != null) {
				if (edges2.head.regionLeft.p.equals(tp2_top)) {
					Point s = Edge.interceptionPoint(edges2.head, ke);
					if (null != s) {
						s1 = s.y < s1.y ? s : s1;
					}
				}
				if (edges2.head.regionRight.p.equals(tp2_top)) {
					Point s = Edge.interceptionPoint(edges2.head, ke);
					if (null != s) {
						s1 = s.y < s1.y ? s : s1;
					}
				}

			}
			
//			for (VoronoiRegion vr : model.regions) {
//				if (vr.p.x == tp1_top.x && vr.p.y == tp1_top.y) {
//					for (Edge e : vr.edges) {
//						
//					}
//				}
//				if (vr.p.x == tp2_top.x && vr.p.y == tp2_top.y) {
//					for (Edge e : vr.edges) {
//						Point s = Edge.interceptionPoint(e, ke);
//						if (null != s) {
//							s2 = s.y < s2.y ? s : s1;
//						}
//					}
//				}
//				
//				
//			}
			
			Point smin = s1.y > s2.y ? s2 : s1;
			g.setColor(Color.BLUE);
			g.drawLine((int)k.x, (int)k.y, (int)smin.x, (int)smin.y);
			v.repaint();
			System.out.println("Going into loop");
			k = smin;
			tp1_top = tp1_bottom;
			
		}
		
		
		
		return null;
	}
	
	
	
	public static double getMedian(List<Point> l) {
		l.sort((p1,p2) -> Double.compare(p1.x,p2.x));
		int s = l.size();
		if (s > 0) {
			int i = s-1;
			if (s % 2 == 0) return (l.get(i/2).x + l.get(i/2+1).x)/2;
			else return l.get(i/2).x;
		}
		return 0;
	}
	
	public static int getSplitIndex(List<Point> l) {
		int s = l.size();
		return s > 0 ? (s-1)/2 : 0;
	}
	
	
	
}
