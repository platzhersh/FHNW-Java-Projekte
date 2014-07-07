package utils;

public class Vector {
	
	private double[] v;

	public Vector(int n) {
		v = new double[n];
	}
	
	public double[] getV() {
		return v;
	}

	public Vector(double first, double... data) {
		v = new double[data.length+1];
		int c=0;
		v[c] = first;
		for(double a : data) {
			c++;
			v[c] = a;
		}
	}
	
	public Vector(double[] data) {
		v = data.clone();
	}
	
	public int length() {
		return v.length;
	}
	
	public double x(int i) {
		if(i<1) throw new IndexOutOfBoundsException();
		return v[i-1];
	}
	
	public Vector plus(Vector that) { 
		if(that.length() != this.length()) throw new IllegalStateException("Vector don't have the same length");
		Vector neuV = new Vector(length());
		for(int i=0; i<length();i++) {
			neuV.v[i] = v[i] + that.v[i];
		}
		return neuV;
	}
	
	public Vector minus(Vector that) {
		if(that.length() != this.length()) throw new IllegalStateException("Vector don't have the same length");
		Vector neuV = new Vector(length());
		for(int i=0; i<length();i++) {
			neuV.v[i] = v[i] - that.v[i];
		}
		return neuV;
	}
	
	public Vector times(double factor) {
		Vector neuV = new Vector(length());
		for(int i=0; i<length();i++) {
			neuV.v[i] = v[i] * factor;
		}
		return neuV;
	}
	
	public double dot(Vector that) {
		if(that.length() != this.length()) throw new IllegalStateException("Vector don't have the same length");
		double sum = 0;
		for(int i=0; i<length(); i++) {
			sum += v[i] * that.v[i];
		}
		return sum;
	}
	
	public double magnitude() {
		double res = 0;
		for(int i=0;i<v.length;i++) {
			res += Math.pow(v[i],2);
		}
		return Math.sqrt(res);
	}
	
	public Vector cross(Vector that) {
		if(length() != 3 || that.v.length != 3) throw new IllegalStateException();
		Vector neuV = new Vector(length());
		neuV.v[0] = v[1]*that.v[2]  -  v[2]*that.v[1];
		neuV.v[1] = v[2]*that.v[0]  -  v[0]*that.v[2];
		neuV.v[2] = v[0]*that.v[1]  -  v[1]*that.v[0];			
		return neuV;
	}
	
	public double distanceTo(Vector that) {
		double res = 0;
		for(int i=0;i<v.length;i++) {
			res += Math.pow((v[i]-that.v[i]),2);
		}
		return Math.sqrt(res);
	}
	
	public Vector direction() {
		double mag = magnitude();
		Vector neuV = new Vector(length());
		for(int i=0; i<length();i++) {
			neuV.v[i] = v[i] / mag;
		}
		return neuV;
	}
	
	public Vector normalize() {
		return this.times(1 / this.magnitude());
	}
	
	public String toString() {
		String out = "V[";
		for(double i : v) {
			out += i + ",";
		}
		out += "]";
		return out;
	}
}
