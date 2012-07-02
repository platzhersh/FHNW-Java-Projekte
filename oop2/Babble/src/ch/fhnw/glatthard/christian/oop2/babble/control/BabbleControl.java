package ch.fhnw.glatthard.christian.oop2.babble.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.fhnw.glatthard.christian.oop2.babble.*;
import ch.fhnw.glatthard.christian.oop2.babble.model.*;
import ch.fhnw.glatthard.christian.oop2.babble.view.*;

/***
 * Controls GUI
 * Event Handling for View & method calls on model based on events
 * @author chregi
 *
 */
public class BabbleControl implements ActionListener, ChangeListener {
	
	private Babble model;
	private BabbleView view;
	
	private String filePath = null;
	
	
	public BabbleControl(Babble model, BabbleView view) {
		this.model = model;
		this.view = view;
		
		// Register Controls for Event Handling
		view.btnFileInput.addActionListener(this);
		view.btnBabbleIt.addActionListener(this);
	    view.sliderWindowSize.addChangeListener(this);

		
		// window-closing event handling 
		view.addWindowListener(new WindowAdapter() { 
		public void windowClosing(WindowEvent evt) { 
			BabbleProgram.getMainProgram().endMainProgram(); 
			};
		});
		
		

	}
	/***
	 * Updates the Analysis Labels of the GUI
	 * @param patternCount number of Patterns created
	 * @param charCount length of input text
	 */
	public void updateInputLabels(Integer patternCount, Integer charCount) {
		view.lblInputPatternsValue.setText(patternCount.toString());
		view.lblInputLengthValue.setText(charCount.toString());
	}
	
	/***
	 * Updates the Analysis Labels of the GUI
	 * @param charCount length of output text
	 */
	public void updateOutputLabels(Integer charCount){
		view.lblOutputLengthValue.setText(charCount.toString());
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource()==view.btnFileInput){
			
		// Open FileChooser (text files only)
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt","text");
		chooser.setFileFilter(filter);
		
		int returnValue = chooser.showOpenDialog(this.view);
		
		if (returnValue == JFileChooser.APPROVE_OPTION)
			filePath = chooser.getSelectedFile().getAbsolutePath();
		
		// Read-in File and display it's content in the Input Text Area
		String text = model.fileToString(filePath);
		view.txtAreaInput.setText(text);
		
		
		// Analyze Text
		try {
			model.analyzeText(view.txtAreaInput.getText(), view.sliderWindowSize.getValue());
			updateInputLabels(model.getPatternCount(), view.txtAreaInput.getText().length());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		view.btnBabbleIt.setEnabled(true);
		
		} 
		else if (event.getSource()==view.btnBabbleIt){
			try {
				String output = model.babble(view.txtAreaInput.getText(), view.sliderWindowSize.getValue());
				view.txtAreaOutput.setText(output);
				updateOutputLabels(view.txtAreaOutput.getText().length());
				
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			}
			
		}

	}

	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource()==view.sliderWindowSize){
			if (view.txtAreaInput.getText().length() > 0) {
				System.out.println("Let's analyze this (windowSize "+view.sliderWindowSize.getValue()+")");
				try {
					model.analyzeText(view.txtAreaInput.getText(), view.sliderWindowSize.getValue());
					updateInputLabels(model.getPatternCount(), view.txtAreaInput.getText().length());
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			System.out.println("Select File first");
		}
		
	}

}
