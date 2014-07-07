package ch.fhnw.glnum.Serie03;

import org.la4j.LinearAlgebra;
import org.la4j.decomposition.MatrixDecompositor;
import org.la4j.factory.Factory;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;
import org.la4j.vector.Vector;

public class Main {

	
	public static void main(String[] args) {
		
		/* Aufgabe 1 */
		
		// Downloading, including and reading into http://la4j.org/
		
		/* Aufgabe 2 */
		
		Matrix m1 = new CRSMatrix(new double[][]{
				
				{2,1,-2,3},
				{1,2,2,2},
				{-2,2,-1,0},
				{4,1,3,5}
				
		});
		Vector v1 = new BasicVector(new double[] {
				-19,10,7,-4
		});
		
		
		
		LinearSystemSolver lss1 = m1.withSolver(LinearAlgebra.GAUSSIAN);
		Vector x = lss1.solve(v1, LinearAlgebra.SPARSE_FACTORY);
			
		System.out.println(v1.toString());
	
		
		/* Aufgabe 3 */
		
		Matrix m2 = new CRSMatrix(new double[][]{
				{9,45,-9,-18},
				{45,234,-48,-93},
				{-9,-48,19,19},
				{-18,-93,19,46}
		});
		MatrixDecompositor md1 = m2.withDecompositor(LinearAlgebra.LU);
		Matrix[] lup = md1.decompose(LinearAlgebra.DENSE_FACTORY);
		System.out.println(lup[0].toString());
		System.out.println(lup[1].toString());
		
		
		
	}
}
