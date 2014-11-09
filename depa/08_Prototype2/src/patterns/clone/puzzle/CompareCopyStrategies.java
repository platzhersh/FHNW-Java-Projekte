package patterns.clone.puzzle;

public class CompareCopyStrategies {

	public static void main(String[] args) {
		final int SIZE = 10000;
		final int NOFCLONES = 1000;

		Dictionary dict = new Dictionary("german", SIZE);

		long start = System.currentTimeMillis();
		for (int i = 0; i < NOFCLONES; i++) {
			dict.clone();
		}
		long end = System.currentTimeMillis();
		double t = (end - start) / 1000.0;
		System.out.println("Time used: " + t);
	}

}
