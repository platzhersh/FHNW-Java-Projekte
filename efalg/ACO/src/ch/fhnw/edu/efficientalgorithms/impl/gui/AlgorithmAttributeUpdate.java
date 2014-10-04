package ch.fhnw.edu.efficientalgorithms.impl.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable;

/**
 * Updates an algorithm if new values are available.
 * 
 * @author Martin Schaub
 */
public final class AlgorithmAttributeUpdate implements ActionListener, FocusListener {

	/**
	 * To update.
	 */
	private final TSPAlgorithmTunable tunable;
	/**
	 * Attribute to update.
	 */
	private final String attribute;
	/**
	 * To get the value from.
	 */
	private final JTextField field;

	/**
	 * Constructor
	 * 
	 * @param tunable to update
	 * @param attribute attribute to update;
	 * @param field go get the value from
	 */
	public AlgorithmAttributeUpdate(final TSPAlgorithmTunable tunable, final String attribute, final JTextField field) {
		if (tunable == null || attribute == null || field == null) {
			throw new NullPointerException();
		}
		this.tunable = tunable;
		this.attribute = attribute;
		this.field = field;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		update();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(final FocusEvent e) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(final FocusEvent e) {
		update();
	}

	/**
	 * Updates the algorithm.
	 */
	private void update() {
		String ret = tunable.valueChanged(attribute, field.getText());
		if (ret != null) {
			JOptionPane.showMessageDialog(null, ret, "Error setting value " + attribute, JOptionPane.OK_OPTION);
			field.setText(tunable.getValue(attribute));
		}
	}
}
