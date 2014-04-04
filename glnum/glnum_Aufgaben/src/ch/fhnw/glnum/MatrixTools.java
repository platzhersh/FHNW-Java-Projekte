package ch.fhnw.glnum;

import Jama.Matrix;

/**
 *
 */
public class MatrixTools
{
	/**
	 * Stores the maximum allowed error/delta when comparing two doubles.
	 */
	public static double delta = 0.000001;


	/**
	 * Checks if the two Matrices are equal (all components are equal/the same).
	 *
	 * @param Matrix x
	 * @param Matrix y
	 *
	 * @return boolean true if the two Matrices are equal
	 */
	public static boolean equal(Matrix x, Matrix y)
	{
		double[][] x_arr = x.getArray();
		double[][] y_arr = y.getArray();

		if (x_arr.length != y_arr.length) {
			return false;
		}

		for (int i = 0; i < x_arr.length; ++i) {
			if (x_arr[i].length != y_arr[i].length) {
				return false;
			}
			for (int j = 0; j < x_arr[i].length; ++j) {
				if (Math.abs(x_arr[i][j] - y_arr[i][j]) > MatrixTools.delta) {
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * Generates a n?n Matrix with diagonal value D, starting top-left going down-right.
	 * 
	 * @param int    n number of rows/cols
	 * @param double L lower boundry value
	 * @param double D diagonal value
	 * @param double U upper boundry value
	 * 
	 * @return Matrix the generated Matrix
	 */
	public static Matrix generateLeftTriagonalMatrix(int n, double L, double D, double U)
	{
		Matrix m = new Matrix(n, n);
		for (int i = 0; i < n; ++i) {
			m.set(i, i, D);
			if (i > 0) {
				m.set(i - 1, i, U);
				m.set(i, i - 1, L);
			}
		}

		return m;
	}
	
	/**
	 * Generates a n?n Matrix with diagonal value D, starting top-right going down-left.
	 * 
	 * @param int    n number of rows/cols
	 * @param double L lower boundry value
	 * @param double D diagonal value
	 * @param double U upper boundry value
	 * 
	 * @return Matrix the generated Matrix
	 */
	public static Matrix generateRightTriagonalMatrix(int n, double L, double D, double U)
	{
		Matrix m = new Matrix(n, n);
		for (int i = 0; i < n; ++i) {
			m.set(i, n - i - 1, D);
			if (i < n - 1) {
				m.set(i, n - i - 2, U);
				m.set(i + 1, n - i - 1, L);
			}
		}
		
		return m;
	}
	
	/**
	 * Generates a n?n Matrix with diagonal value D, starting top-left going down-right.
	 * 
	 * @param int    n  number of rows/cols
	 * @param double LL the lowest value
	 * @param double L  lower boundry value
	 * @param double D  diagonal value
	 * @param double U  upper boundry value
	 * @param double UU the highest value
	 * 
	 * @return Matrix the generated Matrix
	 */
	public static Matrix generateLeft5Matrix(int n, double LL, double L, double D, double U, double UU)
	{
		Matrix m = new Matrix(n, n);
		for (int i = 0; i < n; ++i) {
			m.set(i, i, D);
			if (i > 0) {
				m.set(i - 1, i, U);
				m.set(i, i - 1, L);
			}
		}
		for (int j = 0, i = 2; j < n - 2; ++j, ++i) {
			m.set(i, j, LL);
			m.set(j, i, UU);
		}

		return m;
	}
	
	/**
	 * Generates a n?n Matrix with diagonal value D, starting top-right going down-left.
	 * 
	 * @param int    n  number of rows/cols
	 * @param double LL the lowest value
	 * @param double L  lower boundry value
	 * @param double D  diagonal value
	 * @param double U  upper boundry value
	 * @param double UU the highest value
	 * 
	 * @return Matrix the generated Matrix
	 */
	public static Matrix generateRight5Matrix(int n, double LL, double L, double D, double U, double UU)
	{
		Matrix m = new Matrix(n, n);
		for (int j = n - 1, i = 0; j >= 0; j--, ++i) {
			m.set(i, j, D);
		}
		for (int j = n - 1, i = 0; j > 0; j--, ++i) {
			m.set(i, j - 1, U);
			m.set(i + 1, j, L);
		}
		for (int j = n - 1, i = 0; j > 1; j--, ++i) {
			m.set(i, j - 2, UU);
			m.set(i + 2, j, LL);
		}

		return m;
	}
	
	/**
	 * Checks if the Matrix is convergent.
	 * 
	 * @param Matrix x
	 * 
	 * @return boolean true if the Matrix is convergent
	 */
	public static boolean isConvergent(Matrix x)
	{
		return MatrixTools.spectralRadius(x) < 1;
	}
	
	/**
	 * Checks if the Matrix is invertible (zero is not an Eigenvalue).
	 * 
	 * @param Matrix x
	 * 
	 * @return boolean true if is invertible
	 */
	public static boolean isInvertible(Matrix x)
	{
		double[] eigs = x.eig().getRealEigenvalues();
		for (double eig : eigs) {
			if (eig == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if the Matrix x is negative definite.
	 *
	 * @param Matrix x
	 *
	 * @return boolean true if is negative definite
	 */
	public static boolean isNegativeDefinite(Matrix x)
	{
		if (MatrixTools.isSymmetric(x)) {
			double[] eigenvalues = x.eig().getRealEigenvalues();
			for (int i = 0;  i < eigenvalues.length; ++i) {
				if (eigenvalues[i] > 0) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * Checks if the Matrix x is positive definite.
	 *
	 * @param Matrix x
	 *
	 * @return boolean true if is positive definite
	 */
	public static boolean isPositiveDefinite(Matrix x)
	{
		if (MatrixTools.isSymmetric(x)) {
			double[] eigenvalues = x.eig().getRealEigenvalues();
			for (int i = 0;  i < eigenvalues.length; ++i) {
				if (eigenvalues[i] < 0) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * Checks if the Matrix x is symmetric.
	 *
	 * @param Matrix x the Matrix to check
	 *
	 * @return boolean true if Matrix x is symmetric
	 */
	public static boolean isSymmetric(Matrix x)
	{
		return MatrixTools.equal(x, x.transpose());
	}
	
	/**
	 * Checks if the Matrix is quadratic/square (n?n).
	 * 
	 * @param Matrix x the Matrix to check
	 * 
	 * @return boolean true if square
	 */
	public static boolean isQuadratic(Matrix x)
	{
		return x.getRowDimension() == x.getColumnDimension();
	}
	
	/**
	 * Manual implementation of Jama's norm2() method to calculate the 2nd norm of a Matrix.
	 * 
	 * @param Matrix x the Matrix to get the norm for
	 * 
	 * @return double the 2nd norm
	 */
	public static double norm2(Matrix x)
	{
		return MatrixTools.spectralRadius(x.transpose().times(x));
	}
	
	/**
	 * Manual implementation of Jama's normInf() method to calculate the infinity norm of a Matrix.
	 * 
	 * @param Matrix x the Matrix to get the norm for
	 * 
	 * @return double the infinity norm
	 */
	public static double normInf(Matrix x)
	{
		double[][] values = x.getArray();
		double norm = 0;
		double row;
		for (int i = 0; i < values.length; ++i) {
			row = 0;
			for (int j = 0; j < values[i].length; ++j) {
				row += Math.abs(values[i][j]);
			}
			norm = Math.max(norm, row);
		}
		
		return norm;
	}
	
	/**
	 * Returns the spectral radius of the given Matrix.
	 * 
	 * @param Matrix x
	 * 
	 * @return double the spectral radius
	 */
	public static double spectralRadius(Matrix x)
	{
		double sr = 0;
		double[] eigs = x.eig().getRealEigenvalues();
		for (double eig : eigs) {
			sr = Math.max(sr, Math.abs(eig));
		}
		
		return Math.sqrt(sr);
	}
}
