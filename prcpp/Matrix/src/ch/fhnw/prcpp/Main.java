package ch.fhnw.prcpp;

public class Main {
	
	public static void main(String[] args0) {
		
		Matrix m1 = new Matrix(2,2,1);
		Matrix m2 = new Matrix(2,2,2);
		Matrix m3 = new Matrix(2,2);
		Matrix m4 = new Matrix(2,2,4);
		
		System.out.println(m1.toString());
		System.out.println(m2.toString());
		System.out.println(m3.toString());
		
		Matrix r = m1.multiply(m2);
		System.out.println(r.toString());
		
		System.out.println(m3.equals(m4));
		System.out.println(r.equals(m4));
		
	}

}
