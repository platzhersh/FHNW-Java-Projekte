package patterns.observer.memory;

import javax.swing.JFrame;

public class MulticastTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame() {
			{
				this.setTitle("MulticastTest");
				this.setSize(300, 75);
				this.add(new MulticastPanel());
			}
		};

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
