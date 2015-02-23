package as;

import javafx.scene.paint.Color;

/** Abstracts the mechanics for painting colored dots to the screen. */
public interface PixelPainter {
	void paint(int x, int y, Color color);
}