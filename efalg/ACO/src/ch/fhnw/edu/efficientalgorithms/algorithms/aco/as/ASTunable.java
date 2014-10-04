package ch.fhnw.edu.efficientalgorithms.algorithms.aco.as;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOTunable;

/**
 * Tunable parameters of the ant system.
 * 
 * @author Martin Schaub
 */
public final class ASTunable extends AbstractACOTunable<ASAlgorithm> {

	/**
	 * Evaaporation rate.
	 */
	private final static String EVAPRATION = "evaporation";

	/**
	 * Constructor
	 * 
	 * @param algorithm ant system algorithm
	 */
	public ASTunable(final ASAlgorithm algorithm) {
		super(algorithm);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOTunable#getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		List<String> ret = new LinkedList<String>(Arrays.asList(EVAPRATION));
		ret.addAll(super.getAttributes());
		return ret;
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

		if (attribute.equals(EVAPRATION)) {
			return Double.toString(getAlgorithm().getEvaporation());
		}
		throw new InternalError("Internal error, attribute " + attribute + " is not known");
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

		if (attribute.equals(EVAPRATION)) {
			try {
				double num = Double.parseDouble(newValue);
				if (num <= 0 || num > 1) {
					return "Expected a number in the intervall (0,1]";
				}
				getAlgorithm().setEvaporation(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected a number in the intervall [(,1]";
			}
		}

		throw new InternalError("Internal error, attribute " + attribute + " is not known");
	}

}
