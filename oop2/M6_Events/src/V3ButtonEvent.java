import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class V3ButtonEvent extends JFrame implements ActionListener {

	JButton b;
	JLabel l;
	
	public V3ButtonEvent(){
		super("Version 2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(2,1));
		b= new JButton("Drueck mich!"); 
		b.addActionListener(this);
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
		V3ButtonEvent be= new V3ButtonEvent();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Der Button wurde gedrückt");
	}

}
