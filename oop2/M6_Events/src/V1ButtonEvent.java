import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class V1ButtonEvent extends JFrame{

	JButton b;
	JLabel l;
	
	public V1ButtonEvent(){
		super("Version 1");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(2,1));
		b= new JButton("Drueck mich!"); 
		b.addActionListener(new MyButtonListener());
		add(b);
		l= new JLabel();
		add(l);
		pack();
		setSize(300,100);
		setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		V1ButtonEvent be= new V1ButtonEvent();
	}

}
