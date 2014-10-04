package ch.fhnw.edu.efficientalgorithms.algorithms.aco.as;

import java.util.List;
import java.util.Map;

import ch.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOAlgorithm;
import ch.fhnw.edu.efficientalgorithms.algorithms.aco.Ant;
import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;

/**
 * Implementation of the ant colony optimization as ant system (AS).
 *
 * http://www.scholarpedia.org/article/Ant_colony_optimization
 *
 * @author Martin Schaub
 */
public final class ASAlgorithm extends AbstractACOAlgorithm {

	/**
	 * Evaporation rate.
	 */
	private double evaporation = 0.5;

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm#allowsModification()
	 */
	@Override
	public boolean allowsModification() {
		return false;
	}

	/**
	 * Getter method for the evaporation property.
	 *
	 * @return the evaporation property's value
	 */
	public double getEvaporation() {
		return evaporation;
	}

	/**
	 * Setter method for the evaporation property.
	 *
	 * @param evaporation the new evaporation to set
	 */
	public void setEvaporation(final double evaporation) {
		if (evaporation <= 0 || evaporation > 1) {
			throw new IllegalArgumentException();
		}
		this.evaporation = evaporation;
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
	 * @seech.fhnw.edu.efficientalgorithms.algorithms.aco.AbstractACOAlgorithm#constructAntSolutions(ch.fhnw.edu.
	 * efficientalgorithms.tsp.TSPModel, java.util.Map)
	 */
	@Override
	protected List<Ant> constructAntSolutions(TSPModel model, Map<Location, Map<Location, Double>> pheromoneLevel) {

		// TODO Implementieren Sie hier Ihren Code.

		return null;
	}

}
