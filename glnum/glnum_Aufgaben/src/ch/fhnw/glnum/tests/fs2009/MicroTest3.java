package ch.fhnw.glnum.tests.fs2009;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;

import ch.fhnw.glnum.tests.fs2013.MatrixDecomposer;

public class MicroTest3 {
	
	public static void main(String[] args) {
		
		Matrix m1a = new CRSMatrix(new double[][] {
				{-2,6,-3,16},
				{0,0,3,20},
				{-6,12,-18,28},
				{-1,2,-3,4}				
		});
		
		double inf = Double.POSITIVE_INFINITY;
		
		Matrix m1g = new CRSMatrix(new double[][] {
				{inf,inf,inf,1},
				{inf,inf,1,0},
				{inf,1,0,0},
				{1,0,0,0}
		});
		
		Matrix m1h = new CRSMatrix(new double[][] {
				{inf,inf,inf,inf},
				{inf,inf,inf,0},
				{inf,inf,0,0},
				{inf,0,0,0}
		});
		
		MatrixDecomposer md = new MatrixDecomposer(m1a, m1g, m1h);
		Matrix[] mres = md.decompose();
		
		System.out.println(mres[0].toString());
		System.out.println(mres[1].toString());
		
	}
	
}
