package ch.fhnw.edu.efficientalgorthims.sudoku;

import java.util.List;

/**
 * Solves a sudoku example.
 * 
 * @author Martin Schaub
 */
public interface SudokuSolver {

	/**
	 * Solves the input sudoku board and produce all possible solutions. The input board mustn't be modified. After
	 * returning the list of boards, this list mustn't be modified from implementations nor a implementation should depend
	 * that a list is the same in the next call.
	 * 
	 * @param input input board to solve
	 * @return solved output boards. This is saved in a list, because a persistent order might be needed. Additionally the
	 *         correctness of a implementation can be checked better, because it might find multiple times the same
	 *         solution!
	 */
	List<SudokuBoard> solve(SudokuBoard input);

}
