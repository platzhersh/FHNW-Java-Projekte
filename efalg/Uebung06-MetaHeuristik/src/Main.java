
public class Main {

	public static void main(String[] args) {
		double[][] rm = getRosebrockMatrix(-1, 1);
		printMatrix(rm);

	}
	
	static double[][] getRosebrockMatrix(int min, int max) {
		int dimension = Math.abs(min) + Math.abs(max) + 1;
		int zeroPos = dimension - Math.abs(max) -1;
		double[][] m = new double[dimension][dimension];
		System.out.println("zeroPos: "+zeroPos);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				
				// calculate the correct coordinates for Rosenbrock function
				int iIndex = i; int jIndex = j;
				if (i == zeroPos) iIndex = 0;
				else iIndex = i - zeroPos;
				if (j == zeroPos) jIndex = 0;
				else jIndex = j - zeroPos;

				m[i][j] = Obj_f(new double[]{iIndex,jIndex});
			}
		}
		
		return m;
	}
	
	/***
	 * Objective function f (Rosenbrock function /  Banana function)
	 * @param x double array consisting of two values, x[0] = x & x[1] = y
	 * @return
	 */
	static double Obj_f(double[] x) {
		System.out.println("x: "+x[0]+", y: "+x[1]);
		return (1.0-x[0])*(1.0-x[0]) + 100.0*(x[1]-x[0])*(x[1]-x[0]*x[0]);
	}

	static void printMatrix(double[][] m) {
		for (int i = 0; i < m.length; i++) {
			System.out.print("[");
			for (int j = 0; j < m.length; j++) {
				System.out.print(" "+m[i][j]+",");
			}
			System.out.println(" ]");
		}
	}
	
}
