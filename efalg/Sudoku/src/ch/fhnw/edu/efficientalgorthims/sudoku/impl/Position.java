package ch.fhnw.edu.efficientalgorthims.sudoku.impl;

/**
 * Represents a board position.
 * 
 * @author Martin Schaub
 */
public final class Position {

	/**
	 * Which sudoku board.
	 */
	private final int fieldNumber;
	/**
	 * Which position on the sudoku board.
	 */
	private final int positionNumber;

	/**
	 * Constructor
	 * 
	 * @param fieldNumber which sudoku board
	 * @param positionNumber which position on the sudoku board
	 */
	public Position(final int fieldNumber, final int positionNumber) {
		if (fieldNumber < 0 || fieldNumber > 8) {
			throw new IllegalArgumentException("Fieldnumber is not valid");
		}
		if (positionNumber < 0 || positionNumber > 8) {
			throw new IllegalArgumentException("Positionnumber is not valid");
		}
		this.fieldNumber = fieldNumber;
		this.positionNumber = positionNumber;
	}

	/**
	 * Getter method for the fieldNumber property.
	 * 
	 * @return the fieldNumber property's value
	 */
	public final int getFieldNumber() {
		return fieldNumber;
	}

	/**
	 * Getter method for the positionNumber property.
	 * 
	 * @return the positionNumber property's value
	 */
	public final int getPositionNumber() {
		return positionNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return fieldNumber + "/" + positionNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fieldNumber;
		result = prime * result + positionNumber;
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
		Position other = (Position) obj;
		if (fieldNumber != other.fieldNumber) {
			return false;
		}
		if (positionNumber != other.positionNumber) {
			return false;
		}
		return true;
	}

}
