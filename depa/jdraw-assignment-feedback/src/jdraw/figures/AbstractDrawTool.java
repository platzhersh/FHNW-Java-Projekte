package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

public abstract class AbstractDrawTool implements DrawTool {
	
	
	/**
	 * Temporary variable. During rectangle creation (during a
	 * mouse down - mouse drag - mouse up cycle) this variable refers
	 * to the new rectangle that is inserted.
	 */
	// XXX ich finde es nicht schön wenn Felder protected deklariert werden . Ich würde diese Variable als
	//     privat deklarieren und über eine (abstrakte) Methode setzen, d.h. das mouseDown gehört auch in 
	//     diese Klasse rein.
	protected AbstractFigure fig;
	
	/** 
	 * the image resource path. 
	 */
	// XXX auch dieses Feld würde ich privat deklarieren. Dann muss auch die Methode getIcon hier in dieser
	//     klasse definiert werden. Den Filenamen könnte man mit dem Konstruktor übergeben.
	protected static final String IMAGES = "/images/";
	
	/**
	 * The context we use for drawing.
	 */
	// XXX auch diese Variable würde ich privat definieren (und final) und über den Konstruktor setzen.
	protected DrawContext context;
	
	/**
	 * The context's view. This variable can be used as a shortcut, i.e.
	 * instead of calling context.getView().
	 */
	// XXX würde ich auch privat (und final) deklarieren; ist einfach möglich wenn das mouseDown auch in dieser
	//     Klasse deklariert ist.
	protected DrawView view;
	
	/**
	 * Temporary variable.
	 * During rectangle creation this variable refers to the point the
	 * mouse was first pressed.
	 */
	// XXX auch das gehört zu ihrer Implementierung von mouseDown/mouseDrag/mouseUp. Eigentlich unschön dass das
	//     protected sein muss, so ein Implementationsdetail gehört in eine einzige Klasse.
	protected Point anchor = null;
	
	@Override
	public void deactivate() {
		this.context.showStatusText("");
		
	}
	
	@Override
	public void activate() {
		this.context.showStatusText(this.getName() + " Mode");
	}
	
	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		createHandles();
		fig = null;
		anchor = null;
		this.context.showStatusText(getName() + " Mode");
	}
	
	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		fig.setBounds(anchor, new Point(x, y));
		java.awt.Rectangle r = fig.getBounds();
		this.context.showStatusText("w: " + r.width + ", h: " + r.height);
	}
	
	abstract public void createHandles();
}
