package uebung3.voronoi;

import geometry.Point;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JButton;


public class View extends JFrame {
	
	Model m;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5402716063017734083L;
	private Canvas canvas;
	private JLabel lblPos;
	public JButton btnReset;
	public JButton btnRefresh;

	/**
	 * Create the frame.
	 */
	public View(Model model) {
		setTitle("Voronoi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		canvas = new Canvas();
		getContentPane().add(canvas);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		lblPos = new JLabel("Pos");
		panel.add(lblPos);
		
		btnReset = new JButton("Reset");
		
		panel.add(btnReset);
		
		btnRefresh = new JButton("Refresh");
		//panel.add(btnRefresh);
		m = model;
	}

	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setPosLbl(String text) {
		lblPos.setText(text);
	}
	
	public void drawPoints(Graphics g) {
		g.setColor(Color.BLACK);
		for (Point p : m.getPoints()) {
			g.drawOval((int)p.x-3, (int)p.y-3, 6, 6);
			g.fillOval((int)p.x-3, (int)p.y-3, 6, 6);
		}
		//this.repaint();
	}
	/*
	public void drawEdges(Graphics g) {
		g.setColor(Color.BLACK);
		for (Edge e : m.getEdges()) {
			g.drawLine((int)e.start.x, (int)e.start.y, (int)e.end.x, (int)e.end.y);
		}
		this.repaint();
	}*/
	
	
	public void paintComponent(Graphics g) {
		g.setColor( Color.white ); 
		g.fillRect (0, 0, getWidth(), getHeight());
		
		drawPoints(g);
		//drawEdges(g);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(400, 300);
	}
	
	
}
