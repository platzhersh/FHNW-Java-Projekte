package ch.fhnw.swent.exceptionhandling.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ch.fhnw.swent.exceptionhandling.sensor.ISensorComponent;


public class GUI extends JPanel implements ISensorComponent.StatisticListener {

	private class StartAction extends AbstractAction {

		public void actionPerformed(ActionEvent evt) {
			try {
			  if (evt.getSource() == start) {
			    controller.startContinuousRead();
			  } else if (evt.getSource() == stop) {
			    controller.stopContinuousRead();
			  } else if (evt.getSource() == timesTextField) {
			    int times = Integer.parseInt(timesTextField.getText());
			    controller.setReadRepeat(times);
			  } else if (evt.getSource() == intervalTextField) {
			    int interval = Integer.parseInt(intervalTextField.getText());
			    controller.setReadInterval(interval);
			  } else {
			    controller.readSensor();
			  }
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	private JPanel south = new JPanel();
	private JPanel center = new JPanel();
	private Box east;

  private JLabel timesLabel = new JLabel("Number of reads: ");
  private JTextField timesTextField = new JTextField(10);
  private JLabel intervalLabel = new JLabel("Interval between reads (in ms): ");
  private JTextField intervalTextField = new JTextField(10);
	

	private JLabel maxLabel = new JLabel("Max: ");
	private JLabel minLabel = new JLabel("Min: ");
	private JLabel avgLabel = new JLabel("Avg: ");
	private JLabel maxValue = new JLabel("10.0");
	private JLabel minValue = new JLabel("0.0");
	private JLabel avgValue = new JLabel("0.0");

	private JButton start = new JButton("Start");
	private JButton stop = new JButton("Stop");
	private JButton read = new JButton("Read now");

	private ISensorComponent controller;
	private StartAction startaction = new StartAction();

	protected GUI(ISensorComponent controller) {
		this.controller = controller;
		controller.addStatisticListener(this);
		initLayout();
		initFunctionality();
	}

	public int getNumberOfReads() {
		return Integer.parseInt(timesTextField.getText());
	}

	private void initFunctionality() {
		start.addActionListener(startaction);
    stop.addActionListener(startaction);
    read.addActionListener(startaction);
    timesTextField.addActionListener(startaction);
    intervalTextField.addActionListener(startaction);
	}

	private void initLayout() {
		this.setLayout(new BorderLayout());
		center.setLayout(new FlowLayout());
		south.setLayout(new FlowLayout());
				
		center.add(read);
		center.add(start);
		center.add(stop);

		Box southleft = Box.createVerticalBox();
		Box southright = Box.createVerticalBox();
		timesTextField.addActionListener(startaction);
		timesTextField.setText(Integer.toString(controller.getReadRepeats()));
		intervalTextField.addActionListener(startaction);
		intervalTextField.setText(Integer.toString(controller.getReadInterval()));
		
		southleft.add(timesLabel);
		southleft.add(intervalLabel);
		southright.add(timesTextField);
		southright.add(intervalTextField);
    south.add(southleft);
    south.add(southright);

		east = Box.createHorizontalBox();
		east.setBorder(new LineBorder(Color.BLACK, 2, true));
		Box eastleft = Box.createVerticalBox();
		Box eastright = Box.createVerticalBox();
		east.add(eastleft);
		east.add(eastright);

		eastleft.add(maxLabel);
		eastleft.add(minLabel);
		eastleft.add(avgLabel);
		eastright.add(maxValue);
		eastright.add(minValue);
		eastright.add(avgValue);

		this.add(south, BorderLayout.SOUTH);
		this.add(east, BorderLayout.EAST);
		this.add(center, BorderLayout.CENTER);
	}

	public void update(double min, double max, double avg) {
		minValue.setText(Double.toString(min));
		maxValue.setText(Double.toString(max));
		avgValue.setText(Double.toString(avg));
	}

}
