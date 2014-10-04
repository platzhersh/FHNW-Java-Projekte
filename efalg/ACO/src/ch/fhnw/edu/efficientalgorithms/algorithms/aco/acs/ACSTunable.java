package ch.fhnw.edu.efficientalgorithms.algorithms.aco.acs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOTunable;

/**
 * Tunes the ACS algorithm.
 * 
 * @author Martin Schaub
 */
public final class ACSTunable extends AbstractACOTunable<ACSAlgorithm> {

	/**
	 * Constant for local evaporation.
	 */
	private static final String EVAPORATIONLOCAL = "local evaporation";
	/**
	 * Constant for global evaporation.
	 */
	private static final String EVAPORATIONGLOBAL = "global evaporation";

	/**
	 * Constructor
	 * 
	 * @param algorithm algorithm to tune
	 */
	public ACSTunable(final ACSAlgorithm algorithm) {
		super(algorithm);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOTunable#getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		List<String> ret = new LinkedList<String>(Arrays.asList(EVAPORATIONLOCAL, EVAPORATIONGLOBAL));
		ret.addAll(super.getAttributes());
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOTunable#valueChanged(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String valueChanged(final String attribute, final String newValue) {
		String ret = super.valueChanged(attribute, newValue);
		if (ret == null || !ret.equals(NOT_FOUND)) {
			return ret;
		}

		if (attribute.equals(EVAPORATIONLOCAL)) {
			try {
				double num = Double.parseDouble(newValue);
				if (num < 0 || num > 1) {
					return "Expected a number in the intervall [0,1]";
				}
				getAlgorithm().setEvaporationLocal(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected a number in the intervall [0,1]";
			}
		}
		if (attribute.equals(EVAPORATIONGLOBAL)) {
			try {
				double num = Double.parseDouble(newValue);
				if (num < 0 || num > 1) {
					return "Expected a number in the intervall [0,1]";
				}
				getAlgorithm().setEvaporationGlobal(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected a number in the intervall [0,1]";
			}
		}
		throw new InternalError("Internal error, attribute " + attribute + " is not known");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOTunable#getValue(java.lang.String)
	 */
	@Override
	public String getValue(final String attribute) {
		String str = super.getValue(attribute);
		if (!str.equals(NOT_FOUND)) {
			return str;
		}

		if (attribute.equals(EVAPORATIONLOCAL)) {
			return Double.toString(getAlgorithm().getEvaporationLocal());
		}
		else if (attribute.equals(EVAPORATIONGLOBAL)) {
			return Double.toString(getAlgorithm().getEvaporationGlobal());
		}
		throw new InternalError("Internal error, attribute " + attribute + " is not known");
	}
}
