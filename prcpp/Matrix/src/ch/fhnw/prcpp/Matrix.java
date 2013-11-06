package ch.fhnw.prcpp;

public class Matrix {

	double[] m;
	int h, w;
	
	public Matrix( int height, int width) {
		this.w = width;
		this.h = height;
		
		this.m = new double[height*width];
		
		for (int i = 0; i < this.m.length; i++) {
			this.m[i] = Math.random();
		}
	}
	
	public Matrix( int height, int width, double val) {
		this.w = width;
		this.h = height;
		
		this.m = new double[height*width];
		
		for (int i = 0; i < this.m.length; i++) {
			this.m[i] = val;
		}
	}
	
	public Matrix multiply(Matrix matrix) {
		Matrix r = new Matrix(this.h, matrix.w, 0);
		
		// i = row, j = column, field index = row + column
		for (int i = 0; i < this.h; i++) {
			
			for (int j = 0; j < this.w; j++) {
				
				int in = i*this.h+j;
				for (int k = 0; k < matrix.w; k++) {
					r.m[in] += this.m[i+k] * matrix.m[i*k+j];
				}
			}
		}
		return r;
	}
	public boolean equals(Matrix matrix) {
	
		boolean b = true;
		for (int i = 0; i < this.m.length; i++) {
			b = b && (this.m[i] == matrix.m[i]);
			}
		return b;
		
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < this.m.length; i++) {
			sb.append(this.m[i]+" ");
			if ((i+1) % this.w == 0) sb.append("\n");
		}
		return sb.toString();
	}

/*static {
	System.loadLibrary("NativeFunctions");
}*/



}
