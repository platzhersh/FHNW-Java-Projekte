package patterns.strategy.layout;

import java.awt.*;

public class BorderLayout  implements LayoutManager {

	public static final String NORTH = "North";
	public static final String SOUTH = "South";
	public static final String EAST = "East";
	public static final String WEST = "West";
	public static final String CENTER = "Center";

	int hgap;
	int vgap;

	Component north;
	Component west;
	Component east;
	Component south;
	Component center;

	public BorderLayout() { this(0, 0); }

	public BorderLayout(int i, int j)  {
	hgap = i;
	vgap = j;
	}

	public void addLayoutComponent(String s, Component component) {
		/* Special case:  treat null the same as "Center". */
		if (s == null) {
		    s = "Center";
		}
		
		if("Center".equals(s)) center = component;
		else if("North".equals(s))     north = component; 
		else if("South".equals(s))     south = component;
		else if("East".equals(s))     east = component;
		else if("West".equals(s))     west = component;
		else     throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + s);
	}

	public void removeLayoutComponent(Component component) {
		if(component == center)     center = null;
		else if(component == north)     north = null;
		else if(component == south)     south = null;
		else if(component == east)     east = null;
		else if(component == west)     west = null;
	}

	public Dimension minimumLayoutSize(Container container) {
		Dimension dimension = new Dimension(0, 0);
		Component component = null;
		if((component = getChild("East")) != null) {
			Dimension dimension1 = component.getMinimumSize();
			dimension.width += dimension1.width + hgap;
			dimension.height = Math.max(dimension1.height, dimension.height);
		}
		if((component = getChild("West")) != null) {
			Dimension dimension2 = component.getMinimumSize();
			dimension.width += dimension2.width + hgap;
			dimension.height = Math.max(dimension2.height, dimension.height);
		}
		if((component = getChild("Center")) != null) {
			Dimension dimension3 = component.getMinimumSize();
			dimension.width += dimension3.width;
			dimension.height = Math.max(dimension3.height, dimension.height);
		}
		if((component = getChild("North")) != null) {
			Dimension dimension4 = component.getMinimumSize();
			dimension.width = Math.max(dimension4.width, dimension.width);
			dimension.height += dimension4.height + vgap;
		}
		if((component = getChild("South")) != null) {
			Dimension dimension5 = component.getMinimumSize();
			dimension.width = Math.max(dimension5.width, dimension.width);
			dimension.height += dimension5.height + vgap;
		}
		Insets insets = container.getInsets();
		dimension.width += insets.left + insets.right;
		dimension.height += insets.top + insets.bottom;
		return dimension;
	}

	public Dimension preferredLayoutSize(Container container)  {
		Dimension dimension = new Dimension(0, 0);
		Component component = null;
		if((component = getChild("East")) != null) {
			Dimension dimension1 = component.getPreferredSize();
			dimension.width += dimension1.width + hgap;
			dimension.height = Math.max(dimension1.height, dimension.height);
		}
		if((component = getChild("West")) != null) {
			Dimension dimension2 = component.getPreferredSize();
			dimension.width += dimension2.width + hgap;
			dimension.height = Math.max(dimension2.height, dimension.height);
		}
		if((component = getChild("Center")) != null) {
			Dimension dimension3 = component.getPreferredSize();
			dimension.width += dimension3.width;
			dimension.height = Math.max(dimension3.height, dimension.height);
		}
		if((component = getChild("North")) != null) {
			Dimension dimension4 = component.getPreferredSize();
			dimension.width = Math.max(dimension4.width, dimension.width);
			dimension.height += dimension4.height + vgap;
		}
		if((component = getChild("South")) != null) {
			Dimension dimension5 = component.getPreferredSize();
			dimension.width = Math.max(dimension5.width, dimension.width);
			dimension.height += dimension5.height + vgap;
		}
		Insets insets = container.getInsets();
		dimension.width += insets.left + insets.right;
		dimension.height += insets.top + insets.bottom;
		return dimension;
	}

	public void layoutContainer(Container container) {
		Insets insets = container.getInsets();
		int i = insets.top;
		int j = container.getHeight() - insets.bottom;
		int k = insets.left;
		int l = container.getWidth() - insets.right;
		Component component = null;
		if((component = getChild("North")) != null) {
			component.setSize(l - k, component.getHeight());
			Dimension dimension = component.getPreferredSize();
			component.setBounds(k, i, l - k, dimension.height);
			i += dimension.height + vgap;
		}
		if((component = getChild("South")) != null) {
			component.setSize(l - k, component.getHeight());
			Dimension dimension1 = component.getPreferredSize();
			component.setBounds(k, j - dimension1.height, l - k, dimension1.height);
			j -= dimension1.height + vgap;
		}
		if((component = getChild("East")) != null) {
			component.setSize(component.getWidth(), j - i);
			Dimension dimension2 = component.getPreferredSize();
			component.setBounds(l - dimension2.width, i, dimension2.width, j - i);
			l -= dimension2.width + hgap;
		}
		if((component = getChild("West")) != null) {
			component.setSize(component.getWidth(), j - i);
			Dimension dimension3 = component.getPreferredSize();
			component.setBounds(k, i, dimension3.width, j - i);
			k += dimension3.width + hgap;
		}
		if((component = getChild("Center")) != null)
		component.setBounds(k, i, l - k, j - i);
	}

	private Component getChild(String s) {
		Component component = null;
		if(s == "North") component = north;
		else if(s == "South") component = south;
		else if(s == "West") component = west;
		else if(s == "East") component = east;
		else if(s == "Center") component = center;

		if(component != null && !component.isVisible())
		component = null;
		return component;
	}

}
