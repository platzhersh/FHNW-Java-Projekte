import java.io.IOException;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println(fibonacciRecursive(6));
		System.out.println(fibonacciIterative(6));
		int test = System.in.read();
		System.out.println(test);

	}
	
	static int fibonacciRecursive(int x) {
		if (x == 0 || x == 1) return 1;
		else return fibonacciRecursive(x-1)+fibonacciRecursive(x-2);
		
	}
	
	static int fibonacciIterative(int x) {
		int last = 1;
		int bflast = 0;
		int current = 0;
		int i = 0;
		while (i < x) { 
			current = last + bflast;
			i++;
			bflast = last;
			last = current;
		}
		return current;
		
	}

}
