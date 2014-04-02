public class Matrix {

	private double[][] mat = null;

	/*
	 * create the zero matrix of dimension m x n
	 */
	public Matrix(int m, int n) {
		mat = new double[m][n];
	}

	/*
	 * create a matrix from an array
	 */
	public Matrix(double[][] data) {
		mat = new double[data.length][data[0].length];
		for (int i = 0; i < data.length; ++i) {
			for (int j = 0; j < data[i].length; ++j) {
				mat[i][j] = data[i][j];
			}
		}
	}

	/*
	 * returns the number of rows
	 */
	public int nRows() {
		return mat.length;
	}

	/*
	 * return the number of columns
	 */
	public int nCols() {
		return mat[0].length;
	}

	/*
	 * return matrix element(i, j)
	 */
	public double el(int i, int j) {
		return mat[i][j];
	}

	/*
	 * matrix addition
	 */
	public Matrix plus(Matrix that) {
		Matrix temp = new Matrix(mat.length, mat[0].length);
		for (int i = 0; i < mat.length; ++i) {
			for (int j = 0; j < mat[i].length; ++j) {
				temp.mat[i][j] = mat[i][j] + that.mat[i][j];
			}
		}
		return temp;
	}

	/*
	 * matrix subtraction
	 */
	public Matrix minus(Matrix that) {
		Matrix temp = new Matrix(mat.length, mat[0].length);
		for (int i = 0; i < mat.length; ++i) {
			for (int j = 0; j < mat[i].length; ++j) {
				temp.mat[i][j] = mat[i][j] - that.mat[i][j];
			}
		}
		return temp;
	}

	/*
	 * product scalar * matrix
	 */
	public Matrix times(double factor) {
		Matrix temp = new Matrix(mat.length, mat[0].length);
		for (int i = 0; i < mat.length; ++i) {
			for (int j = 0; j < mat[i].length; ++j) {
				temp.mat[i][j] = mat[i][j] + factor;
			}
		}
		return temp;
	}

	/*
	 * matrix multiplication
	 */
	public Matrix times(Matrix that) {
		Matrix temp = new Matrix(mat.length, that.mat[0].length);
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < that.mat[i].length; j++) {
				for (int k = 0; k < that.mat.length; k++) {
					temp.mat[i][j] += mat[i][k] * that.mat[k][j];
				}
			}
		}
		return temp;
	}

	/*
	 * product matrix * vector
	 */
	public Vector times(Vector that) {
		double[] res = new double[mat.length];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				res[i] += mat[i][j] * that.x(j);
			}
		}
		return new Vector(res);
	}

	/*
	 * matrix transposition
	 */
	public Matrix transpose() {
		double[][] trans = new double[mat[0].length][mat.length];
		for (int i = 0; i < mat[0].length; ++i) {
			for (int j = 0; j < mat.length; ++j) {
				trans[i][j] = mat[j][i];
			}
		}
		return new Matrix(trans);
	}

	/*
	 * returns the identity matrix
	 */
	static public Matrix identity(int n) {
		Matrix temp = new Matrix(n, n);
		for (int i = 0; i < n; ++i)
			temp.mat[i][i] = 1;
		return temp;
	}

	/*
	 * returns rotation matrix
	 */
	static public Matrix rotation(double phi, double a1, double a2, double a3) {
		double c = Math.cos(Math.toRadians(phi));
		double s = Math.sin(Math.toRadians(phi));
		double[][] rot =
			{
						{ (1 - c) * a1 * a1 + c, (1 - c) * a1 * a2 - s * a3,
								(1 - c) * a1 * a3 + s * a2, 0 },
						{ (1 - c) * a2 * a1 + s * a3, (1 - c) * a2 * a2 + c,
								(1 - c) * a2 * a3 - s * a1, 0 },
						{ (1 - c) * a3 * a1 - s * a2,
								(1 - c) * a3 * a2 + s * a1,
								(1 - c) * a3 * a3 + c, 0 },
						{ 0, 0, 0, 1 } };
		return new Matrix(rot);
	}

	/*
	 * returns translation matrix
	 */
	static public Matrix translation(double a1, double a2, double a3) {
		Matrix temp = Matrix.identity(4);
		temp.mat[0][3] = a1;
		temp.mat[1][3] = a2;
		temp.mat[2][3] = a3;
		return temp;
	}

// ------ Bewegungen des Objekt-Systems
	public Matrix rotateModelSysAbs(Matrix U, double phi, double a1, double a2,
			double a3) {
		Matrix R = Matrix.rotation(phi, a1, a2, a3);
		return R.times(U);
	}

	public Matrix rotateModelSysRel(Matrix U, double phi, double a1, double a2,
			double a3) {
		Matrix R = Matrix.rotation(phi, a1, a2, a3);
		return U.times(R);
	}

	public Matrix translateModelSysAbs(Matrix U, double a1, double a2, double a3) {
		Matrix T = Matrix.translation(a1, a2, a3);
		return T.times(U);
	}

	public Matrix translateModelSysRel(Matrix U, double a1, double a2, double a3) {
		Matrix T = Matrix.translation(a1, a2, a3);
		return U.times(T);
	}

// ------ Bewegungen des Kamera-Systems
	public Matrix rotateCamSysAbs(Matrix V, double phi, double a1, double a2,
			double a3) {
		Matrix R = Matrix.rotation(-phi, a1, a2, a3); // Inverse der Rotation = negativer Winkel
		return V.times(R);

	}

	public Matrix rotateCamSysRel(Matrix V, double phi, double a1, double a2,
			double a3) {
		Matrix R = Matrix.rotation(-phi, a1, a2, a3); // Inverse der Rotation = negativer Winkel
		return R.times(V);
	}

	public Matrix translateCamSysAbs(Matrix V, double a1, double a2, double a3) {
		// V = V * T^-1
		Matrix T = Matrix.translation(-a1, -a2, -a3);
		return V.times(T);
	}

	public Matrix translateCamSysRel(Matrix V, double a1, double a2, double a3) {
		// V = T^-1 * V
		Matrix T = Matrix.translation(-a1, -a2, -a3);
		return T.times(V);
	}

	public void print() {
		for (double[] i : mat) {
			for (double j : i) {
				System.out.print(j + "\t");
			}
			System.out.println();
		}
	}
}
