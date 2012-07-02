package ch.fhnw.glatthard.christian.oop2.swb;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class View extends JFrame {
	
	Model model;
	Control control;
	
	
	// Initialize Components
	Box boxNavigation;
	JLabel lblURL;
	JLabel lblPort;
	JButton btnGo;
	JTextField txtURL;
	JTextField txtPort;
	JTextPane lblHTMLOutput;
	
	
	// Constructor
	public View(Model m) {

		// Initialize Window & Set Title
		super("Simple Web Browser");
		
		// Link to other MVC Components
		this.model = m;
				
		// Define Components
		boxNavigation = Box.createHorizontalBox();
		lblURL = new JLabel("URL");
		lblPort = new JLabel("Port");
		btnGo = new JButton("GO");
		txtURL = new JTextField();
			txtURL.setColumns(120);
		txtPort = new JTextField("80");
			txtPort.setColumns(4);
		lblHTMLOutput = new JTextPane();
			lblHTMLOutput.setMinimumSize(new Dimension(400,200));
		
		// Set Layout Manager
		this.setLayout(new BorderLayout());
		
		// Place Components
		this.add(boxNavigation, BorderLayout.NORTH);
			boxNavigation.add(lblURL);
			boxNavigation.add(txtURL);
			boxNavigation.add(lblPort);
			boxNavigation.add(txtPort);
			boxNavigation.add(btnGo);
		this.add(lblHTMLOutput, BorderLayout.CENTER);
		
		// Create GUI
		this.pack();
		this.setVisible(true);
	}
	


}
