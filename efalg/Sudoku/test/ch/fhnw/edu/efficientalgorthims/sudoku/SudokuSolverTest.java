package ch.fhnw.edu.efficientalgorthims.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import ch.fhnw.edu.efficientalgorthims.sudoku.impl.Position;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.SudokuBacktrackSolver;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.SudokuBoardImpl;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.Value;

/**
 * Tests implementations according to some testcases. To be able to run this tests, JUnit is required.
 * 
 * @author Martin Schaub
 */
public class SudokuSolverTest {

	/**
	 * Reads a board from a file name.
	 * 
	 * @param filename filename that stores the data
	 * @return generated board
	 */
	private SudokuBoard readFile(final String filename) {
		try {
			SudokuBoard board = new SudokuBoardImpl();
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String str = null;
			int i = 0;
			while ((str = in.readLine()) != null) {
				String[] line = str.split("\\s+");
				for (int j = 0; j < line.length; ++j) {
					if (line[j].length() != 1) {
						throw new RuntimeException("Error on line " + i);
					}

					if (Character.isDigit(line[j].charAt(0))) {
						board
								.setValue(new Position(j / 3 + (i / 3) * 3, j % 3 + (i % 3) * 3), new Value(Integer.parseInt(line[j])));
					}
				}
				i++;
			}
			in.close();

			return board;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Private helper method that searches a list for duplicates.
	 * 
	 * @param solutions list to check
	 * @return true if the list has duplicates and else otherwise.
	 */
	private boolean hasDuplicates(final List<Integer> solutions) {
		for (int i = 0; i < solutions.size(); ++i) {
			for (int j = i + 1; j < solutions.size(); ++j) {
				if (solutions.get(i).equals(solutions.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Generates a new solver for testing.
	 * 
	 * @return new solver
	 */
	private SudokuSolver getSolver() {
		return new SudokuBacktrackSolver();
	}

	/**
	 * Validates solutions of a sudoku board.
	 * 
	 * @param solutions solutions to validate
	 */
	private void validate(final List<SudokuBoard> solutions) {
		Set<SudokuBoard> solutionSet = new HashSet<SudokuBoard>(solutions);
		assertEquals(solutionSet.size(), solutions.size());

		for (SudokuBoard board : solutions) {
			for (int i = 0; i < 9; ++i) {
				assertFalse(hasDuplicates(board.getRow(i)));
				assertEquals(board.getRow(i).size(), 9);
				assertFalse(hasDuplicates(board.getColumn(i)));
				assertEquals(board.getColumn(i).size(), 9);
				assertFalse(hasDuplicates(board.getField(i)));
				assertEquals(board.getField(i).size(), 9);
			}
		}
	}

	/**
	 * Checks whether a output really matches the input.
	 * 
	 * @param input input board
	 * @param solutions solutions for input
	 */
	private void checkUsedReallyInput(final SudokuBoard input, final List<SudokuBoard> solutions) {
		for (final SudokuBoard board : solutions) {
			for (int i = 0; i < 9; ++i) {
				for (int j = 0; j < 9; ++j) {
					Value v = input.getValue(new Position(i, j));
					if (!v.isEmpty()) {
						assertEquals(v, board.getValue(new Position(i, j)));
					}
				}
			}
		}
	}

	/**
	 * Tests the generic arguments of a solution.
	 * 
	 * @param filename file to load
	 * @return list of solutions
	 */
	private List<SudokuBoard> testFile(final String filename) {
		SudokuBoard input = readFile(filename);

		SudokuBoard inputClone = input.clone();
		List<SudokuBoard> solutions = getSolver().solve(inputClone);

		assertEquals(input, inputClone);
		checkUsedReallyInput(input, solutions);

		validate(solutions);
		return solutions;
	}

	/**
	 * Tests using file example1
	 */
	@Test
	public void testExample1() {
		final String file = System.getProperty("user.dir") + File.separatorChar + "examples" + File.separatorChar
				+ "example1.sudoku";
		List<SudokuBoard> solutions = testFile(file);
		assertEquals(solutions.size(), 203);
	}

	/**
	 * Tests using file example2
	 */
	@Test
	public void testExample2() {
		final String file = System.getProperty("user.dir") + File.separatorChar + "examples" + File.separatorChar
				+ "example2.sudoku";
		List<SudokuBoard> solutions = testFile(file);

		assertEquals(solutions.size(), 1);
		assertEquals(readFile(file + "-sol"), solutions.get(0));

	}

	/**
	 * Tests using file example3
	 */
	@Test
	public void testExample3() {
		final String file = System.getProperty("user.dir") + File.separatorChar + "examples" + File.separatorChar
				+ "example3-impossible.sudoku";
		List<SudokuBoard> solutions = testFile(file);
		assertEquals(solutions.size(), 0);
	}

	/**
	 * Tests using file example4
	 */
	@Test
	public void testExample4() {
		final String file = System.getProperty("user.dir") + File.separatorChar + "examples" + File.separatorChar
				+ "example4.sudoku";
		List<SudokuBoard> solutions = testFile(file);

		assertEquals(solutions.size(), 1);
		assertEquals(readFile(file + "-sol"), solutions.get(0));
	}
}
