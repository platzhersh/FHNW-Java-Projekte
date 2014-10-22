package patterns.strategy.layout;

import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TestLayout extends JFrame {

	public static void main (String[] args){
		JFrame f = new TestLayout();
		f.setSize(300,200);
		f.setVisible(true);
	}

	int counter = 0;

	TestLayout(){
		setTitle("Layout Manager Test");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		final Container c = getContentPane();

		c.setLayout(new java.awt.BorderLayout()); // default for JFrame
//		c.setLayout(new java.awt.FlowLayout());
//		c.setLayout(new java.awt.GridLayout(0,2));
//		c.setLayout(new javax.swing.BoxLayout(c, javax.swing.BoxLayout.Y_AXIS));
//		c.setLayout(new javax.swing.BoxLayout(c, javax.swing.BoxLayout.X_AXIS));
//		c.setLayout(new ListLayout());		
//		c.setLayout(new DiagonalLayout());
//		c.setLayout(new RandomLayout());

//		c.setLayout(null);
//		c.setLayout(new NullLayout());

		JButton b = new JButton("new Button");
		b.setBounds(0, 0, 100, 20);
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JButton b = new JButton("Button"+counter++);
				b.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						Rectangle r = b.getBounds();
						r.translate (10, 0);
						b.setBounds(r);
					}
				});
				c.add(b);
				b.setBounds(0, counter*20, 100, 20);
				validate();
			}
		});
		c.add("North", b);
	}
}
