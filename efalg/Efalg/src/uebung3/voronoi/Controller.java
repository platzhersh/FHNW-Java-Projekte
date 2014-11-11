package uebung3.voronoi;

import uebung3.voronoi.helpers.Point;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import uebung3.voronoi.helpers.Debug;
import uebung3.voronoi.helpers.Edge;
import uebung3.voronoi.helpers.Vector;

public class Controller {

	private View v;
	private Model model;
	private Debug debug;
	final int factor = 100;
	
	public Controller(View view, Model model) {
		this.v = view;
		this.model = model;
		this.debug = new Debug();
		registerListeners();
	}
	
	public void registerListeners() {
		
		v.btnReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model = new Model();
				v.m = model;
				Graphics g = v.getCanvas().getGraphics();
				v.update(g);
				v.repaint();
				
			}
		});
		v.btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Graphics g = v.getCanvas().getGraphics();
				v.getCanvas().revalidate();
				v.getCanvas().repaint();
				v.getCanvas().update(g);
				
			}
		});
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
				
				System.out.println("Median:" + getMedianX(model.points));
				System.out.println("Average:" + getAverageX(model.points));
				EdgeList l = divideEtImpera(model.points);
				
				for (Edge e : l.edges) {
					g.setColor(Color.CYAN);
					e.drawEdge(g);
				}
				
				System.out.println("------------------------");
				System.out.println();
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
			Point p1 = new Point(m.x-norm.getX()*factor,m.y-norm.getY()*factor);
			Point p2 = new Point(m.x+norm.getX()*factor,m.y+norm.getY()*factor);
			
			v.getCanvas().getGraphics().drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
			v.repaint();
			
			Point vrLeft = new Point(lp.get(0));
			Point vrRight = new Point(lp.get(1));
			//model.regions.add(vrLeft);
			//model.regions.add(vrRight);
			
			// not really necessary
			Edge e1 = new Edge(p1, p2, vrLeft , vrRight);
			/*
			vrLeft.edges.add(e1);
			vrRight.edges.add(e1);
			*/
			
			return new EdgeList(e1);
		}
		
		// divide
		int spl = getSplitIndex(lp);
		//System.out.println("SplitIndex: "+ spl);
		ArrayList<Point> sub1 = new ArrayList<Point>(lp.subList(0, spl));
		ArrayList<Point> sub2 = new ArrayList<Point>(lp.subList(spl, lp.size()));
		
		EdgeList edges1 = divideEtImpera(sub1);
		EdgeList edges2 = divideEtImpera(sub2);
		
		// the Edgelist to pass on with all the edges from previous recursions
		// edges will be trimmed later in the algorithm
		EdgeList el = new EdgeList();
		for (Edge e : edges1.edges) {
			el.addEdge(e);
		}
		for (Edge e : edges2.edges) {
			el.addEdge(e);
		}
		// conquer
		
		// sort points ascending by their y-coordinate
		sub1.sort((p1,p2) -> Double.compare(p1.y,p2.y));
		sub2.sort((p1,p2) -> Double.compare(p1.y,p2.y));
		
		Point next_tp1_top = new Point(sub1.get(0).x, sub1.get(0).y);
		Point next_tp2_top = new Point(sub2.get(0).x, sub2.get(0).y);;
		Point tp1_top = new Point(next_tp1_top);
		Point tp2_top = new Point(next_tp2_top);
		
		// untere Tangentialpunkte (Endpunkt um K zu berechnen)
		Point tp1_bottom= new Point(sub1.get(sub1.size()-1).x, sub1.get(sub1.size()-1).y); 
		Point tp2_bottom= new Point(sub2.get(sub2.size()-1).x, sub2.get(sub2.size()-1).y); 
				
		// distance vector between tp1_top and tp2_top
		Vector diff = Vector.diff(tp1_top, tp2_top);
		// norm vector on distance vector
		Vector norm = Vector.norm(diff);
					
		// point on perpendicular bisector of tp1_top and tp2_top (very far away)
		Point kAway = new Point(tp1_top.x+diff.getX()/2+norm.getX()*factor, tp1_top.y+diff.getY()/2+norm.getY()*factor);
		Point k = new Point(kAway);	
		
		List<Edge> kEdges = new LinkedList<Edge>();
		
		// Beginning of the big loop
		while (!tp1_top.equals(tp1_bottom) || !tp2_top.equals(tp2_bottom)) {
			Point s1 = new Point(Double.MAX_VALUE,Double.MAX_VALUE);
			Point s2 = new Point(Double.MAX_VALUE,Double.MAX_VALUE);
			
			tp1_top = new Point(next_tp1_top);
			tp2_top = new Point(next_tp2_top);
			
			System.out.println("tp1_top: "+tp1_top.toString());
			System.out.println("tp2_top: "+tp2_top.toString());
			
			// distance vector between tp1_top and tp2_top
			diff = Vector.diff(tp1_top, tp2_top);
			// norm vector on distance vector
			norm = Vector.norm(diff);
			
			// interception point of perpendicular bisector of tp1_top and tp2_top and distance vector between them
			Point m = new Point(tp1_top.x+diff.getX()/2, tp1_top.y+diff.getY()/2);
			// point on perpendicular bisector of tp1_top and tp2_top (very far away)
			Point m2 = new Point(tp1_top.x+diff.getX()/2-norm.getX()*factor, tp1_top.y+diff.getY()/2-norm.getY()*factor);
			
			kAway = new Point(tp1_top.x+diff.getX()/2+norm.getX()*factor, tp1_top.y+diff.getY()/2+norm.getY()*factor);
			Edge ke = new Edge(kAway,m2, new Point(tp2_top), new Point(tp1_top));
			kEdges.add(ke);
			
			g.setColor(Color.GREEN);	// Strecke zwischen tp1_top und tp2_top
			g.drawLine((int) tp1_top.x, (int) tp1_top.y, (int) tp2_top.x, (int) tp2_top.y);
			g.setColor(Color.RED);		// Mittelsenkrechte zwischen tp1_top und tp2_top
			ke.drawEdge(g);
			
			// zum zwischenspeichern der geschnittenen Edge Referenz 
			Edge kLast = new Edge(null,null);
			
			// Todo: go through all edges and fix conditions
			for (Edge e : edges1.edges) {
				if (e.getRegionLeft().equals(tp1_top) || e.getRegionRight().equals(tp1_top)) {
					Point s = Edge.interceptionPoint(e, ke);
					if (null != s && s.y >= k.y && s.y < s1.y) {
						kLast = e; kLast.cut = true;
						s1 = s;
					}
				}
			}

			for (Edge e : edges2.edges) {
				if (e.getRegionRight().equals(tp2_top) || e.getRegionLeft().equals(tp2_top)) {
					Point s = Edge.interceptionPoint(e, ke);
					if (null != s && s.y >= k.y && s.y < s2.y) {
						kLast = e; kLast.cut = true;
						s2 = s;
					}
				}
			}
			
			
			// first Interception Point of ke with Voronoi Edge
			Point smin;

			// get next Point
			if (s1.y == s2.y && s1.y == Double.MAX_VALUE) {
				System.out.println("kein Schnittpunkt - break");
				// nonetheless: have to paint the line because it is the final one
				ke.setStart(new Point(k.x,k.y));
				g.setColor(Color.PINK);	
				ke.drawEdge(g);
				el.addEdge(ke);
				break;
			}
			// s1 höher als s2 -> Schnittpunkt in P1
			if (s1.y < s2.y) {
				smin = s1;
				if (ke.getRegionLeft().equals(tp1_top)) System.err.println("Something's wrong! " +ke.toString());
				
				next_tp1_top = kLast.getRegionLeft().equals(tp1_top) ? kLast.getRegionRight() : kLast.getRegionLeft();
				System.out.println("switch tp1_top");
				
				ke.setStart(new Point(k.x,k.y));
				ke.setEnd(new Point(smin.x,smin.y));
				kLast.setStart(kLast.getLeftEnd());
				kLast.setEnd(new Point(smin.x,smin.y));
				
				
			// s2 höher als s1 -> Schnittpunkt in P2
			} else if (s2.y < s1.y){
				smin = s2;
				System.out.println("smin.x = " + smin.x + " rRight.x = " + kLast.getRegionRight().x);
				// check for special case: neighbor of p1 left of ke
				if (kLast.getRegionRight().x < smin.x) {
					System.out.println("switch tp2_top special");
					next_tp2_top = kLast.getRegionLeft().equals(tp2_top) ? kLast.getRegionRight() : kLast.getRegionLeft();
					
					ke.setStart(new Point(k.x,k.y));
					ke.setEnd(new Point(smin));
					
					kLast.setStart(kLast.getStart());
					kLast.setEnd(new Point(smin.x,smin.y));
				} else {
					System.out.println("switch tp2_top");
					next_tp2_top = kLast.getRegionLeft().equals(tp2_top) ? kLast.getRegionRight() : kLast.getRegionLeft();
					
					ke.setStart(new Point(smin));
					ke.setEnd(new Point(k.x,k.y));
					
					kLast.setStart(new Point(smin.x,smin.y));
					kLast.setEnd(kLast.getRightEnd());
				}
				
				
			} else {
				smin= new Point(0,0);
				System.out.println("s1 == s2 aber nicht double.MAX_VALUE sondern " + s1.y + " == " +s2.y);
				ke.drawEdge(g);
				break;
			}
			
			ke.cut = true;
			el.addEdge(ke);
			// draw K until interception point
			g.setColor(Color.BLUE);	
			ke.drawEdge(g);
			g.setColor(Color.YELLOW);
			kLast.drawEdge(g);
			v.repaint();
			
			k = smin;
			
		}
		
		System.out.println("---- next Rec level, return " + el.size() +" elements --------");	
		
		return el;
	}
	
	
	public static double getAverageX(List<Point> l) {
		double sum = 0;
		for (Point p : l) {
			sum += p.x;
		}
		return sum / l.size();
	}
	
	public static double getMedianX(List<Point> l) {
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
