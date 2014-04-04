package ch.fhnw.glnum.tests.fs2011;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;

import ch.fhnw.glnum.tests.fs2013.MatrixDecomposer;

public class MicroTest2 {

	public static void main(String[] args) {
		
		
		System.out.println("----------------------------------------");
		System.out.println("Aufgabe 1:");
		System.out.println("----------------------------------------");
				
		Matrix m1a = new CRSMatrix(new double[][] {
				{1,2,4,6},
				{2,7,13,19},
				{3,15,30,63},
				{4,20,40,93}				
		});
		
		double inf = Double.POSITIVE_INFINITY;
		
		Matrix m1g = new CRSMatrix(new double[][] {
				{inf,0,0,0},
				{inf,1,0,0},
				{inf,inf,inf,0},
				{inf,inf,inf,1}
		});
		
		Matrix m1h = new CRSMatrix(new double[][] {
				{1,inf,inf,inf},
				{0,inf,inf,inf},
				{0,0,1,inf},
				{0,0,0,inf}
		});
		
		MatrixDecomposer md = new MatrixDecomposer(m1a, m1g, m1h);
		Matrix[] mres = md.decompose();
		
		System.out.println(mres[0].toString());
		System.out.println(mres[1].toString());
		
		System.out.println("----------------------------------------");
		System.out.println("Aufgabe 2:");
		System.out.println("----------------------------------------");
		
	}
	
}
