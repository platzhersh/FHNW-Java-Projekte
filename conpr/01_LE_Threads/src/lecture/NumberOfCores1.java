package lecture;

public class NumberOfCores1 {

	public static void main(String[] args) throws Exception {
		System.out.println(Runtime.getRuntime().availableProcessors());
		
		System.out.println("input to start");
		System.in.read();
		System.out.println(">>starting");

		System.out.println(Runtime.getRuntime().availableProcessors());
	}

}
