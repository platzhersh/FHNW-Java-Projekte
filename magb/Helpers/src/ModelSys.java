
public class ModelSys {
	private Matrix U = Matrix.identity(4);
	
	void rotateAbs(double phi, double a1, double a2, double a3) {
		// U = R * U
		Matrix R = Matrix.rotation(phi,a1,a2,a3);
		U = R.times(U);
	}
	void translateAbs(double a1, double a2, double a3){
		Matrix T = Matrix.translation(a1,a2,a3);
		U = T.times(U);
	}
	void rotateRel(double phi, double a1, double a2, double a3) {
		Matrix R = Matrix.rotation(phi,a1,a2,a3);
		U = U.times(R);
	}
	void translateRel(double a1, double a2, double a3) {
		Matrix T = Matrix.translation(a1,a2,a3);
		U = U.times(T);
	}
	
	Matrix getModelMatrix() { return new Matrix(); }
		
	double[] getModelMatrixLinear() { 
		double[] a = new double[16];
		for (int j=0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				a[j*4+i] = U.el(i, j);
			}
		}
		return a;
	}

}
