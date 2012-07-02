package ch.fhnw.chregi.oop;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BossPuzzle extends JFrame {
	
	public static void main(String[] args) {
	new BossPuzzle();
	}

	JButton[] buttons = new JButton[9];
	JButton startButton;
	JLabel ausgabe;
	JLabel titel;
	
	public BossPuzzle() {
		// Erzeugen der Elemente
		super("Boss Puzzle");
		startButton = new JButton("Start");
		titel = new JLabel("Willkommen");
		ausgabe = new JLabel(""); 
				
		BorderLayout alloverLayout = new BorderLayout();
		GridLayout buttonLayout = new GridLayout(3,3);
		GridLayout controlLayout = new GridLayout(2,1);

		JPanel buttonPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		
		// Set Fonts
		titel.setFont(new Font("Arial",1,24));
		ausgabe.setFont(new Font("Arial",1,20));
		startButton.setFont(new Font("Arial",1,20));
		
		// Center
		titel.setHorizontalAlignment(JLabel.CENTER);
		startButton.setHorizontalAlignment(JButton.CENTER);
		
		// Spacing / Padding
		alloverLayout.setHgap(20);
		buttonLayout.setHgap(10);
		buttonLayout.setVgap(10);
		controlLayout.setHgap(5);
		
		// Set Layouts
		this.setLayout(alloverLayout);
		buttonPanel.setLayout(buttonLayout);
		controlPanel.setLayout(controlLayout);
		
		// Set Borders
		//controlLayout.setBorder(new EmptyBorder(5,60,5,60));
		
		Font buttonFont = new Font("Arial",1,38);
		
		// create Number Buttons
		Integer buttonText = 1;
		for (int i = 0;i<9;i++){
			JButton button = new JButton(buttonText.toString());
			buttons[i] = button;
			buttonPanel.add(buttons[i]);
			buttonText++;
		}
		buttons[8].setText("");
		
		// Add Controls to Layout
		controlPanel.add(startButton);
		controlPanel.add(ausgabe);
		this.add(buttonPanel, BorderLayout.CENTER);
		this.add(titel, BorderLayout.NORTH);
		this.add(controlPanel, BorderLayout.SOUTH);
		
		
		startGUI();
		
	}
	
	public void startGUI() {
		this.pack();
		this.setVisible(true);
	}
	
}
