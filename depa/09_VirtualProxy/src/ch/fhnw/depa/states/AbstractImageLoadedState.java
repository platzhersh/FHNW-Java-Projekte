package ch.fhnw.depa.states;

import java.awt.Component;
import java.awt.Graphics;

import ch.fhnw.depa.ImageProxy;

public abstract class AbstractImageLoadedState {
	
	static ImageProxy context;
	
	// constructor
	
	public AbstractImageLoadedState(ImageProxy c) {
		context = c;
	}
	
	// getter & setter
	
	public abstract int getIconWidth();
	public abstract int getIconHeight();
	
	public abstract void paintIcon(final Component c, Graphics g, int x, int y);
}
