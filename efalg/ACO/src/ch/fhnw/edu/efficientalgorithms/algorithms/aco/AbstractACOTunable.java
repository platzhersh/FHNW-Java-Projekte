package ch.fhnw.edu.efficientalgorithms.algorithms.aco;

import java.util.Arrays;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.algorithms.AbstractTSPAlgorithmTunable;

/**
 * Stores the tunable options for all attributes in the AbstractACOAlgorithm. Its implemented abstract since it has a
 * special behavior in returning not found attributes.
 * 
 * @author Martin Schaub
 * 
 * @param <T> concrete type
 */
public abstract class AbstractACOTunable<T extends AbstractACOAlgorithm> extends AbstractTSPAlgorithmTunable<T> {

	/**
	 * Return value if the attribute was not found.
	 */
	protected final static String NOT_FOUND = "NOT FOUND";

	/**
	 * Constant for the number of runs.
	 */
	private static final String NUMOFRUNS = "number of runs";
	/**
	 * Constant for the number of ants per node.
	 */
	private static final String NUMOFNATS = "ants per node";
	/**
	 * Constant for pheromoneQuantifier
	 */
	private static final String PHEROMONEQUANTIFIER = "pheromone quantifier";
	/**
	 * Constant for heuristicQuantifier.
	 */
	private static final String HEURISTICQUANTIFIER = "heuristic quantifier";
	/**
	 * Constant for the initial pheromone level.
	 */
	private static final String INITIALPHEROMONE = "initial pheromnoe";
	/**
	 * Constant on how long the process waits until it produces a new solution.
	 */
	private static final String SLEEPPERIODE = "wait [ns]";
	/**
	 * Constant for the elite bypass property.
	 */
	private static final String USEELITEBYPASS = "elite bypass";

	/**
	 * Constructor
	 * 
	 * @param algorithm algorithm to pass to the base class
	 */
	public AbstractACOTunable(final T algorithm) {
		super(algorithm);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable#getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		return Arrays.asList(NUMOFNATS, NUMOFRUNS, PHEROMONEQUANTIFIER, HEURISTICQUANTIFIER, INITIALPHEROMONE,
				SLEEPPERIODE, USEELITEBYPASS);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable#getValue(java.lang.String)
	 */
	@Override
	public String getValue(final String attribute) {
		if (attribute.equals(NUMOFRUNS)) {
			return Integer.toString(getAlgorithm().getNumberOfRuns());
		}
		else if (attribute.equals(NUMOFNATS)) {
			return Double.toString(getAlgorithm().getAntNodeRatio());
		}
		else if (attribute.equals(PHEROMONEQUANTIFIER)) {
			return Double.toString(getAlgorithm().getPheromoneQuantifier());
		}
		else if (attribute.equals(HEURISTICQUANTIFIER)) {
			return Double.toString(getAlgorithm().getHeuristicQuantifier());
		}
		else if (attribute.equals(INITIALPHEROMONE)) {
			return Double.toString(getAlgorithm().getInitialPheromone());
		}
		else if (attribute.equals(SLEEPPERIODE)) {
			return Integer.toString(getAlgorithm().getSleepPeriode());
		}
		else if (attribute.equals(USEELITEBYPASS)) {
			return Boolean.toString(getAlgorithm().isUseEliteByPass());
		}

		return NOT_FOUND;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmTunable#valueChanged(java.lang.String, java.lang.String)
	 */
	@Override
	public String valueChanged(final String attribute, final String newValue) {
		if (attribute.equals(NUMOFRUNS)) {
			try {
				int num = Integer.parseInt(newValue);
				if (num <= 0) {
					return "Expected an integer number bigger than zero in number of runs field";
				}
				getAlgorithm().setNumberOfRuns(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected an integer number bigger than zero in number of runs field";
			}
		}
		else if (attribute.equals(NUMOFNATS)) {
			try {
				double num = Double.parseDouble(newValue);
				if (num <= 0) {
					return "Expected a positive number";
				}
				getAlgorithm().setAntNodeRatio(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected an positive double value for the number of ants per node";
			}
		}
		else if (attribute.equals(PHEROMONEQUANTIFIER)) {
			try {
				double num = Double.parseDouble(newValue);
				getAlgorithm().setPheromoneQuantifier(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected a number in the pheromone quantifier field";
			}
		}
		else if (attribute.equals(HEURISTICQUANTIFIER)) {
			try {
				double num = Double.parseDouble(newValue);
				getAlgorithm().setHeuristicQuantifier(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected a number in the heuristic quantifier field";
			}
		}
		else if (attribute.equals(INITIALPHEROMONE)) {
			try {
				double num = Double.parseDouble(newValue);
				getAlgorithm().setInitialPheromone(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected a number in the initial pheromone field";
			}
		}
		else if (attribute.equals(SLEEPPERIODE)) {
			try {
				int num = Integer.parseInt(newValue);
				if (num <= 0) {
					return "Expected an integer number bigger than zero in sleep periode field";
				}
				getAlgorithm().setSleepPeriode(num);
				notifyObserversChange(attribute);
				return null;
			}
			catch (NumberFormatException e) {
				return "Expected an integer number bigger than zero in sleep periode field";
			}
		}
		else if (attribute.equals(USEELITEBYPASS)) {
			getAlgorithm().setUseEliteByPass(Boolean.parseBoolean(newValue));
			notifyObserversChange(attribute);
			return null;
		}

		return NOT_FOUND;
	}
}
