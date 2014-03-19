public class DoubleToFloat {

	public static void umrechnen(double d){
		System.out.println("next float: Math.nextUp [" + Math.nextUp(d) + "]"+ " -> " + Double.toString(d));
		System.out.println("Fehler: "+(Math.nextUp(d)-d));
	}


	public static void  main(String[] args) {
		double d = 3.123456789123456789123456789123456789123456789;
		umrechnen(d);
	}
}
