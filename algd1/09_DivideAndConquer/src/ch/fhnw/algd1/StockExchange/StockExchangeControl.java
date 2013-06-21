package ch.fhnw.algd1.StockExchange;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StockExchangeControl implements ActionListener, ChangeListener	{

	StockExchangeGUI view;
	StockExchange model;
	
	public StockExchangeControl(StockExchangeGUI gui, StockExchange m) {
		view = gui;
		model = m;
		registerEventHandlers();
	}
	
	public void registerEventHandlers() {
		// Register Controls for Event Handling
		view.btnGenerate.addActionListener(this);
		view.btnFileInput.addActionListener(this);
		view.btnFileOutput.addActionListener(this);
		view.txtAreaStockArray.getDocument().addDocumentListener(new MyDocumentListener(view));
	}
	
	@Override
	public void stateChanged(ChangeEvent event) {
		// --
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource()==view.btnFileInput){
			// Open FileChooser (text files only)
			JFileChooser chooser = new JFileChooser();
			chooser.setAcceptAllFileFilterUsed(false);


			int returnValue = chooser.showOpenDialog(this.view);
			
			String filePath;
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				filePath = chooser.getSelectedFile().getAbsolutePath();
				
				try {
					// create an ObjectInputStream for the file we created before
			         ObjectInputStream ois =
			                 new ObjectInputStream(new FileInputStream(filePath));
		
			         // read and print an object and cast it as string
			         model.stock = (float[]) ois.readObject();
			         updateOutputFields();
			         ois.close();
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		}
		else if (event.getSource() == view.btnFileOutput ) {
			
			// Open FileChooser (text files only)
			JFileChooser chooser = new JFileChooser();
			chooser.setAcceptAllFileFilterUsed(false);


			int returnValue = chooser.showOpenDialog(this.view);
			
			String filePath;
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				filePath = chooser.getSelectedFile().getAbsolutePath();

				try {
					// create a new file with an ObjectOutputStream
			         FileOutputStream out = new FileOutputStream(filePath);
			         ObjectOutputStream oout = new ObjectOutputStream(out);
		
			         // write something in the file
			         oout.writeObject(model.stock);
			         oout.flush();
			         oout.close();
					
				} catch (Exception e) {
					System.err.println(e);
				}

			}
		}
		else if (event.getSource() == view.btnGenerate ) {
			model.generateStock(100000, 100);
			updateOutputFields();
		}
		
	}
	
	public void updateOutputFields() {
		view.txtAreaStockArray.setText(model.toString());
		view.txtAreaAnalysis.setText(model.getBestPeriod().toString());
	}
	
	private class MyDocumentListener implements DocumentListener {
		
		StockExchangeGUI view;
		
		public MyDocumentListener(StockExchangeGUI v) {
			view = v;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO?
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			if (e.getDocument().getLength() >= 0 ) view.btnFileOutput.setEnabled(true);
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if (e.getDocument().getLength() <= 0 ) view.btnFileOutput.setEnabled(false);
			
		}
		
	}
}
