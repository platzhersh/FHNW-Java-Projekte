package patterns.strategy.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.Random;

public class RandomLayout implements LayoutManager {
	Random random = new Random();

	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
	}

	public Dimension preferredLayoutSize(Container parent) {
		return parent.getSize();
	}

	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int minX = insets.left;
		int minY = insets.top;
		int maxWidth = parent.getSize().width - (insets.left + insets.right);
		int maxHeight = parent.getSize().height - (insets.top + insets.bottom);

		int nComps = parent.getComponentCount();

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				Dimension d = c.getPreferredSize();
				int x = minX + random.nextInt(Math.max(1, maxWidth - d.width));
				int y = minY
						+ random.nextInt(Math.max(1, maxHeight - d.height));
				c.setBounds(x, y, d.width, d.height);
			}
		}
	}

}
