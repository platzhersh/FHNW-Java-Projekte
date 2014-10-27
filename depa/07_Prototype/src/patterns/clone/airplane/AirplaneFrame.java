package patterns.clone.airplane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AirplaneFrame extends JFrame {

	public static void main(String[] args) {
		JFrame f = new AirplaneFrame();
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setTitle("Flight Simulator");
		f.setSize(300, 200);
		f.setVisible(true);
	}

	public AirplaneFrame() {
		final Airplane a = new Airplane();
		final Airplane b = (Airplane) a.clone();

		JPanel p = new JPanel(new java.awt.GridLayout(2, 3));
		add(p, java.awt.BorderLayout.CENTER);

		p.add(new JLabel("<html>Original<br>Airplane M1"));
		JButton fireA = new JButton("<html>Catch Fire<br>(Engine 0)");
		p.add(fireA);
		fireA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.catchFire(0);
				repaint();
			}
		});
		p.add(new JComponent() {
			public void paint(Graphics g) {
				Dimension d = getSize();
				int size = Math.min(d.width, d.height) - 8;
				g.setColor(a.isWarningLightOn() ? Color.red : Color.green);
				g.fillOval((d.width - size) / 2, (d.height - size) / 2, size,
						size);
			}
		});

		p.add(new JLabel("<html>Cloned<br>Airplane M2"));
		JButton fireB = new JButton("<html>Catch Fire<br>(Engine 0)");
		p.add(fireB);
		fireB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b.catchFire(0);
				repaint();
			}
		});
		p.add(new JComponent() {
			public void paint(Graphics g) {
				Dimension d = getSize();
				int size = Math.min(d.width, d.height) - 8;
				g.setColor(b.isWarningLightOn() ? Color.red : Color.green);
				g.fillOval((d.width - size) / 2, (d.height - size) / 2, size,
						size);
			}
		});

		JButton reset = new JButton("Reset");
		add(reset, java.awt.BorderLayout.SOUTH);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.drawFire();
				b.drawFire();
				repaint();
			}
		});

	}
}
