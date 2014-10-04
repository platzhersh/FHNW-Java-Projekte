package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Function;

/**
 * Represents a polynomial.
 * 
 * @author Martin Schaub
 */
public final class Polynomial implements Function {

	/**
	 * Polynom's coefficient.
	 */
	private final List<Double> coefficients;

	/**
	 * Constructor
	 * 
	 * @param coefficients coefficients to evaluate
	 */
	public Polynomial(final List<Double> coefficients) {
		if (coefficients == null) {
			throw new NullPointerException();
		}
		this.coefficients = new ArrayList<Double>(coefficients);
	}

	/**
	 * Uses Horner's schema to evaluate the polynomial
	 */
	@Override
	public double getValueAt(final double x) {
		if (coefficients.size() == 0) {
			return 0;
		}

		double res = coefficients.get(coefficients.size() - 1);
		for (int i = coefficients.size() - 2; i >= 0; i--) {
			res = res * x + coefficients.get(i);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Polynomial clone() {
		try {
			// The list mustn't be copied, because it is not modified
			return (Polynomial) super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coefficients == null) ? 0 : coefficients.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Polynomial other = (Polynomial) obj;
		if (coefficients == null) {
			if (other.coefficients != null) {
				return false;
			}
		}
		else if (!coefficients.equals(other.coefficients)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		Iterator<Double> it = coefficients.iterator();
		if (it.hasNext()) {
			buf.append(it.next());
			int i = 1;
			while (it.hasNext()) {
				buf.append(" + ");
				buf.append(it.next());
				buf.append("*x^" + i);
				++i;
			}
		}

		return buf.toString();
	}

}
