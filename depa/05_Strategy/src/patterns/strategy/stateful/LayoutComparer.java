package patterns.strategy.stateful;

import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LayoutComparer extends JFrame {

	public static void main(String[] args) {
		JFrame f = new LayoutComparer();
		f.pack();
		f.setVisible(true);
	}

	static int counter = 0;

	JPanel createPanel(LayoutManager layout, String title) {
		JPanel p = new JPanel();
		p.setLayout(layout);
		p.add(new JButton("Click " + counter++), "West");
		p.add(new JButton("Click " + counter++), "Center");
		p.add(new JButton("Click " + counter++), "East");
		p.setBorder(BorderFactory.createTitledBorder(title));
		return p;
	}

	LayoutComparer() {
		setTitle("Layout Manager Test");
		setLayout(new GridLayout(1, 2));
		LayoutManager m1;
		LayoutManager m2;
		//m = new java.awt.FlowLayout();
		m1 = new java.awt.BorderLayout();
		m2 = new java.awt.BorderLayout();

		add(createPanel(m1, "Left"));
		//pack();
		add(createPanel(m2, "Right"));
	}
}
