package ch.fhnw.glatthard.christian.test;

public class Test {

	int intMax = Integer.MAX_VALUE;
	double doubleMax = Double.MAX_VALUE;
	float floatMax = Float.MAX_VALUE;
	
	int intMin= Integer.MIN_VALUE;
	double doubleMin = Double.MIN_VALUE;
	float floatMin = Float.MIN_VALUE;	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test();
	}
	
	public Test() {
		System.out.println(intMax);
		System.out.println(doubleMax);
		System.out.println(floatMax);
		System.out.println("-------------------------------");
		
		float i = 2l;
		
		System.out.println(i);
		
		System.out.println("-------------------------------");
		
		byte b = Byte.MIN_VALUE;
		System.out.println(b);
		System.out.println(- --b);
		System.out.println(-b--);
		
		System.out.println("-------------------------------");
		
		int x = 4;
		System.out.println(x);
		boolean bool = (++x >> 1 < x);
		System.out.println(bool);
		System.out.println(x);
		
		
	}

}
