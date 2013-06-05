package ch.fhnw.algd1;

public class Main {

	
	public static <T> void echo(T val ) {
		
		System.out.println(val);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		float f1 = 0x2f + 2f;
		echo(f1);
		
		short s = (short)-32868;
		echo(s);
		
		int i1 = 2^2;
		echo(i1);
		
		int i = 15 >> -30;
		echo(i);
		
		long l = -+-007L;
		echo(l);
		
		double d1 = .5;
		echo(d1);

	}

}
