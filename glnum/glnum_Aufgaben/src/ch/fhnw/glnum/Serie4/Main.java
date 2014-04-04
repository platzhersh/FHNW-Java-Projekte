package ch.fhnw.glnum.Serie4;

import java.nio.file.LinkOption;

import org.la4j.LinearAlgebra;
import org.la4j.LinearAlgebra.DecompositorFactory;
import org.la4j.decomposition.MatrixDecompositor;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;

public class Main {
	
	public static void main(String[] args) {
		
		
		/* Aufgabe 1 */
		Matrix m1 = new CRSMatrix(new double[][]
				{
					{9, 45, -9, -18},
					{45, 234, -48, -93},
					{-9, -48, 19, 19},
					{-18, -93, 19, 46}
				});
		
		MatrixDecompositor md1 = m1.withDecompositor(LinearAlgebra.CHOLESKY);
		Matrix[] ms1 = md1.decompose();
		System.out.println(ms1[0].toString());
	
		/* Aufgabe 2 */ 
		
		
		
	}
}
