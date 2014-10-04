package ch.fhnw.edu.efficientalgorithms.algorithms.aco.acs;

import java.util.List;
import java.util.Map;

import ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOAlgorithm;
import ch.fhnw.edu.efficientalgorithms.algorithms.aco.Ant;
import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;

/**
 * Implementation of the ant colony optimization as ant colony system (ACS).
 *
 * http://www.scholarpedia.org/article/Ant_colony_optimization
 *
 * @author Martin Schaub
 */
public final class ACSAlgorithm extends AbstractACOAlgorithm {

	/**
	 * Used evaporation for local update.
	 */
	private double evaporationLocal = 0.5;
	/**
	 * Used evaporation for global update.
	 */
	private double evaporationGlobal = 0.75;

	/**
	 * Getter method for the evaporationGlobal property.
	 *
	 * @return the evaporationGlobal property's value
	 */
	public double getEvaporationGlobal() {
		return evaporationGlobal;
	}

	/**
	 * Setter method for the evaporationGlobal property.
	 *
	 * @param evaporationGlobal the new evaporationGlobal to set
	 */
	public void setEvaporationGlobal(final double evaporationGlobal) {
		this.evaporationGlobal = evaporationGlobal;
	}

	/**
	 * Getter method for the evaporationLocal property.
	 *
	 * @return the evaporationLocal property's value
	 */
	public double getEvaporationLocal() {
		return evaporationLocal;
	}

	/**
	 * Setter method for the evaporationLocal property.
	 *
	 * @param evaporationLocal the new evaporationLocal to set
	 */
	public void setEvaporationLocal(final double evaporationLocal) {
		if (evaporationLocal < 0 || evaporationLocal > 1) {
			throw new IllegalArgumentException();
		}
		this.evaporationLocal = evaporationLocal;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm#allowsModification()
	 */
	@Override
	public boolean allowsModification() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOAlgorithm#updatePheromones(java.util.List,
	 * java.util.Map)
	 */
	@Override
	protected void updatePheromones(List<Ant> ants, Map<Location, Map<Location, Double>> pheromoneLevel) {

		// TODO Implementieren Sie hier Ihren Code.
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOAlgorithm#constructAntSolutions(ch.fhnw.edu.efficientalgorithms.tsp.TSPModel, java.util.Map)
	 */
	@Override
	protected List<Ant> constructAntSolutions(TSPModel model, Map<Location, Map<Location, Double>> pheromoneLevel) {

	// TODO Implementieren Sie hier Ihren Code.

		return null;
	}

}
