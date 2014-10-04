package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Point;

/**
 * Updates a JMenuItem to display whether a calculation is currently in progress or not.
 * 
 * @author Martin Schaub
 */
public class StatusAction extends AbstractAction implements AlgorithmListener {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -7673956528898399905L;
	/**
	 * MenuItem to update.
	 */
	private final JMenuItem item;

	/**
	 * Constructor
	 * 
	 * @param item item to update
	 */
	public StatusAction(final JMenuItem item) {
		if (item == null) {
			throw new NullPointerException();
		}
		this.item = item;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#currentCoordinate(ch.fhnw.edu.efficientalgorithms
	 * .lineintersection.Point)
	 */
	@Override
	public void currentCoordinate(final Point point) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#processEnded()
	 */
	@Override
	public void processEnded() {
		item.setText("Calculation finished");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#processStarted()
	 */
	@Override
	public void processStarted() {
		item.setText("Calculation started");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.lineintersection.AlgorithmListener#reportObject(java.lang.Object)
	 */
	@Override
	public void reportObject(final Object object) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// Do nothing
	}

}
