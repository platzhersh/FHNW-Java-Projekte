package ch.fhnw.glnum.tests.fs2014;

import org.la4j.LinearAlgebra;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;

import ch.fhnw.glnum.tests.fs2013.MatrixDecomposer;

public class MicroTest2 {

	
	public static void main(String[] args) {
	
		Matrix m1a = new CRSMatrix(new double[][] {
				{3,24,12,6},
				{-2,-18,-5,-5},
				{3,20,19,8},
				{-4,-38,-9,-21}				
		});
		
		double x = Double.POSITIVE_INFINITY;
		
		Matrix m1g = new CRSMatrix(new double[][] {
				{x,0,0,0},
				{x,1,0,0},
				{x,x,x,0},
				{x,x,x,1}
		});
		
		Matrix m1h = new CRSMatrix(new double[][] {
				{1,x,x,x},
				{0,x,x,x},
				{0,0,1,x},
				{0,0,0,x}
		});
		
		MatrixDecomposer md = new MatrixDecomposer(m1a, m1g, m1h);
		md.verbose();
		Matrix[] mres = md.decompose();
		
		System.out.println(mres[0].toString());
		System.out.println(mres[1].toString());
		
		Matrix result = mres[0].multiply(mres[1]);
		
		System.out.println("F*G = A : "+ result.equals(m1a));
		
		/* Aufgabe 2 */
		System.out.println("----------------------------------------");
		System.out.println("Aufgabe 2:");
		System.out.println("----------------------------------------");

		// Prepare Matrix 1
		int d1 = 200;
		double[][] a1 = new double[d1][d1];
		for (int i = 0; i < d1; i++) {
			if (i-2 >=0) a1[i-2][i] = 1;
			if (i-1 >=0) a1[i-1][i] = -4;
			 				 a1[i-0][i] = 6;
			if (i+1 <= d1-1) a1[i+1][i] = -4;
			if (i+2 <= d1-1) a1[i+2][i] = 1;
			
		}
		a1[0][0] = 7;
		a1[d1-1][d1-1] = 7;
		Matrix m21 = new CRSMatrix(a1);
		
		// Prepare Matrix 2
		double[][] a2 = new double[d1][d1];
		for (int i = 0; i < d1; i++) {
			 a2[i][i] = 1;			
		}
		Matrix m22 = new CRSMatrix(a2);

		Matrix m23 = m21.multiply(20).add(m22);

		
		// Prepare Vector
		double[] a3 = new double[d1];
		for (int i = 0; i < d1; i++) {
			a3[i] = 1;
		}
		Vector v2 = new BasicVector(a3);
		
		LinearSystemSolver lss2 = m23.withSolver(LinearAlgebra.GAUSSIAN);
		Vector sol2 = lss2.solve(v2);
		
		System.out.println("["+sol2.get(0)+", "+sol2.get(1)+", "+sol2.get(99)+", "+sol2.get(100)+"]");

		
		/* Aufgabe 3 */
		System.out.println("----------------------------------------");
		System.out.println("Aufgabe 3:");
		System.out.println("----------------------------------------");
		
		double x1 = 2.0/3.0;
		double x2 = 1.0/3.0;
		
		Matrix m3 = new CRSMatrix(new double[][] {
			
				{1,		0,		0,		0,		0		},
				{x2/4, 	0,	 	x1/3,	0, 		x1/2	},
				{x2/4,	2*x1/4, 0, 		x1/2, 	0		},
				{x2/4, 	x1/4, 	2*x1/3,	0, 		x1/2	},
				{x2/4, 	x1/4,	0,		x1/2,	0		}
				
		});
		
		Matrix eigenwert = new CRSMatrix(new double[][] {
				{1,0,0,0,0},
				{0,1,0,0,0},
				{0,0,1,0,0},
				{0,0,0,1,0},
				{0,0,0,0,1}
		});
		
		Matrix m32 = //m3.subtract(eigenwert);
				new CRSMatrix(new double[][] {
			
				{0,		0,		0,		0,		0		},
				{1/12, 	-1,	 	2/9,	0, 		2/6	},
				{1/12,	2*2/12, -1, 	2/6, 	0		},
				{1/12, 	2/12, 	2*2/9,	-1,		2/6	},
				{1/12, 	2/12,	0,		2/6,	-1		}
				
		});
		
		System.out.println(m32.toString());
		
		Vector v3 = new BasicVector(new double[] {
			1,0,0,0,0
		});
		
		LinearSystemSolver lsl3 = m32.withSolver(LinearAlgebra.GAUSSIAN);
		
		System.out.println(lsl3.solve(v3, LinearAlgebra.SPARSE_FACTORY));
	}
	
	
}
