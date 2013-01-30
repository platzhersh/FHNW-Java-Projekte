package ch.fhnw.christian.glatthard.swko;

public class helloWorld {

	String text;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		helloWorld blubb = new helloWorld();
		blubb.hello();
		

	}
	
	public helloWorld () {
		this.text = "Hello World";
	}
	
	private void hello() {
		System.out.println(this.text);
	}

}
