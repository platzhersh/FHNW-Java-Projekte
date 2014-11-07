package uebung3.voronoi;

import geometry.Point;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

public class Main {

	public static void main(String[] args) {
		Model m = new Model();
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View(m);
					Controller c = new Controller(frame, m);
					frame.setVisible(true);
					Graphics g = frame.getCanvas().getGraphics();
					g.setColor( Color.WHITE); 
					g.fillRect (0, 0, frame.getWidth(), frame.getHeight());
					frame.drawPoints(g);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
//		Point p = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
//		System.out.println(p.x == p.y);
	
	}

}
