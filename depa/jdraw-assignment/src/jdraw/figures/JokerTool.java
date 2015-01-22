package jdraw.figures;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jdraw.framework.DrawContext;

public class JokerTool extends AbstractDrawTool {

	AbstractDrawTool toDecorate;
	
	public JokerTool(DrawContext context) {
		super();
		this.context = context;
		this.view = context.getView();
	}
	
	private void setTool() {
		if (toDecorate == null) {
			Object[] possibilities = {"Rectangle", "Ellipse", "Circle"};

			String s = (String)JOptionPane.showInputDialog(new JFrame(),
			                    "Choose the tool you would like to use:\n",
			                    "Joker Tool",
			                    JOptionPane.PLAIN_MESSAGE,
			                    getIcon(),
			                    possibilities,
			                    "Rectangle");

			//If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				switch(s) {
					case "Rectangle":
						toDecorate = new RectTool(context);
						break;
					case "Circle":
						toDecorate = new CircleTool(context);
						break;
					case "Ellipse":
						toDecorate = new EllipseTool(context);
						break;
				}
			}

		}
	}
	
	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		toDecorate.mouseDown(x, y, e);
	}
	
	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		toDecorate.mouseDrag(x, y, e);
	}
	
	@Override
	public void mouseUp(int x, int y, MouseEvent e) { 
		toDecorate.mouseUp(x, y, e);
	}
	
	

	@Override
	public Cursor getCursor() {
		return toDecorate.getCursor();
	}

	@Override
	public Icon getIcon() {
		if (toDecorate != null) {
			return toDecorate.getIcon();
		}
		return new ImageIcon(getClass().getResource(IMAGES + "joker.png"));
	}

	@Override
	public String getName() {
		if (toDecorate != null) {
			return toDecorate.getName();
		}
		return "JokerTool";
	}

	@Override
	public void createHandles() {
		toDecorate.createHandles();
		
	}
	
	@Override
	public void activate() {
		setTool();
		this.context.showStatusText(this.getName() + " Mode");
	}

}
