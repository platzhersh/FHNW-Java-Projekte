package uebung3.voronoi;

import geometry.Point;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Debug {

		
	public void paintAreas(Canvas canvas, List<Point> points){
		
		Random rand = new Random();
		Graphics gr = canvas.getGraphics();
		
		// define random colors for each area
		List<Color> colors = new LinkedList<Color>();
		for (Point p : points) {
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			colors.add(new Color(r, g ,b));
		}
		
		for (int i = 0; i < canvas.getWidth(); i++) {
			for (int j = 0; j < canvas.getHeight(); j++) {
				
				double min = Double.MAX_VALUE;
				int minID = 0;
				for (int k = 0; k < points.size(); k++) {
					double dist = Math.abs(calcDistance(new Point(i,j), points.get(k)));
					if (dist < min) {
						min = dist;
						minID = k;
					}
				}
				gr.setColor(colors.get(minID));
				gr.drawRect(i, j, 1, 1);
			}
		}
	}
	
	public double calcDistance(Point a, Point b) {
		return Math.sqrt(Math.pow((a.x - b.x), 2)+Math.pow((a.y - b.y),2));
	}
	
}
