package patterns.decorator.demo.resizable;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {

	ResizableDecorator2 label1 = new ResizableDecorator2(new JLabel(" Label1"));
	ResizableDecorator2 button1 = new ResizableDecorator2(new JButton("Button"));
	ResizableDecorator2 scrollbar = new ResizableDecorator2(new JScrollBar(JScrollBar.HORIZONTAL));
	
	
	public Frame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		//setLayout(new GridLayout(0,1));
		label1.setBounds(new Rectangle(10, 10, 120, 25));
		button1.setBounds(new Rectangle(10, 60, 120, 25));
		scrollbar.setBounds(new Rectangle(10, 110, 120, 25));
		
		add(label1);
		add(button1);
		add(scrollbar);

		JComponent p = new JPanel();
		p = new ResizableDecorator2(p);
		p.setLayout(new GridLayout(2,2));

		JButton b;
		p.add(b = new JButton("1")); b.setBounds(new Rectangle(5, 5, 40, 20));
		p.add(new JButton("2"));
		p.add(new JButton("3"));
		p.add(new JButton("4"));	// dürfen nicht in den Decorator angefügt werden.
									// dort müsste allenfalls das add so überschrieben sein
									// dass das an das Child weitergegeben wird
		
		p.setBounds(new Rectangle(10, 160, 150, 50));
		add(p);
	}

	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.setBounds(0, 0, 200, 200);
		frame.setVisible(true);
	}

}