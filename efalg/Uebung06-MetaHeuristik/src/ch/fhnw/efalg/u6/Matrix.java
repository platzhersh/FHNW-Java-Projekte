package ch.fhnw.efalg.u6;

import ch.fhnw.efalg.u6.functions.Function;
import ch.fhnw.efalg.u6.functions.Rosenbrock;
import ch.fhnw.efalg.u6.metaheuristics.Hillclimbing;
import ch.fhnw.efalg.u6.metaheuristics.MetaHeuristic;

public class Matrix {
	double[][] m;
	int zeroPos;
	public int upperLimit, lowerLimit, n;
	
	MetaHeuristic mh;
	Function f;
	
	/* Constructors */
	
	public Matrix() {
		// default limits: -100, 100
		this(100, 2);
	}
	
	public Matrix(int limit, int dimensions) {
		this(Math.abs(limit)*(-1), Math.abs(limit), dimensions);
	}
	
	public Matrix(int lower, int upper, int dimensions) {
		lowerLimit = lower;
		upperLimit = upper;
		n = dimensions;
		
		// default Function: Rosenbrock, default MetaHeuristic: Hillclimbing
		setFunction(new Rosenbrock());		
		setMetaHeuristic(new Hillclimbing(1.0));
	}
	
	/* Getter & Setter */
	public void setFunction(Function func) {
		f = func;
	}
	
	public void setMetaHeuristic(MetaHeuristic m) {
		mh = m;
	}
	
	/* other functions */
	
	public void run() {
		mh.getMinimum(this);
		//mh.getMaximum(this);
	}
	
	public double f(double[] x) {
		return f.Obj_f(x);
	}

	public String arrayToString(double[] a) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < a.length; j++) {
			sb.append(a[j]+", ");
		}
		return sb.toString();
	}
	
	/*
	static void printMatrix(double[][] m) {
		for (int i = 0; i < m.length; i++) {
			System.out.print("[");
			for (int j = 0; j < m.length; j++) {
				System.out.print(" "+m[i][j]+",");
			}
			System.out.println(" ]");
		}
	}
	*/
}
