package utils;

import java.lang.reflect.Array;
import java.util.List;

public class Matrix {

	private double[] arr;
	private int col_size,row_size;
	
	/*
	 * m x n Matrix with zeroes
	 */
	public Matrix(int m, int n) {
		if(m<=0 || n<=0) throw new IllegalArgumentException("Invalid Matrix size!");
		arr = new double[m*n];
		row_size = m;
		col_size = n;
		
		for(int i=0;i<arr.length;i++)
			arr[i] = 0;		
	}
	
	/*
	 * m x n Matrix with given array
	 */
	public Matrix(int m, int n, double[] a) {
		if(m<=0 || n<=0 || n*m != a.length) throw new IllegalArgumentException("Invalid Matrix size!");
		arr = a;
		row_size = m;
		col_size = n;
		
	}
	
	/*
	 * Matrix from array
	 */
	public Matrix(double[][] data) {
		if(data == null || data.length <= 0) throw new IllegalArgumentException("Invalid double array");
		row_size = data.length;
		col_size = data[0].length; //all rows should have the same length
		arr = new double[row_size * col_size];
		for(int i=0;i<row_size;i++) {
			for(int j=0;j<col_size;j++) {
				arr[j + i*col_size] = data[i][j]; 
			}
		}
	}
	
	/*
	 * number of rows
	 */
	public int nRows() {
		return row_size;
	}
	
	/*
	 * number of columns
	 */
	public int nCols() {
		return col_size;
	}
	
	/*
	 * get Element row, column
	 */
	public double el(int i, int j) {
		if(i<0 || j<0 || i>=col_size || j >= row_size) throw new IllegalArgumentException();
		return arr[j + i * row_size];
	}
	
	/*
	 * Matrix addition
	 */
	public Matrix plus(Matrix m) {
		if(m == null || m.row_size != row_size || m.col_size != col_size) throw new IllegalArgumentException();
		Matrix res = new Matrix(row_size,col_size);
		for(int i=0;i<row_size;i++) {
			for(int j=0;j<col_size;j++) {
				int x = j + i*col_size;
				res.arr[x] = m.arr[x] + arr[x];
			}
		}
		return res;
	}
	
	/*
	 * this - Matrix m  (Substraction)
	 */
	public Matrix minus(Matrix m) {
		if(m == null || m.row_size != row_size || m.col_size != col_size) throw new IllegalArgumentException();
		Matrix res = new Matrix(row_size,col_size);
		for(int i=0;i<row_size;i++) {
			for(int j=0;j<col_size;j++) {
				int x = j + i*col_size;
				res.arr[x] = m.arr[x] - arr[x];
			}
		}
		return res;
	}
	
	/*
	 * Scalar * Matrix
	 */
	public Matrix times(double factor) {
		Matrix res = new Matrix(row_size,col_size);
		for(int i=0;i<row_size;i++) {
			for(int j=0;j<col_size;j++) {
				int x = j + i*col_size;
				res.arr[x] = factor * arr[x];
			}
		}
		return res;
	}
	
	/*
	 * matrix * matrix
	 * 
	 * 
	 *

void multiply(double* arr, double* brr, double* res, int a_height, int b_width, int b_height) {
	for (int d = 0; d < a_height; d++)
		for (int e = 0; e < b_width; e++)
			for (int f = 0; f < b_height; f++) {
				res[(d*b_width) + e] += arr[(d * b_height) + f] * brr[(f * b_width) + e];
			}
}

	 */
	public Matrix times(Matrix that) {
		if(row_size != that.row_size || col_size != that.col_size) throw new IllegalArgumentException();
		
		Matrix res = new Matrix(row_size, col_size);
		for(int d = 0; d < row_size;d++) {
			for(int e = 0; e < that.col_size; e++) {
				for(int f = 0; f < that.row_size; f++) {
					res.arr[(d*that.col_size) + e] += arr[(d*that.row_size) + f] * that.arr[(f * that.col_size) + e];
				}
			}
		}
		return res;
	}
	
	/*
	 * matrix * vector  =>  3x3 * 3x1
	 */
	public Vector times(Vector v) {
		if(v == null || v.length() != row_size) throw new IllegalArgumentException();
		
		double[] res = new double[col_size];
		for(int i=0; i<row_size; i++) {
			double sum = 0;
			for(int j=0; j<col_size; j++) {
				sum += arr[j + i*col_size] * v.x(j+1);
			}
			res[i] = sum;			
		}
		return new Vector(res);
	}
	
	/*
	 * transpose matrix e.g. from 3x1 to 1x3
	 */
	public Matrix transpose() {
		double[] newArr = new double[arr.length];
		for(int i=0; i<row_size;i++) {
			for(int j=0;j<col_size;j++) {
				newArr[i + j*row_size] = arr[j + i*col_size];
			}
		}
		return new Matrix(col_size,row_size,newArr);
	}

	/*
	 * Einheitsmatrix with size of nxn
	 */
	static public Matrix identity(int n) {
		Matrix m = new Matrix(n,n);
		for(int i=0;i<n;i++) {
			m.arr[i + i*n] = 1;
		}
		return m;
	}
	
	/*
	 * drehung
	 */
	static public Matrix rotation( double phi, double a1, double a2, double a3) {
		double c = Math.cos(Math.toRadians(phi));
		double s = Math.sin(Math.toRadians(phi));
		Matrix m = new Matrix(4,4);
		m.arr[0] = (1-c) * a1 * a1 + c;
		m.arr[1] = (1-c) * a1 * a2 - s * a3;
		m.arr[2] = (1-c) * a1 * a3 + s * a2;
		m.arr[3] = 0;
		
		m.arr[4] = (1-c) * a2 * a1 + s * a3;
		m.arr[5] = (1-c) * a2 * a2 + c;
		m.arr[6] = (1-c) * a2 * a3 - s * a1;
		m.arr[7] = 0;
		
		m.arr[8] = (1-c) * a3 * a1 - s * a2;
		m.arr[9] = (1-c) * a3 * a2 + s * a1;
		m.arr[10] = (1-c) * a3 * a3 + c;
		m.arr[11] = 0;
		
		m.arr[12] = 0;
		m.arr[13] = 0;
		m.arr[14] = 0;
		m.arr[15] = 1;
		return m;
	}
	
	/*
	 * skalierung
	 */
	static public Matrix scale(double x,double y) {
		
		Matrix m = new Matrix(3,3);
		m.arr[0] = x;
		m.arr[1] = 0;
		m.arr[2] = 0;
		
		m.arr[3] = 0;
		m.arr[4] = y;
		m.arr[5] = 0;
		
		m.arr[6] = 0;
		m.arr[7] = 0;
		m.arr[8] = 1;
		
		return m;
	}
	
	/*
	 * drehung 3d
	 */
	static public Matrix rotation3D( double phi, double a1, double a2, double a3) {
		double c = Math.cos(Math.toRadians(phi));
		double s = Math.sin(Math.toRadians(phi));
		Matrix m = new Matrix(3,3);
		m.arr[0] = (1-c) * a1 * a1 + c;
		m.arr[1] = (1-c) * a1 * a2 - s * a3;
		m.arr[2] = (1-c) * a1 * a3 + s * a2;
		
		m.arr[3] = (1-c) * a2 * a1 + s * a3;
		m.arr[4] = (1-c) * a2 * a2 + c;
		m.arr[5] = (1-c) * a2 * a3 - s * a1;
		
		m.arr[6] = (1-c) * a3 * a1 - s * a2;
		m.arr[7] = (1-c) * a3 * a2 + s * a1;
		m.arr[8] = (1-c) * a3 * a3 + c;
		
		return m;
	}
	
	/*
	 * verschiebung mit 4x4 Matrix
	 */
	static public Matrix translation( double a1, double a2, double a3) {
		Matrix temp = Matrix.identity(4);
		temp.arr[3] = a1;
		temp.arr[7] = a2;
		temp.arr[11] = a3;
		return temp;
	}
	
	
	public static void main(String args[]) {
		double[][] t = { {3,7,4}, {2,6,4},{6,7,8} };
		Matrix m1 = new Matrix(t);
		Vector v = new Vector (10,0,0);
		
		System.out.println(m1);
		System.out.println(m1.times(v));
	}
	
	/*
	 * String output
	 */
	public String toString() {
		String out = "";
		for(int i=0;i<row_size;i++) {
			out += "| ";
			for(int j=0;j<col_size;j++) {
				out += arr[j + i*col_size] + " ";
			}
			out += "|\n";
		}
		return out;
	}
	
	public double[] getArray() {
		return arr;
	}
}
