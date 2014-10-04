package ch.fhnw.edu.efficientalgorithms.algorithms.aco;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.fhnw.edu.efficientalgorithms.algorithms.AbstractTSPAlgorithm;
import ch.fhnw.edu.efficientalgorithms.impl.TSPSolutionImpl;
import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;

/**
 * Stores common implementations for different ant colony optimization techniques.
 *
 * @author Martin Schaub
 */
public abstract class AbstractACOAlgorithm extends AbstractTSPAlgorithm {

	/**
	 * How long the algorithm waits after publishing an solution.
	 */
	private int sleepPeriode = 500;
	/**
	 * How many ants are created per node in mean.
	 */
	private double antNodeRatio = 1;
	/**
	 * Number of runs.
	 */
	private int numberOfRuns = 100;
	/**
	 * How much heuristic informations come to account.
	 */
	private double heuristicQuantifier = 2;
	/**
	 * How much pheromone informations come to account.
	 */
	private double pheromoneQuantifier = 1;
	/**
	 * Initial pheromone level.
	 */
	private double initialPheromone = 100;
	/**
	 * Use elite by pass
	 */
	private boolean useEliteByPass = true;

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm#start(ch.fhnw.edu.efficientalgorithms.tsp.TSPModel)
	 */
	@Override
	public final void start(final TSPModel model) {
		if (model.getLocations().size() > 1) {
			Map<Location, Map<Location, Double>> pheromoneLevel = initPheromone(model);

			Ant best = null;
			for (int i = 0; i < getNumberOfRuns(); ++i) {
				List<Ant> ants = constructAntSolutions(model, pheromoneLevel);
				if (useEliteByPass) {
					if (best == null || ants.get(0).getPathLength() < best.getPathLength()) {
						best = ants.get(0);
					}
					else {
						ants.add(0, best);
					}
				}

				updatePheromones(ants, pheromoneLevel);
				model.setSolution(new TSPSolutionImpl(ants.get(0).getPath(), ants.get(0).getPathLength(), i + 1));

				// Wait
				try {
					Thread.sleep(sleepPeriode);
				}
				catch (InterruptedException e) {
					// nothing to do
				}
			}
		}
	}

	/**
	 * Creates ants and let them run.
	 *
	 * @param model TSP model
	 * @param pheromoneLevel current pheromoneLevel
	 *
	 * @return a sorted list of all ants
	 */
	protected abstract List<Ant> constructAntSolutions(final TSPModel model,
			final Map<Location, Map<Location, Double>> pheromoneLevel);

	/**
	 * Perform a global update on the path.
	 *
	 * @param ants sorted list of ants (first one is the one with the smallest distance)
	 * @param pheromoneLevel stores all pheromene Levels
	 */
	protected abstract void updatePheromones(List<Ant> ants, Map<Location, Map<Location, Double>> pheromoneLevel);

	/**
	 * Initializes a map for storing the pheromone levels.
	 *
	 * @param model model to get all locations
	 * @return a map for storing the pheromone levels
	 */
	private Map<Location, Map<Location, Double>> initPheromone(final TSPModel model) {
		int n = model.getLocations().size();
		Map<Location, Map<Location, Double>> pheromoneLevel = new HashMap<Location, Map<Location, Double>>(2 * n);
		for (Location loc : model.getLocations()) {
			Map<Location, Double> map = new HashMap<Location, Double>(2 * n);
			pheromoneLevel.put(loc, map);
			for (Location other : model.getLocations()) {
				if (!other.equals(loc)) {
					map.put(other, initialPheromone);
				}
			}
		}
		return pheromoneLevel;
	}

	/**
	 * Getter method for the numberOfRuns property.
	 *
	 * @return the numberOfRuns property's value
	 */
	public final int getNumberOfRuns() {
		return numberOfRuns;
	}

	/**
	 * Setter method for the numberOfRuns property.
	 *
	 * @param numberOfRuns the new numberOfRuns to set
	 */
	public final void setNumberOfRuns(final int numberOfRuns) {
		if (numberOfRuns <= 0) {
			throw new IllegalArgumentException();
		}
		this.numberOfRuns = numberOfRuns;
	}

	/**
	 * Getter method for the antNodeRatio property.
	 *
	 * @return the antNodeRatio property's value
	 */
	public final double getAntNodeRatio() {
		return antNodeRatio;
	}

	/**
	 * Setter method for the antNodeRatio property.
	 *
	 * @param antNodeRatio the new antNodeRatio to set
	 */
	public final void setAntNodeRatio(final double antNodeRatio) {
		this.antNodeRatio = antNodeRatio;
	}

	/**
	 * Getter method for the heuristicQuantifier property.
	 *
	 * @return the heuristicQuantifier property's value
	 */
	public final double getHeuristicQuantifier() {
		return heuristicQuantifier;
	}

	/**
	 * Setter method for the heuristicQuantifier property.
	 *
	 * @param heuristicQuantifier the new heuristicQuantifier to set
	 */
	public final void setHeuristicQuantifier(final double heuristicQuantifier) {
		this.heuristicQuantifier = heuristicQuantifier;
	}

	/**
	 * Getter method for the pheromoneQuantifier property.
	 *
	 * @return the pheromoneQuantifier property's value
	 */
	public final double getPheromoneQuantifier() {
		return pheromoneQuantifier;
	}

	/**
	 * Setter method for the pheromoneQuantifier property.
	 *
	 * @param pheromoneQuantifier the new pheromoneQuantifier to set
	 */
	public final void setPheromoneQuantifier(final double pheromoneQuantifier) {
		this.pheromoneQuantifier = pheromoneQuantifier;
	}

	/**
	 * Getter method for the initialPheromone property.
	 *
	 * @return the initialPheromone property's value
	 */
	public final double getInitialPheromone() {
		return initialPheromone;
	}

	/**
	 * Setter method for the initialPheromone property.
	 *
	 * @param initialPheromone the new initialPheromone to set
	 */
	public final void setInitialPheromone(final double initialPheromone) {
		this.initialPheromone = initialPheromone;
	}

	/**
	 * Getter method for the sleepPeriode property.
	 *
	 * @return the sleepPeriode property's value
	 */
	public final int getSleepPeriode() {
		return sleepPeriode;
	}

	/**
	 * Setter method for the sleepPeriode property.
	 *
	 * @param sleepPeriode the new sleepPeriode to set
	 */
	public final void setSleepPeriode(final int sleepPeriode) {
		if (sleepPeriode < 0) {
			throw new IllegalArgumentException();
		}
		this.sleepPeriode = sleepPeriode;
	}

	/**
	 * Getter method for the useEliteByPass property.
	 *
	 * @return the useEliteByPass property's value
	 */
	public final boolean isUseEliteByPass() {
		return useEliteByPass;
	}

	/**
	 * Setter method for the useEliteByPass property.
	 *
	 * @param useEliteByPass the new useEliteByPass to set
	 */
	public final void setUseEliteByPass(final boolean useEliteByPass) {
		this.useEliteByPass = useEliteByPass;
	}

}
