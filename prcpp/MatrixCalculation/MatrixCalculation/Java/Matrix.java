public class Matrix {

	double[] m;
	int h, w;
	
	public Matrix( int height, int width) {
		this.w = width;
		this.h = height;
		
		this.m = new double[height*width];
		
		for (int i = 0; i < this.m.length; i++) {
			this.m[i] = (int)Math.random() * 2;
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
		Matrix r = new Matrix(this.height, matrix.width);
		
		// i = row, j = column, field index = row + column
		for (int i = 0; i < this.height; i++) {
			
			for (int j = 0; j < m.width; i++) {
				
				double v;
				for (int k = 0; k < matrix.width; i++) {
					v += this.m[i+k] * matrix[i*k];
				}
				this.m[i+j];				
			}
			
		}
	}
	public boolean equals(Matrix matrix) {
	
		boolean b = True;
		for (int i = 0; i < this.m.length; i++) {
			b = b & (this.m[i] = matrix[i]);
			}
		return b;
		
	}

static {
	System.loadLibrary("NativeFunctions");
}

public static void main(String[] args) {
	
}


}