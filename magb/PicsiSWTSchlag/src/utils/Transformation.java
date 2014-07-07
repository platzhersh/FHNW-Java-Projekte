package utils;

public class Transformation {
	
	public static Matrix pointRotation2D(double phi, int x, int y) {
		double[] transArr1 = { 	1, 0, x, 
								0, 1, y,
								0, 0, 1 };
		Matrix trans1 = new Matrix(3, 3, transArr1);
		Matrix invRot = Matrix.rotation3D(-phi, 0, 0, 1);
		double[] transArr2 = { 	1, 0, -x, 
								0, 1, -y, 
								0, 0, 1 };
		Matrix trans2 = new Matrix(3, 3, transArr2);
		
		return trans1.times(invRot).times(trans2);
	}
	
	public static Matrix scaleLocal(double x, double y, double scale_x, double scale_y) {
		double[] transArr1 = { 	1, 0, x, 
								0, 1, y,
								0, 0, 1 };
		Matrix trans1 = new Matrix(3, 3, transArr1);
		Matrix scale = Matrix.scale(1/scale_x, 1/scale_y);
		double[] transArr2 = { 	1, 0, -x, 
								0, 1, -y, 
								0, 0, 1 };
		Matrix trans2 = new Matrix(3, 3, transArr2);
		
		return trans1.times(scale).times(trans2);
	}
	
}
