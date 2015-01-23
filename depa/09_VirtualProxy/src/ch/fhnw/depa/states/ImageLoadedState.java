package ch.fhnw.depa.states;

import java.awt.Component;
import java.awt.Graphics;

import ch.fhnw.depa.ImageProxy;

public class ImageLoadedState extends AbstractImageLoadedState {

	public ImageLoadedState(ImageProxy c) {
		super(c);
	}

	@Override
	public int getIconWidth() {
		return context.getImageIcon().getIconWidth();
	}

	@Override
	public int getIconHeight() {
		return context.getImageIcon().getIconHeight();
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		context.getImageIcon().paintIcon(c, g, x, y);
		
	}

}
