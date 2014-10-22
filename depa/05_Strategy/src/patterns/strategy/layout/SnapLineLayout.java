package patterns.strategy.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.Hashtable;

// Reference:
// http://www.trigeminus.com/text/produkt/layoutmanager/TRG%20LayoutManagers%20-%20Developers%20Guide.pdf
public class SnapLineLayout implements LayoutManager {
	enum Direction {
		HORIZONTAL, VERTICAL
	}
	
	private Hashtable<Component, SnapLineBoundingBox> components = new Hashtable<Component, SnapLineBoundingBox>();
	private Hashtable<String, SnapLine> snapLines = new Hashtable<String, SnapLine>();

	public SnapLineLayout() {
		snapLines.put("TOP", new SnapLine(0, 0, Direction.HORIZONTAL));
		snapLines.put("BOTTOM", new SnapLine(0, 1, Direction.HORIZONTAL));
		snapLines.put("LEFT", new SnapLine(0, 0, Direction.VERTICAL));
		snapLines.put("RIGHT", new SnapLine(0, 1, Direction.VERTICAL));
	}

	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
		deregisterComponent(comp);
	}

	public Dimension minimumLayoutSize(Container parent) {
		return parent.getSize();
	}

	public Dimension preferredLayoutSize(Container parent) {
		return parent.getSize();
	}

	public void layoutContainer(Container parent) {
		int x, y, w, h, xpos, ypos, width, height, numberOfComponents;
		xpos = parent.getInsets().left;
		ypos = parent.getInsets().top;
		width = parent.getSize().width - parent.getInsets().left
				- parent.getInsets().right;
		height = parent.getSize().height - parent.getInsets().top
				- parent.getInsets().bottom;
		numberOfComponents = parent.getComponentCount();

		for (int i = 0; i < numberOfComponents; i++) {
			Component currentComp = parent.getComponent(i);
			if (currentComp == null)
				continue;
			SnapLineBoundingBox bb = components.get(currentComp);
			if (bb == null)
				continue;
			SnapLine left, right, top, bottom;

			top = snapLines.get(bb.top);
			bottom = snapLines.get(bb.bottom);
			left = snapLines.get(bb.left);
			right = snapLines.get(bb.right);

			if ((top == null) || (bottom == null) || (left == null)
					|| (right == null))
				continue;

			x = xpos + left.abs + (int) (left.rel * (float) width);
			w = xpos + right.abs + (int) (right.rel * (float) width);
			y = ypos + top.abs + (int) (top.rel * (float) height);
			h = ypos + bottom.abs + (int) (bottom.rel * (float) height);
			w -= x;
			h -= y;

			currentComp.setBounds(x, y, w, h);
		}
	}

	// R E G I S T E R / D E R E G I S T E R C O M P O N E N T S

	public void registerComponent(Component component, String top,
			String bottom, String left, String right) {
		if (snapLines.get(top).dir != Direction.HORIZONTAL
				|| snapLines.get(bottom).dir != Direction.HORIZONTAL
				|| snapLines.get(left).dir != Direction.VERTICAL
				|| snapLines.get(right).dir != Direction.VERTICAL) {
			throw new AssertionError();
		}
		components.put(component, new SnapLineBoundingBox(top, bottom, left,
				right));
	}

	public void deregisterComponent(Component component) {
		components.remove(component);
	}

	// S N A P L I N E S

	public void addSnapLine(String name, int abs, float rel, SnapLineLayout.Direction dir) {
		snapLines.put(name, new SnapLine(abs, rel, dir));
	}

	static class SnapLine {
		public int abs;
		public float rel;
		public SnapLineLayout.Direction dir;

		public SnapLine(int abs, float rel, SnapLineLayout.Direction dir) {
			this.abs = abs;
			this.rel = rel;
			this.dir = dir;
		}
	}
	
	static class SnapLineBoundingBox {
		String top = null;
		String bottom = null;
		String left = null;
		String right = null;

		public SnapLineBoundingBox(String top, String bottom, String left, String right) {
			this.top = top;
			this.bottom = bottom;
			this.left = left;
			this.right = right;
		}
	}


}


