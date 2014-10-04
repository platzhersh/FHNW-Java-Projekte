package ch.fhnw.edu.efficientalgorthims.sudoku;

import java.util.List;

/**
 * Listener Interface for receiving events form GUI components.
 * 
 * @author Martin Schaub
 */
public interface SudokuGUIListener {

	/**
	 * This event is triggered, when a new board is loaded.
	 * 
	 * @param newBoard the newly loaded board.
	 */
	void boardLoaded(SudokuBoard newBoard);

	/**
	 * This event is fired, after the users selects a new board.
	 * 
	 * @param board newly selected board
	 */
	void boardSelected(SudokuBoard board);

	/**
	 * The solvingStarted method is called after the calculation was started.
	 */
	void solvingStarted();

	/**
	 * This event is triggered after the calculation has been finished and the results are available.
	 * 
	 * @param solution the calculated results
	 */
	void solvingEnded(List<SudokuBoard> solution);
}
