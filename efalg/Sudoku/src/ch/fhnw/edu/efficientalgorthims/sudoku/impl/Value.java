package ch.fhnw.edu.efficientalgorthims.sudoku.impl;

/**
 * Represents a board value. This class is immutable.
 * 
 * @author Martin Schaub
 */
public final class Value {

	/**
	 * The number it represents.
	 */
	private final int value;

	/**
	 * Constructor for setting a value that represents the number.
	 * 
	 * @param value value to set
	 */
	public Value(final int value) {
		if (value < 1 || value > 9) {
			throw new IllegalArgumentException("Only numbers between 1 and 9 are allowed");
		}
		this.value = value;
	}

	/**
	 * Constructor for initializing a empty field.
	 */
	public Value() {
		value = 0;
	}

	/**
	 * Gets back the number it represents. There must be a numeric value set or a IllegalStateExeption is thrown.
	 * 
	 * @return the value property's value
	 */
	public final int getValue() {
		if (isEmpty()) {
			throw new IllegalStateException("No value is set!");
		}
		return value;
	}

	/**
	 * Checks whether the field is empty or not.
	 * 
	 * @return true, if the board value is set or false otherwise.
	 */
	public final boolean isEmpty() {
		return value == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isEmpty()) {
			return " ";
		}
		return Integer.toString(value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		Value other = (Value) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}

}
