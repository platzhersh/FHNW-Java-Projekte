import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class V2ButtonEvent extends JFrame{

	JButton b;
	JLabel l;
	
	public V2ButtonEvent(){
		super("Version 2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(2,1));
		b= new JButton("Drueck mich!"); 
		b.addActionListener(new MyButtonListener2());
		add(b);
		l= new JLabel();
		add(l);
		pack();
		setSize(300,100);
		setVisible(true);
	}
	
	public class MyButtonListener2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Der Button wurde gedrückt");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		V2ButtonEvent be= new V2ButtonEvent();
	}

}
