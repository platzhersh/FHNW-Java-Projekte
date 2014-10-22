package patterns.strategy.layout;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class SnapLineLayoutTest extends JFrame {

	public static void main(String[] args) {
		JFrame f = new SnapLineLayoutTest();
		f.setSize(300, 200);
		f.setVisible(true);
	}

	int counter = 0;

	SnapLineLayoutTest() {
		setTitle("Layout Manager Test");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		SnapLineLayout layout = new SnapLineLayout();
		this.setLayout(layout);
		layout.addSnapLine("snap1", -20, 0.33f,
				SnapLineLayout.Direction.VERTICAL);
		layout.addSnapLine("snap2", 48, 0, SnapLineLayout.Direction.HORIZONTAL);
		layout.addSnapLine("snap3", -40, 1, SnapLineLayout.Direction.HORIZONTAL);

		JComponent c1 = new JPanel();
		JComponent c2 = new JPanel();
		JComponent c3 = new JPanel();
		JComponent c4 = new JPanel();
		c1.setBackground(Color.blue);
		c2.setBackground(Color.green);
		c3.setBackground(Color.yellow);
		c4.setBackground(Color.red);
		layout.registerComponent(c1, "TOP", "snap2", "LEFT", "RIGHT");
		layout.registerComponent(c2, "snap2", "BOTTOM", "LEFT", "snap1");
		layout.registerComponent(c3, "snap2", "snap3", "snap1", "RIGHT");
		layout.registerComponent(c4, "snap3", "BOTTOM", "snap1", "RIGHT");

		this.add(c1);
		this.add(c2);
		this.add(c3);
		this.add(c4);
	}
}
