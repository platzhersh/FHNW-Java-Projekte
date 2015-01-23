package ch.fhnw.depa;

import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import ch.fhnw.depa.states.AbstractImageLoadedState;
import ch.fhnw.depa.states.ImageLoadedState;
import ch.fhnw.depa.states.ImageNotLoadedState;

public class ImageProxy implements Icon {

	volatile ImageIcon imageIcon;
	final URL imageURL;
	
	// states
	AbstractImageLoadedState state;
	public final ImageLoadedState imageLoadedState = new ImageLoadedState(this);
	public final ImageNotLoadedState imageNotLoadedState = new ImageNotLoadedState(this);
	
	// constructor 
	
	public ImageProxy(URL url) {
		imageURL = url;
		state = imageNotLoadedState;
	}
	
	// getter & setter methods
	
	public URL getImageUrl() {
		return imageURL;
	}

	public int getIconWidth() {
		return state.getIconWidth();
	}
	
	public int getIconHeight() {
		return state.getIconHeight();
	}
	
	public synchronized void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}
	
	public synchronized ImageIcon getImageIcon() {
		return imageIcon;
	}
	
	public void setState(AbstractImageLoadedState s) {
		state = s;
	}
	
	// other methods
	
	public void paintIcon(final Component c, Graphics g, int x, int y) {
		state.paintIcon(c, g, x, y);
	}
	
	
}
