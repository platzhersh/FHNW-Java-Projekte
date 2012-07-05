package fhnw.oop2.streamreader.view;

import java.awt.BorderLayout;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class StreamReaderView extends JFrame {
	
	private BorderLayout layout;
	
	private JLabel lblFileName = new JLabel("File:");
	private JTextField txtFileName = new JTextField();
	
	private JButton btnInfo = new JButton("show Info");
	private JButton btnDirectory = new JButton("show Directory");
	private JButton btnFileReader = new JButton("use FileReader");
	private JButton btnBufferedReader = new JButton("use BufferedReader");
	private JButton btnLineNumberReader = new JButton("use LineNumberReader");
	private JButton btnSave = new JButton("Save Text");
	
	private JTextArea txtStream = new JTextArea();
	
	private JLabel lblStatus = new JLabel();
	
	public StreamReaderView () {
		super("Stream Reader");
		createView();
	}
		
	public void createView() {	
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		/* NORTH - all the Navigation Stuff and Functionality */
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		panel.add(lblFileName);
		panel.add(txtFileName);
		panel.add(btnInfo);
		panel.add(btnDirectory);
		panel.add(btnFileReader);
		panel.add(btnBufferedReader);
		panel.add(btnLineNumberReader);
		panel.add(btnSave);
		
		
		/* CENTER - the content */
		getContentPane().add(txtStream, BorderLayout.CENTER);
		
		/* SOUTH - status bar to display errors */
		getContentPane().add(lblStatus, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	public String getFileName() {
		return txtFileName.getText();
	}
	
	/* Action Listener Schnittstelle bereitstellen */
	
	public void setInfoButtonListener(ActionListener a) {
		this.btnInfo.addActionListener(a);
	}
	public void setDirectoryButtonListener(ActionListener a) {
		this.btnInfo.addActionListener(a);
	}
	public void setFileReaderButtonListener(ActionListener a) {
		this.btnInfo.addActionListener(a);
	}
	public void setBufferedReaderButtonListener(ActionListener a) {
		this.btnInfo.addActionListener(a);
	}
	public void setLineNumberReaderButtonListener(ActionListener a) {
		this.btnInfo.addActionListener(a);
	}
	public void setSaveButtonListener(ActionListener a) {
		this.btnInfo.addActionListener(a);
	}
}
