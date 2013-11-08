package ch.fhnw.prcpp;

public class Main {
	
	public static void main(String[] args0) {
		
		Matrix x1 = new Matrix(2,2,2);
		Matrix x2 = new Matrix(2,2,2);
		
		System.out.println("Java: ");
		System.out.println(x1.multiply(x2).toString());
		System.out.println("C++:");
		System.out.println(x1.multiplyNative(x2).toString());
		
		
		System.out.println("");
		System.out.println("---------------------");
		System.out.println("");
		
		// Test Multiply				
		Matrix a1 = new Matrix(400,6000);
		Matrix b1 = new Matrix(6000,300);

		long start = System.currentTimeMillis();
		Matrix r1 = a1.multiply(b1);
		long stop1 = System.currentTimeMillis();
		Matrix r2 = a1.multiplyNative(b1);
		long stop2 = System.currentTimeMillis();
		
		System.out.println("");
		System.out.println("---------------------");
		System.out.println("");
		
		System.out.println("multiplyNative == multiply: "+r1.equals(r2));
		System.out.println("multiplyNative: "+ (stop2 - stop1) +"ms");
		System.out.println("multiply: "+ (stop1 - start) +"ms");
		
		System.out.println("");
		System.out.println("---------------------");
		System.out.println("");
		
		// Test Power
		
		Matrix a2 = new Matrix(200,200);

		start = System.currentTimeMillis();
		Matrix r3 = a2.power(51);
		stop1 = System.currentTimeMillis();
		Matrix r4 = a2.powerNative(51);
		stop2 = System.currentTimeMillis();
		
		System.out.println("");
		System.out.println("---------------------");
		System.out.println("");
		
		System.out.println("powerNative == power: "+r3.equals(r4));
		System.out.println("powerNative: "+ (stop2 - stop1) +"ms");
		System.out.println("power: "+ (stop1 - start) +"ms");
		
		System.out.println("");
		System.out.println("---------------------");
		System.out.println("");		
		
		}

}
