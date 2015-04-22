package main;

import impl.BasicComputer2;
import interfaces.AsyncResult;
import interfaces.Calculation;

public class Main {

	public static void main(String[] args) {
		Calculation c1 = new Calculation() {
			public double calc() {
				return (5*3)*2;
			}
		};
		Calculation c2 = new Calculation() {
			public double calc() {
				return (5*4)*2;
			}
		};
		Calculation c3 = new Calculation() {
			public double calc() {
				return Math.pow(2, 2);
			}
		};
		
		//BasicComputer bc = new BasicComputer();
		BasicComputer2 bc = new BasicComputer2();
		
		AsyncResult a1 = bc.doCalc(c1);
		AsyncResult a2 = bc.doCalc(c2);
		AsyncResult a3 = bc.doCalc(c3);
		
		System.out.println(a1.awaitResult() + " " + a2.awaitResult() + " " + a3.awaitResult());
		
	}

}
