package ch.fhnw.algd1.StockExchange;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class StockExchangeGUI extends JFrame {

	public JButton btnGenerate = new JButton("Generate StockExchange");
	public JButton btnFileInput = new JButton("Import Text File");
	public JButton btnFileOutput = new JButton("Export Text File");
	
	public JTextArea txtAreaStockArray = new JTextArea();
	public JTextArea txtAreaAnalysis = new JTextArea();
	
	public StockExchangeGUI() {
		super("algd1 - StockExchange");
		// Set Default Close Action
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		BorderLayout layout = new BorderLayout();
		layout.setVgap(10);
		layout.setHgap(10);
		
		this.setLayout(layout);
		this.add(getButtons(), BorderLayout.NORTH);
		this.add(getTextAreas(), BorderLayout.CENTER);	
		
		// GUI packen
		this.pack();

		// GUI sichtbar machen
		this.setVisible(true);
		
	}
	
	public JPanel getButtons() {
		btnFileOutput.setEnabled(false);
		JPanel panel = new JPanel();
		
		panel.add(btnGenerate);
		panel.add(btnFileInput);
		panel.add(btnFileOutput);
		
		return panel;
	}

	public Box getTextAreas() {
		txtAreaStockArray.setColumns(50);
		txtAreaStockArray.setRows(6);
		txtAreaStockArray.setEditable(false);
		txtAreaStockArray.setAutoscrolls(true);
		txtAreaStockArray.setLineWrap(true);
		txtAreaStockArray.setWrapStyleWord(true);
		
		txtAreaAnalysis.setColumns(50);
		txtAreaAnalysis.setRows(1);
		txtAreaAnalysis.setEditable(false);
		
		JScrollPane panel1 = new JScrollPane(txtAreaStockArray);
		
		
		JPanel panel2 = new JPanel();
		panel2.add(txtAreaAnalysis);
		
		Box box = Box.createVerticalBox();
		box.add(panel1);
		box.add(panel2);
		return box;
	}
	
}
