package ch.fhnw.edu.efficientalgorithms.algorithms.mstheuristic;

import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.algorithms.AbstractTSPAlgorithmTunable;

/**
 * Tunable class for the two comparative ratio heuristic.
 * 
 * @author Martin Schaub
 */
public final class TwoComparativeTunable extends AbstractTSPAlgorithmTunable<TwoComparativeAlgorithm> {

	/**
	 * Constructor
	 * 
	 * @param algorithm algorithm to tune
	 */
	public TwoComparativeTunable(final TwoComparativeAlgorithm algorithm) {
		super(algorithm);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable#getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		return new LinkedList<String>();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable#valueChanged(java.lang.String, java.lang.String)
	 */
	@Override
	public String valueChanged(final String attribute, final String newValue) {
		return "Internal error, attribute " + attribute + " is not known";
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable#getValue(java.lang.String)
	 */
	@Override
	public String getValue(final String attribute) {
		return "Internal error, attribute " + attribute + " is not known";
	}

}
