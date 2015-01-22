package ch.fhnw.efalg.u6;

import java.util.HashMap;
import java.util.HashSet;

import ch.fhnw.efalg.u6.functions.Easom;
import ch.fhnw.efalg.u6.functions.Michaelwicz;
import ch.fhnw.efalg.u6.metaheuristics.Hillclimbing;
import ch.fhnw.efalg.u6.metaheuristics.Hillclimbing2;

public class Main {

	public static void main(String[] args) {
		
		Matrix m = new Matrix(100,2);
		// uncomment next line to change hillclimbing stepSize
		m.setMetaHeuristic(new Hillclimbing2(m));
		m.setFunction(new Michaelwicz());
		//m.setFunction(new Easom());
		//((Hillclimbing2)m.mh).setStepSize(0.01);
		
		double error = 0;
		int steps = 0;
		int errorCount = 0;
		int times = 1000;
		
		HashMap<Double, Integer> results = new HashMap<Double, Integer>();
		
		for (int i = 0; i < times; i++) {
			double[] d = m.run();
			steps += d[1]; 
			error += d[0];
			if (d[0] != 0) errorCount++;
			
			Integer in = results.get(d[0]);
			if (in != null) {
				in++;
			} else {
				results.put(d[0], 1);
			}
				
			m.setMetaHeuristic(new Hillclimbing2(m));
			m.setFunction(new Michaelwicz());
//			m.setFunction(new Easom());
//			((Hillclimbing2)m.mh).setStepSize(0.1);
		}
			
		
		System.out.println("---------------------------------------------");
		System.out.println("excutions: " + times);
		System.out.println("average error: " + error/times + "%");
		System.out.println("average steps: " + steps/times);
		System.out.println("correct results: " + (times-errorCount));
		
//		for (double d = -10; d <= 11; d+=1) {
//			for (double e = -10; e <= 11; e+=1) {
//				System.out.println(m.f(new double[]{d,e}));
//			}
//		}

	}
}
