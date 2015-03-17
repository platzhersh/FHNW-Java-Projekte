package apm.classloader;

public class Singleton {
	private static Singleton instance = new Singleton();

	
	private Singleton() {
		System.out.println("Singleton instance initialized!");
	}
	
	public static Singleton getInstance() {
		return instance;
	}
	
	public void m() {
	}
}
