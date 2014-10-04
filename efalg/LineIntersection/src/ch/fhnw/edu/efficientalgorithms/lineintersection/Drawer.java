package ch.fhnw.edu.efficientalgorithms.lineintersection;

import java.awt.Color;
import java.awt.Shape;

/**
 * A drawer paints the added objects.
 * 
 * @author Martin Schaub
 */
public interface Drawer {

	/**
	 * Adds a new shape to the drawer to be drawn. The color of the shape will be black.
	 * 
	 * @param shape shape to add.
	 */
	void addDrawable(Shape shape);

	/**
	 * Adds a new shape to the drawer to be drawn
	 * 
	 * @param shape shape to add.
	 * @param color defines the color of the new shape
	 */
	void addDrawable(Shape shape, Color color);

	/**
	 * Removes a object to draw.
	 * 
	 * @param shape the shape which is not longer to be used.
	 */
	void removeDrawable(Shape shape);

	/**
	 * Removes all shapes, so no more objects are going to be drawn.
	 */
	void clearAllDrawables();
}
