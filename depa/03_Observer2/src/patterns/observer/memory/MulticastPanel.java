package patterns.observer.memory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// A panel with buttons to create and close sample frames.
public class MulticastPanel extends JPanel implements ActionListener {
	private int counter = 0;
	private JButton closeAllButton;

	public MulticastPanel() {
		// add "New" button
		JButton newButton = new JButton("New");
		this.add(newButton);
		newButton.addActionListener(this);

		closeAllButton = new JButton("Close all");
		this.add(closeAllButton);
	}

	public void actionPerformed(ActionEvent evt) {
		SimpleFrame f = new SimpleFrame();
		counter++;
		f.setTitle("Window " + counter);
		f.setSize(250, 150);
		f.setVisible(true);
		closeAllButton.addActionListener(f);
	}

	private class SimpleFrame extends JFrame implements ActionListener {
		byte[][] buf = new byte[1024][];
		{
			for (int i = 0; i < 1024; i++) {
				buf[i] = new byte[1024 * 64];
			}
		}

		public void actionPerformed(ActionEvent evt) {
			System.out.println("actionPerformed: "+this.getTitle());
			this.dispose();
			closeAllButton.removeActionListener(this);
		}
		

		public void finalize() {
			System.out.println("finalize "+ this.getTitle());
		}
	}
}
