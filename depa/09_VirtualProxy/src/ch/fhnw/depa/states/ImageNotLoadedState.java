package ch.fhnw.depa.states;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import ch.fhnw.depa.ImageProxy;

public class ImageNotLoadedState extends AbstractImageLoadedState {

	Thread retrievalThread;
	boolean retrieving = false;
	
	public ImageNotLoadedState(ImageProxy c) {
		super(c);
	}

	@Override
	public int getIconWidth() {
		return 800;
	}

	@Override
	public int getIconHeight() {
		return 800;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.drawString("Loading CD cover, please wait...", x+300, y+190);
		if (!retrieving) {
			retrieving = true;
			
			retrievalThread = new Thread(new Runnable() {
				public void run() {
					try {
						context.setImageIcon(new ImageIcon(context.getImageUrl(), "CD Cover"));
						c.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			retrievalThread.start();
		}
	}

	
}