package ch.fhnw.glatthard.christian.oop2.babble.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import ch.fhnw.glatthard.christian.oop2.babble.model.*;

import java.awt.Toolkit;

/***
 * Creates GUI
 * Frontend of the Application. Accesses Model to get Data to display
 * @author chregi
 *
 */
@SuppressWarnings("serial")
public class BabbleView extends JFrame {
	
	Babble model;
	
	
	
	// -------- Initialize Controls
	
	// Box1
	public JButton btnFileInput = new JButton("Open Text File");
	
	// Box2
	public JTextArea txtAreaInput = new JTextArea();
	JScrollPane scrlPaneInput = new JScrollPane(txtAreaInput);
	String filepath = null; // Path to chosen File

	// TODO: Input Analysis
	JLabel lblInputTitle = new JLabel("Original Text");
	JLabel lblInputLength = new JLabel(" characters");
	JLabel lblInputPatterns = new JLabel(" patterns");
	public JLabel lblInputLengthValue = new JLabel("--");
	public JLabel lblInputPatternsValue = new JLabel("--");

	// Box3
	public JTextArea txtAreaOutput = new JTextArea();
	JScrollPane scrlPaneOutput = new JScrollPane(txtAreaOutput);

	// TODO: Output Analysis
	JLabel lblOutputTitle = new JLabel("Babble Text");
	JLabel lblOutputLength = new JLabel(" characters");
	public JLabel lblOutputLengthValue = new JLabel("--");
	
	
	public JButton btnBabbleIt = new JButton("Babble it");	
	public JSlider sliderWindowSize = new JSlider(0,1,7,5);
	
	
	
	/***
	 * Default Constructor for BabbleView
	 * @param m Babble Model
	 */
	public BabbleView(Babble m){

		// initialize JFrame with Title
		super("Babble Text");
		setIconImage(Toolkit.getDefaultToolkit().getImage(BabbleView.class.getResource("/sun/print/resources/orientLandscape.png")));
		
		this.model = m;
		
		btnBabbleIt.setEnabled(false);
		
		txtAreaInput.setColumns(50);
		txtAreaInput.setRows(6);
		txtAreaInput.setEnabled(false);
		txtAreaInput.setLineWrap(true);
		txtAreaInput.setWrapStyleWord(true);
		
		lblInputTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		lblOutputTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		
		txtAreaOutput.setColumns(50);
		txtAreaOutput.setRows(6);
		//txtAreaOutput.setEnabled(false);
		txtAreaOutput.setLineWrap(true);
		txtAreaOutput.setWrapStyleWord(true);
		
		sliderWindowSize.setMajorTickSpacing(1);
		sliderWindowSize.setPaintTicks(true);
		sliderWindowSize.setValueIsAdjusting(true);
		sliderWindowSize.setPaintLabels(true);
		sliderWindowSize.setMaximumSize(new Dimension(120, 60));

		// Set Default Close Action
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// -------- Initialize Containers
		BorderLayout layout = new BorderLayout();
			layout.setVgap(10);
			layout.setHgap(10);
		Box boxContainer = Box.createVerticalBox();

		Box box1 = Box.createHorizontalBox();
			box1.setSize(new Dimension(500, 20));
		Box box2 = Box.createHorizontalBox();
		lblInputTitle.setLabelFor(box2);
			Box box21 = Box.createVerticalBox();
				Box box211 = Box.createHorizontalBox();
				Box box212 = Box.createHorizontalBox();
				Box box213 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		lblOutputTitle.setLabelFor(box2);
			Box box31 = Box.createVerticalBox();
				Box box311 = Box.createHorizontalBox();
				Box box312 = Box.createHorizontalBox();
		
		Box boxSouthContainer = Box.createVerticalBox();
		Box boxSouth = Box.createHorizontalBox();
		
		
		// Set Layout Manager and Properties
		getContentPane().setLayout(layout);
	
		// ----- Place Controls
		
		// North
		getContentPane().add(Box.createVerticalStrut(20),BorderLayout.NORTH);
		
		// CENTER - Controls
		
		/*boxContainer.add(box1);
			box1.add(lblFileInput);
			box1.add(Box.createGlue());
			box1.add(btnFileInput);*/
		
		boxContainer.add(Box.createVerticalStrut(10));
		
		boxContainer.add(box2);
			box2.add(box21);
				box21.add(box211);
					box211.add(lblInputTitle);
					box211.add(Box.createHorizontalGlue());
				box21.add(Box.createVerticalStrut(5));
				box21.add(box212);
					box212.add(lblInputLengthValue);
					box212.add(Box.createHorizontalGlue());
					box212.add(lblInputLength);
				box21.add(Box.createVerticalStrut(5));
				
				box21.add(box213);
					box213.add(lblInputPatternsValue);	
					box213.add(Box.createHorizontalGlue());
					box213.add(lblInputPatterns);					
				box21.add(Box.createVerticalStrut(5));
				
			box2.add(Box.createHorizontalStrut(10));
			box2.add(scrlPaneInput);
			//box2.add(txtAreaInput);
		
		boxContainer.add(Box.createVerticalStrut(10));
			
		boxContainer.add(box3);
			box3.add(box31);
				box31.add(box311);
					box311.add(lblOutputTitle);
					box311.add(Box.createHorizontalGlue());
				box31.add(Box.createVerticalStrut(5));
					
				box31.add(box312);
					box312.add(lblOutputLengthValue);
					box312.add(Box.createHorizontalGlue());
					box312.add(lblOutputLength);
				box31.add(Box.createVerticalStrut(5));
				
			box3.add(Box.createHorizontalStrut(10));
			box3.add(scrlPaneOutput);
			//box3.add(txtAreaOutput);
		
		
		getContentPane().add(boxContainer, BorderLayout.CENTER);

			
		// EAST
		getContentPane().add(Box.createHorizontalStrut(20), BorderLayout.EAST);
		
		// WEST
		getContentPane().add(Box.createHorizontalStrut(20), BorderLayout.WEST);
		
		// SOUTH
		boxSouthContainer.add(boxSouth);
			boxSouth.add(Box.createHorizontalGlue());
			btnFileInput.setIcon(new ImageIcon(BabbleView.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
			boxSouth.add(btnFileInput);
			boxSouth.add(Box.createHorizontalStrut(10));
			boxSouth.add(sliderWindowSize);
			boxSouth.add(Box.createHorizontalStrut(10));
			boxSouth.add(btnBabbleIt);
			boxSouth.add(Box.createHorizontalStrut(25));
		boxSouthContainer.add(Box.createVerticalStrut(15));
		
		
		getContentPane().add(boxSouthContainer, BorderLayout.SOUTH);
		
		
		// GUI packen
		this.pack();
		
		// GUI sichtbar machen
		this.setVisible(true);
		
	}
	
	

}
