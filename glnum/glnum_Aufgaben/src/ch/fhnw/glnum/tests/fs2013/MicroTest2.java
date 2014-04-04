package ch.fhnw.glnum.tests.fs2013;

import org.la4j.LinearAlgebra;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;

public class MicroTest2 {
	
	public static void main(String[] args) {
		
		/* Aufgabe 1 */
		System.out.println("----------------------------------------");
		System.out.println("Aufgabe 1:");
		System.out.println("----------------------------------------");
		
		Matrix m1a = new CRSMatrix(new double[][] {
				{4,8,12,16},
				{-2,-1,0,1},
				{6,11,18,25},
				{5,12,23,35}				
		});
		
		/* Check decomposition made by hand */
		Matrix m1f = new CRSMatrix(new double[][] {
				{0,		0,		0,		4	},
				{0,		0,		3,		-2	},
				{0,		2,		-1,		6	},
				{1,		4,		2,		5	}
		});
		
		Matrix m1g = new CRSMatrix(new double[][] {
				{0,		0,		0,		1	},
				{0,		0,		1,		2	},
				{0,		1,		2,		3	},
				{1,		2,		3,		4	}
		});
		
		System.out.println(m1f.multiply(m1g).equals(m1a));
		
		/* Computed Approach */ 
		
		double inf = Double.POSITIVE_INFINITY;
		
		Matrix m2f = new CRSMatrix(new double[][] {
				{0,		0,		0,		inf	},
				{0,		0,		inf,	inf	},
				{0,		inf,	inf,	inf	},
				{inf,	inf,	inf,	inf	}
		});
		
		Matrix m2g = new CRSMatrix(new double[][] {
				{0,		0,		0,		1	},
				{0,		0,		1,		inf	},
				{0,		1,		inf,	inf	},
				{1,		inf,	inf,	inf	}
		});
		
		MatrixDecomposer md = new MatrixDecomposer(m1a,m2f,m2g);
		Matrix[] mdres = md.decompose();
		
		System.out.println("----------------------------------------");
		
		System.out.println(mdres[0].toString());
		System.out.println(mdres[1].toString());
		
		
		/* Aufgabe 2 */
		System.out.println("----------------------------------------");
		System.out.println("Aufgabe 2:");
		System.out.println("----------------------------------------");

		// Prepare Matrix
		int d = 300;
		double[][] a = new double[d][d];
		for (int i = 0; i < d; i++) {
			if (d-1-2-i >=0) a[d-1-i-2][i] = 1;
			if (d-1-1-i >=0) a[d-1-i-1][i] = -4;
			 				 a[d-1-i-0][i] = 6;
			if (d-1+1-i <=d-1) a[d-1-i+1][i] = -4;
			if (d-1+2-i <=d-1) a[d-1-i+2][i] = 1;
			
		}
		a[d-1][0] = 7;
		a[0][d-1] = 5;
		Matrix m2 = new CRSMatrix(a);
		
		// Prepare Vector
		double[] a2 = new double[d];
		for (int i = 0; i < d; i++) {
			a2[i] = 3*Math.pow(10, -5);
		}
		Vector v2 = new BasicVector(a2);
		
		LinearSystemSolver lss2 = m2.withSolver(LinearAlgebra.GAUSSIAN);
		Vector sol2 = lss2.solve(v2);
		
		System.out.println("["+sol2.get(d/2-1)+", "+sol2.get(d/2)+"]");
		
		
		/* Aufgabe 3 */
		System.out.println("----------------------------------------");
		System.out.println("Aufgabe 3:");
		System.out.println("----------------------------------------");
		Matrix m3 = new CRSMatrix(new double[][] {
			
				{1,			0,		0,		0,		0		},
				{0.25/4, 	-1, 	0.75, 	0.75/3, 0		},
				{0.25/4,	0.75/2, -1, 	0.75/3, 0.75/2	},
				{0.25/4, 	0.75/2, 0,		-1, 	0.75/2	},
				{0.25/4, 	0,		0,		0.75/3,	-1		}
				
		});
		
		Vector v3 = new BasicVector(new double[] {
			1,0,0,0,0
		});
		
		LinearSystemSolver lsl3 = m3.withSolver(LinearAlgebra.GAUSSIAN);
		
		System.out.println(lsl3.solve(v3, LinearAlgebra.SPARSE_FACTORY));
		
		
	}
	
}
