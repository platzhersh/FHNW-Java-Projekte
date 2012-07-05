import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Stream_Frame extends JFrame {
	JButton showInfo = new JButton("show Info");
	JButton showDir = new JButton("show Directory");
	JButton showFile1 = new JButton("use FileReader");
	JButton showFile2 = new JButton("use BufferedReader");
	JButton showFile3 = new JButton("use LineNumberReader");
	JButton saveFile = new JButton("save Text");
	
	JTextField txtInput = new JTextField();
	JLabel errorMessage = new JLabel();
	JTextArea output = new JTextArea();

	public Stream_Frame() {
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(4, 2));
		top.add(new JLabel("Name: "));
		top.add(txtInput);
		top.add(showInfo);
		showDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showInfo();
			}
		});
		top.add(showDir);
		showDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showDir();
			}
		});
		top.add(showFile1);
		showFile1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFile1();
			}
		});
		top.add(showFile2);
		showFile2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFile2();
			}
		});
		top.add(showFile3);
		showFile3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFile3();
			}
		});
		top.add(saveFile);
		saveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//showFile3();
			}
		});
		setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		add(output, BorderLayout.CENTER);
		add(errorMessage, BorderLayout.SOUTH);
		pack();
		setSize(400,600);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void showInfo() {
		output.setText("");
		String dirName = txtInput.getText();

	}

	public void showDir() {
		output.setText("");
		String dirName = txtInput.getText();
		
	}

	public void showFile1() {
		output.setText("");
		String fileName = txtInput.getText();
		
	}

	public void showFile2() {
		output.setText("");
		String fileName = txtInput.getText();
		
	}

	public void showFile3() {
		output.setText("");
		String fileName = txtInput.getText();
		
	}
	
	public void saveFile(){
		String fileName = txtInput.getText();
		
	}

	public static void main(String[] args) {
		Stream_Frame mf = new Stream_Frame();
	}

}
