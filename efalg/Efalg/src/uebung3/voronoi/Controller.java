package uebung3.voronoi;

import geometry.Point;
import geometry.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller {

	private View v;
	private Model model;
	private Debug debug;
	
	public Controller(View view, Model model) {
		this.v = view;
		this.model = model;
		this.debug = new Debug();
		registerListeners();
	}
	
	public void registerListeners() {
		
		v.addComponentListener(new ComponentAdapter() {
			@Override
		    public void componentResized(ComponentEvent e)
		    {
		        v.getCanvas().repaint();
		    }
		});
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
			public void mouseEntered(MouseEvent evt) {
				// TODO
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Graphics g = v.getCanvas().getGraphics();
				model.points.add(new Point(arg0.getX(), arg0.getY()));
				
				debug.paintAreas(v.getCanvas(), model.points);
				v.drawPoints(g);
				
				System.out.println("Median:" + getMedian(model.points));
				divideEtImpera(model.points);
			}
		});
		v.getCanvas().addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent evt) {
				int x = evt.getPoint().x;
				int y = evt.getPoint().y;
				v.setPosLbl("("+x+", "+y+")");				
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	
	public EdgeList divideEtImpera(List<Point> lp) {
		Graphics g = v.getCanvas().getGraphics();
		v.validate();
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
			Point vrLeft = new Point(lp.get(0));
			Point vrRight = new Point(lp.get(1));
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
		System.out.println("SplitIndex: "+ spl);
		ArrayList<Point> sub1 = new ArrayList<Point>(lp.subList(0, spl));
		ArrayList<Point> sub2 = new ArrayList<Point>(lp.subList(spl, lp.size()));
		
		EdgeList edges1 = divideEtImpera(sub1);
		EdgeList edges2 = divideEtImpera(sub2);
		
		// the Edgelist to pass on
		EdgeList el = new EdgeList();
		
		// conquer
		
		// sort points ascending by their y-coordinate
		sub1.sort((p1,p2) -> Double.compare(p1.y,p2.y));
		sub2.sort((p1,p2) -> Double.compare(p1.y,p2.y));
				
		
		Point tp1_top = new Point(sub1.get(0).x, sub1.get(0).y);
		Point tp2_top = new Point(sub2.get(0).x, sub2.get(0).y);
		
		// distance vector between tp1_top and tp2_top
		Vector diff = Vector.diff(tp1_top, tp2_top);
		// norm vector on distance vector
		Vector norm = Vector.norm(diff);
					
		// point on perpendicular bisector of tp1_top and tp2_top (very far away)
		Point kAway = new Point(tp1_top.x+diff.getX()/2+norm.getX()*1000, tp1_top.y+diff.getY()/2+norm.getY()*1000);
		Point k = new Point(kAway);
		
		// untere Tangentialpunkte (Endpunkt um K zu berechnen)
		Point tp1_bottom = new Point(sub1.get(sub1.size()-1).x, sub1.get(sub1.size()-1).y);
		Point tp2_bottom = new Point(sub2.get(sub2.size()-1).x, sub2.get(sub2.size()-1).y);
		
		// index i of P1 and P2 points
		int p1Index = 0;
		int p2Index = 0;
		
		List<Edge> kEdges = new LinkedList<Edge>();
		boolean goOn = true;
		//while (!tp1_top.equals(tp1_bottom) || !tp2_top.equals(tp2_bottom)) {
		while(goOn) {
			Point s1 = new Point(Double.MAX_VALUE,Double.MAX_VALUE);
			Point s2 = new Point(Double.MAX_VALUE,Double.MAX_VALUE);
			
			System.out.println("tp1_top: "+tp1_top.toString());
			System.out.println("tp2_top: "+tp2_top.toString());
			
			// distance vector between tp1_top and tp2_top
			diff = Vector.diff(tp1_top, tp2_top);
			// norm vector on distance vector
			norm = Vector.norm(diff);
			
			// interception point of perpendicular bisector of tp1_top and tp2_top and distance vector between them
			Point m = new Point(tp1_top.x+diff.getX()/2, tp1_top.y+diff.getY()/2);
			// point on perpendicular bisector of tp1_top and tp2_top (very far away)
			Point m2 = new Point(tp1_top.x+diff.getX()/2-norm.getX()*1000, tp1_top.y+diff.getY()/2-norm.getY()*1000);
			
			kAway = new Point(tp1_top.x+diff.getX()/2+norm.getX()*1000, tp1_top.y+diff.getY()/2+norm.getY()*1000);
			Edge ke = new Edge(kAway,m2, new Point(tp2_top), new Point(tp1_top), null, null);
			kEdges.add(ke);
			
			g.setColor(Color.GREEN);	// Tangente
			g.drawLine((int) tp1_top.x, (int) tp1_top.y, (int) tp2_top.x, (int) tp2_top.y);
			g.setColor(Color.RED);		// m durch Tangente
			ke.drawEdge(g);
			
			// used to find interception points 
			Edge kLast = new Edge(null,null,null,null,null,null);
			
			// Todo: go through all edges and fix conditions
			for (Edge e : edges1.edges) {
				//if (e.regionRight.equals(tp1_top) || e.regionRight.equals(tp1_top)) {
					Point s = Edge.interceptionPoint(e, ke);
					if (null != s && s.y >= k.y && s.y < s1.y) {
						kLast = e; kLast.cut = true;
						s1 = s;
					}
				//}
			}

			for (Edge e : edges2.edges) {
				//if (e.regionRight.equals(tp2_top) || e.regionRight.equals(tp2_top)) {
					Point s = Edge.interceptionPoint(e, ke);
					if (null != s && s.y >= k.y && s.y < s2.y) {
						kLast = e; kLast.cut = true;
						s2 = s;
					}
				//}
			}
			
			
			// first Interception Point of ke with Voronoi Edge
			Point smin;
			Edge ki = new Edge(ke);

			// get next Point
			if (s1.y == s2.y && s1.y == Double.MAX_VALUE) {
				System.out.println("kein Schnittpunkt - end");
				// nonetheless: have to paint the line because it is the final one
				ki.start = new Point(k.x,k.y);
				el.addEdge(ki);
				g.setColor(Color.PINK);	
				ki.drawEdge(g);
				goOn = false;
				break;
			}
			// s1 höher als s2
			if (s1.y < s2.y) {
				smin = s1;
				System.out.println("s1 < s2: " + s1.y + " / " + s2.y);
				//System.out.println("get next tp1_top (s1 size: "+sub1.size()+", i: "+p1Index+")");
				//p1Index++;
				
				System.out.println("switch tp1_top: " + tp1_top.equals(kLast.regionLeft) + " " + tp1_top.equals(kLast.regionRight));
				tp1_top = kLast.regionLeft.equals(tp1_top) ? kLast.regionRight : kLast.regionLeft;
				
				ki.setLeftEnd(new Point(k.x,k.y));
				ki.setRightEnd(new Point(smin.x,smin.y));
				kLast.setRightEnd(new Point(smin.x,smin.y));
				
			// s2 höher als s1
			} else if (s2.y < s1.y){
				smin = s2;
				System.out.println("s1 > s2: " + s1.y + " / " + s2.y);
//				System.out.println("get next tp2_top (s2 size: "+sub2.size()+", i: "+p2Index+")");
//				p2Index++;
//				edges2.edges.remove(kLast);
//				edges2.edges.add(ke);
				
				System.out.println("switch tp2_top: " + tp2_top.equals(kLast.regionLeft) + " " + tp2_top.equals(kLast.regionRight));
				tp2_top = kLast.regionLeft.equals(tp2_top) ? kLast.regionRight : kLast.regionLeft;
				
				ki.setLeftEnd(smin);
				ki.setRightEnd(new Point(k.x,k.y));
				kLast.setLeftEnd(new Point(smin.x,smin.y));
			} else {
				smin= new Point(0,0);
				System.out.println("s1 == s2 aber nicht double.MAX_VALUE sondern " + s1.y + " == " +s2.y);
				ke.drawEdge(g);
				break;
			}
			
			ki.cut = true;

			el.addEdge(ki);
			el.addEdge(kLast);
			// draw K until interception point
			g.setColor(Color.BLUE);	
			ki.drawEdge(g);
			g.setColor(Color.YELLOW);
			kLast.drawEdge(g);
			v.repaint();
			
			k = smin;
			/*
			kLast = new Edge(ki);
			kLast.start.x*=1000;
			kLast.start.y*=1000;
			kLast.end.x*=1000;
			kLast.end.y*=1000;
			*/
			System.out.println("while bedingung: "+ (!tp1_top.equals(tp1_bottom) || !tp2_top.equals(tp2_bottom)));
			
		}
		
		for (Edge e : el.edges) {
			System.out.println(e.toString());
		}
		
		
		return el;
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
		return s > 0 ? s/2 : 0;
	}
	
	
	
}
