package patterns.factory.gui;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// AWT solution of calculator
/////////////////////////////

public class Gui01NoFactory {

	public static void main(String[] args){

		Frame f = new Frame("Calculator");

		final TextField x   = new TextField(10);
		final TextField y   = new TextField(10);
		final TextField sum = new TextField(10);
		sum.setEnabled(false);

		Button b = new Button("Compute");
		b.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int ix = Integer.parseInt(x.getText());
					int iy = Integer.parseInt(y.getText());
					sum.setText("" + (ix + iy));
				}
			}
		);

		f.setLayout(new GridLayout(4, 2));

		f.add(new Label("x"));   f.add(x);
		f.add(new Label("y"));   f.add(y);
		f.add(new Label("sum")); f.add(sum);
		f.add(b);

		f.pack();
		f.setVisible(true);

		f.addWindowListener(
			new WindowAdapter(){
				public @Override void windowClosing(WindowEvent e){
					System.exit(0);
				}
			}
		);

	}
}

