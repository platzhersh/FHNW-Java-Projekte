package ch.fhnw.efalg.u6;

import ch.fhnw.efalg.u6.metaheuristics.Hillclimbing;

public class Main {

	public static void main(String[] args) {
		
		Matrix m = new Matrix(10,2);
		// uncomment next line to change hillclimbing stepSize
		//m.setMetaHeuristic(new Hillclimbing(10));
		m.run();
		
//		for (double d = -10; d <= 11; d+=1) {
//			for (double e = -10; e <= 11; e+=1) {
//				System.out.println(m.f(new double[]{d,e}));
//			}
//		}

	}
}
