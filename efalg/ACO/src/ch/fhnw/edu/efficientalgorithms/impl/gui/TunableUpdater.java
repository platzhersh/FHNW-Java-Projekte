package ch.fhnw.edu.efficientalgorithms.impl.gui;

import javax.swing.JTextField;

import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunableListener;

/**
 * Updates a textfield if the underlying tunable was changed.
 * 
 * @author Martin Schaub
 */
public final class TunableUpdater implements TSPAlgorithmTunableListener {

	/**
	 * The observed attribute.
	 */
	private final String observedAttribute;
	/**
	 * Textfield which is changed in case a change is received
	 */
	private final JTextField toUpdate;
	/**
	 * Tunable where the value can be gathered.
	 */
	private final TSPAlgorithmTunable tunable;

	/**
	 * Constructor
	 * 
	 * @param observedAttribute attribute to observe
	 * @param toUpdate textfield to update
	 * @param tunable tunable to get the actual value
	 */
	public TunableUpdater(final String observedAttribute, final JTextField toUpdate, final TSPAlgorithmTunable tunable) {
		if (observedAttribute == null || toUpdate == null || tunable == null) {
			throw new NullPointerException();
		}
		this.observedAttribute = observedAttribute;
		this.toUpdate = toUpdate;
		this.tunable = tunable;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunableListener#tunableChanged(java.lang.String)
	 */
	@Override
	public void tunableChanged(final String attribute) {
		if (attribute.equals(observedAttribute)) {
			toUpdate.setText(tunable.getValue(attribute));
		}
	}
}
