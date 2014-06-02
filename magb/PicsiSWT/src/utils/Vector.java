package utils;
public class Vector {

	final double[] data;
	final int dir;
	
	//public Vector(double i, double j, double k, double l) {
		// TODO Auto-generated constructor stub
	//}

	public Vector(int n)  // create the zero vector of dimension n
	{
		data = new double[n];
		dir = 1;
		for (int i = 0; i<n; i++) {
			data[i] = 0;
		}
	}
	public Vector(double[] d) // create a vector from an array
	{
		dir = 1;
		data = new double[d.length];
		for (int i = 0; i<d.length; i++) {
			data[i] = d[i];
		}
	}
	/*
	 * create vector from vararg list (values are copied)
	 */
	public Vector(double d, double... ddata) {
		data = new double[ddata.length + 1];
		dir = 1;
		data[0] = d;
		for (int i = 1; i <= ddata.length; ++i) {
			data[i] = ddata[i - 1];
		}
	}
	public Vector(double x, double y, double z, int direction) // create vector from vararg list
	{
		dir = direction;
		data = new double[3];
		data[0] = x;
		data[1] = y;
		data[2] = z;
	}
	public int length() // dimension of the vector
	{
		return data.length;
	}
	public double x(int i) // return x_i
	{
		return data[i];
	}
	public Vector plus(Vector that) // vector addition
	{
		if (!compareVectorDimension(that)) throw new IllegalArgumentException();
		Vector v = new Vector(this.data.length);
		for (int i = 0; i < this.data.length; i++) {
			v.data[i] = this.data[i] + that.data[i];
		}
		return v;
	}
	public Vector minus(Vector that) // vector subtraction
	{
		if (!compareVectorDimension(that)) throw new IllegalArgumentException();
		Vector v = new Vector(this.data.length);
		for (int i = 0; i < this.data.length; i++) {
			v.data[i] = this.data[i] - that.data[i];
		}
		return v;
	}
	public Vector times(double factor) // product scalar * vector
	{
		Vector v = new Vector(this.data.length);
		for (int i = 0; i < this.data.length; i++) {
			v.data[i] = this.data[i]*factor;
		}
		return v;
	}
	public double dot(Vector that) // dotproduct
	{
		if (!compareVectorDimension(that)) throw new IllegalArgumentException();
		double d = 0;
		for (int i = 0; i < this.data.length; i++) {
			d += this.data[i] * that.data[i];
		}
		return d;
	}
	public double magnitude() // norm
	{
		double d = 0;
		for (int i = 0; i < this.data.length; i++) {
			d += Math.pow(this.data[i], 2);
		}
		return Math.sqrt(d);
	}
	public Vector cross(Vector that) // crossproduct
	{
		// |a| * |b| * sin(roh) * n, mit n = Einheitsvektor senkrecht zur durch a und b gespannten Ebene
		//double roh = angle(that);	// sin = Gegenkathete / Hypothenuse
		//return this.magnitude() * that.magnitude() * Math.sin(roh) *
		if (!compareVectorDimension(that)) throw new IllegalArgumentException();
		if (this.data.length != 3) throw new IllegalArgumentException();
		
		Vector v = new Vector(this.data.length);
		v.data[0] = this.x(2) * that.x(3) - this.x(3) * that.x(2);
		v.data[1] =	this.x(3) * that.x(1) - this.x(1) * that.x(3);
		v.data[2] = this.x(1) * that.x(2) - this.x(2) * that.x(1);
		
		return v;
	}
	public double distanceTo(Vector that) // Euclidean distance
	{
		return this.minus(that).magnitude();
	}
	public Vector direction() // return the corresponding unit vector
	{
		Vector v = new Vector(this.data.length);
		v = this.times(1/this.magnitude());
		return v;
	}
	
	private double angle(Vector that) {
		return Math.acos(this.dot(that) / this.magnitude()*that.magnitude());
	}
	
	private boolean compareVectorDimension(Vector that) {
		return this.data.length == that.data.length;
	}
	public void print() {
		for (double i : data) {
				System.out.print(i + "\t");
		}
		
	}

	
	
}
