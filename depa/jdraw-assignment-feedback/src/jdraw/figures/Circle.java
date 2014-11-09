package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import jdraw.framework.FigureEvent;


public class Circle extends AbstractRectangularFigure {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8389375971667257198L;



	/***
     * 
     * @param x the x coordinate of the upper left corner of the oval to be drawn.
     * @param y the y coordinate of the upper left corner of the oval to be drawn.
     * @param width the width of the oval to be drawn.
     * @param height the height of the oval to be drawn.
     */
	public Circle(int x, int y, int diameter) {   	
    	rectangle = new Rectangle(x, y, diameter, diameter);
    }
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	// XXX in dieser Methode steckt das sonderbare Kreis-Verhalten (tritt beim resizing über einen Handle auf aber
	//     auch beim Aufziehen einer neuen Figur. 
	@Override
	public void setBounds(Point origin, Point corner) {
		java.awt.Rectangle original = new java.awt.Rectangle(rectangle);
		
		
		int d1 = corner.x - origin.x;
		int d2 = corner.y - origin.y;
		// XXX das ist für mich auch sonderbar, d.h hier wird das rectangle-Objekt geändert, aber unten rufen Sie dann ja 
		//     doch wieder rectangle.setFrameFromDoagonal auf. Hier würde ich daher zwei Hilfsvariablen (lokal) verwenden.
		
		// XXX Sie setzen hier width und height auf denselben Wert. Aber achtung, sie müssen das Vorzeichen von d1/d2
		//     beibehalten!
		
		// XXX und weil sie hier das Maximum nehnen können sie dann den Kreis über den 	Nord/Ost/West/Süd-Handle nicht 
		//     kleiner machen!!!
		rectangle.width = Math.abs(d1) < Math.abs(d2) ? d2 : d1;
		rectangle.height = rectangle.width;
		
		corner.x = origin.x + rectangle.width;
		corner.y = origin.y + rectangle.height;
		
		rectangle.setFrameFromDiagonal(origin, corner);
		if(!original.equals(rectangle)) {
			propagateFigureEvent(new FigureEvent(this));
		}
	}

}
