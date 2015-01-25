package ch.fhnw.depa.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.eclipse.swt.SWT;

import ch.fhnw.depa.controller.ControllerInterface;
import ch.fhnw.depa.model.BeatModelInterface;

public class DJView implements ActionListener, BeatObserver, BPMObserver {

	BeatModelInterface model;
	ControllerInterface controller;
	JFrame viewFrame, controlFrame;
	JPanel viewPanel, controlPanel;
	BeatBar beatBar;
	JLabel bpmOutputLabel, bpmLabel;
	JTextField bpmTextField;
	JButton setBPMButton, increaseBPMButton, decreaseBPMButton;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem startMenuItem, stopMenuItem;
	
	// Constructor
	
	public DJView(ControllerInterface controller, BeatModelInterface model) {
		this.controller = controller;
		this.model = model;
		model.registerObserver((BeatObserver)this);
		model.registerObserver((BPMObserver)this);
	}
	
	// initialize view
	public void createView() {
		// create all swing components here
		viewPanel = new JPanel(new GridLayout(1,2));
		viewFrame = new JFrame("View");
		viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewFrame.setSize(new Dimension(100, 80));
		bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
		beatBar = new BeatBar();
		JPanel bpmPanel = new JPanel(new GridLayout(2, 1));
		bpmPanel.add(beatBar);
		bpmPanel.add(bpmOutputLabel);
		viewPanel.add(bpmPanel);
		viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
		viewFrame.pack();
		viewFrame.setVisible(true);
	}
	
	// setup controls
	public void createControls() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		controlFrame = new JFrame("Control");
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlFrame.setSize(new Dimension(100, 80));
		
		controlPanel = new JPanel(new GridLayout(1, 2));
		
		menuBar = new JMenuBar();
		menu = new JMenu("DJ Control");
		startMenuItem = new JMenuItem("Start");
		menu.add(startMenuItem);
		startMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.start();
			}
		});
		startMenuItem = new JMenuItem("Stop");
		menu.add(stopMenuItem);
		stopMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.stop();
			}
		});
		JMenuItem exit = new JMenuItem("Quit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(exit);
		menuBar.add(menu);
		controlFrame.setJMenuBar(menuBar);
		
		bpmTextField = new JTextField(2);
		bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);
		setBPMButton = new JButton("Set");
		setBPMButton.setSize(new Dimension(10, 40));
		increaseBPMButton = new JButton(">>");
		decreaseBPMButton = new JButton("<<");
		setBPMButton.addActionListener(this);
		increaseBPMButton.addActionListener(this);
		decreaseBPMButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		
		buttonPanel.add(decreaseBPMButton);
		buttonPanel.add(increaseBPMButton);
		
		JPanel enterPanel = new JPanel(new GridLayout(1, 2));
		enterPanel.add(bpmLabel);
		enterPanel.add(bpmTextField);
		JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
		insideControlPanel.add(enterPanel);
		insideControlPanel.add(setBPMButton);
		insideControlPanel.add(buttonPanel);
		controlPanel.add(insideControlPanel);
		
		bpmLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		controlFrame.getRootPane().setDefaultButton(setBPMButton);
		controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);
		
		controlFrame.pack();
		controlFrame.setVisible(true);
		
	}
	
	// control controls
	public void enableStopMenuItem() {
		stopMenuItem.setEnabled(true);
	}
	public void disableStopMenuItem() {
		stopMenuItem.setEnabled(false);
	}
	public void enableStartMenuItem() {
		startMenuItem.setEnabled(true);
	}
	public void disableStartMenuItem() {
		startMenuItem.setEnabled(false);
	}
	
	public void updateBPM() {
		int bpm = model.getBPM();
		if (bpm == 0) {
			bpmOutputLabel.setText("offline");
		} else {
			bpmOutputLabel.setText("Current BPM: " + model.getBPM());
		}
	}


	@Override
	public void updateBeat() {
		beatBar.setValue(100);		
	}

	
	@Override
	/***
	 * gets called when a button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == setBPMButton) {
			int bpm = Integer.parseInt(bpmTextField.getText());
			controller.setBPM(bpm);
		} else if (e.getSource() == increaseBPMButton) {
			controller.increaseBPM();
		} else if (e.getSource() == decreaseBPMButton) {
			controller.decreaseBPM();
		}
	}

}
