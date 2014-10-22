package patterns.strategy.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class NullLayout implements LayoutManager {
	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
	}

	public Dimension minimumLayoutSize(Container parent) {
		return parent.getSize();
	}

	public Dimension preferredLayoutSize(Container parent) {
		return parent.getSize();
	}

	public void layoutContainer(Container parent) {
	}
}
