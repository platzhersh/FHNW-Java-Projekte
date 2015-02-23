package lecture;

public class RuntimeExit {
	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				Runtime.getRuntime().exit(0);
			}
		}.start();

		for (int i = 0; i < 2000; i++) {
			System.out.println("main " + i);
		}
	}
}
