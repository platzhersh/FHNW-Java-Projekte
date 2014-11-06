package uebung3.voronoi;

import geometry.Point;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;


public class View extends JFrame {
	
	Model m;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5402716063017734083L;
	private Canvas canvas;

	/**
	 * Create the frame.
	 */
	public View(Model model) {
		setTitle("Voronoi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		canvas = new Canvas();
		getContentPane().add(canvas);
		m = model;
	}

	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void drawPoints(Graphics g) {
		g.setColor(Color.BLACK);
		for (Point p : m.getPoints()) {
			g.drawOval((int)p.x-2, (int)p.y-2, 4, 4);
			g.fillOval((int)p.x-2, (int)p.y-2, 4, 4);
		}
		this.repaint();
	}
}
