package uebung3.voronoi;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		Model m = new Model();
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View(m);
					Controller c = new Controller(frame, m);
					frame.setVisible(true);
					frame.drawPoints(frame.getCanvas().getGraphics());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	
	}

}
