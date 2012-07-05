import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class V5ButtonEvent extends JFrame {

	JButton b;
	JLabel l;
	
	public V5ButtonEvent(){
		super("Version 2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(2,1));
		b= new JButton("Drueck mich!"); 
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				buttonBclicked();
			}
		});
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
		V5ButtonEvent be= new V5ButtonEvent();
	}

	public void buttonBclicked() {
		System.out.println("Der Button wurde gedrückt");
	}
}
