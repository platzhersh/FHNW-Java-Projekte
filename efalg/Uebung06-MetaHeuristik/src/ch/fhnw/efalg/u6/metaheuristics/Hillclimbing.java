package ch.fhnw.efalg.u6.metaheuristics;

import ch.fhnw.efalg.u6.Matrix;

public class Hillclimbing implements MetaHeuristic {

	
	double stepSize;
	
	
	/* Constructor */
	public Hillclimbing(double step) {
		stepSize = step;
	}
	
	/***
	 * primitive hillclimbing with random starting point
	 * @param m
	 * @return
	 */
	@Override
	public double getMinimum(final Matrix m) {
		// get random starting point
		double[] vars = new double[m.n];
		System.out.print("starting point: ");
		for (int i = 0; i < m.n; i++) {
			vars[i] = (int)(Math.random()*1000) % Math.min(Math.abs(m.upperLimit), Math.abs(m.lowerLimit));
			System.out.print(vars[i]+", ");
		}
		System.out.println();
		
		System.out.println("upper limit: " + m.upperLimit);
		System.out.println("lower limit: " + m.lowerLimit);
		
		double min = Double.MAX_VALUE;
		double[] lastVars = new double[m.n];
		while(m.f(vars) < min) {
			min = m.f(vars);
			lastVars = vars.clone();
			// get smallest neighbor coordinates and save in vars
			vars = getSmallestNeighbor(m,vars);
			
			System.out.println("min: "+min+" at " + m.arrayToString(lastVars));
		}
		
		System.out.println("-----------------------------");
		System.out.println("min: "+min);
		System.out.println("Pos: " + m.arrayToString(lastVars));
		return min;
	}

	/***
	 * 
	 * @param m 
	 * @param i
	 * @param j
	 * @return coordinates of smallest neighbor
	 */
	double[] getSmallestNeighbor(final Matrix m, double[] vars) {
		double[] minPos = new double[vars.length];
		double min = Double.MAX_VALUE;
		
		// TODO: make dynamic
		// check all neighbor fields, in 2d: 8 fields, 3d: 26 fields, 4d: ..?
		// number of fields to check = 3^n - 1
		// vars.length == n
		/*for (int i = 1; i < Math.pow(3,vars.length); i++) {		
			
		}*/
		
		if (vars.length == 2) {
			// neighbors 1 row up (3 neighbors)
			double[] varsTop = vars.clone(); varsTop[0]-=stepSize;
			double[] varsMiddle = vars.clone(); 
			double[] varsBottom = vars.clone(); varsBottom[0]+=stepSize;
			
			if (varsTop[0]> m.lowerLimit) {
				if (m.f(varsTop) < min) {
					min = m.f(varsTop);
					minPos = varsTop;
				}
				
				double[] varsTopLeft = varsTop.clone(); varsTopLeft[1]-=stepSize;
				double[] varsTopRight = varsTop.clone(); varsTopRight[1]+=stepSize;
				
				
				if (varsTopLeft[1] > m.lowerLimit) {
					if (m.f(varsTopLeft) < min) {
						min = m.f(varsTopLeft);
						minPos = varsTopLeft;
					}
				}
				if (varsTopRight[1] < m.upperLimit) {
					if (m.f(varsTopRight) < min) {
						min = m.f(varsTopRight);
						minPos = varsTopRight;
					}
				}
			}
			// neighbors same row (2 neighbors)
			double[] varsMiddleLeft = varsMiddle.clone(); varsMiddleLeft[1]-=stepSize;
			double[] varsMiddleRight = varsMiddle.clone(); varsMiddleRight[1]+=stepSize;
			if (varsMiddleLeft[1] > m.lowerLimit) {
				if (m.f(varsMiddleLeft) < min) {
					min = m.f(varsMiddleLeft);
					minPos = varsMiddleLeft;
				}
			}
			if (varsMiddleRight[1] < m.upperLimit) {
				if (m.f(varsMiddleRight) < min) {
					min = m.f(varsMiddleRight);
					minPos = varsMiddleRight;
				}
			}
			// neighbors 1 row down (3 neighbors)
			if (varsBottom[0] < m.upperLimit) {
				double[] varsBottomLeft = varsTop.clone(); varsBottomLeft[1]-=stepSize;
				double[] varsBottomRight = varsTop.clone(); varsBottomRight[1]+=stepSize;
				
				if (m.f(varsTop) < min) {
					min = m.f(varsTop);
					minPos = varsTop;
				}
				if (varsBottomLeft[1] > m.lowerLimit) {
					if (m.f(varsBottomLeft) < min) {
						min = m.f(varsBottomLeft);
						minPos = varsBottomLeft;
					}
				}
				if (varsBottomRight[1] < m.upperLimit) {
					if (m.f(varsBottomRight) < min) {
						min = m.f(varsBottomRight);
						minPos = varsBottomRight;
					}
				}
			}
			
			vars = minPos;
			
		} else {
			System.out.println("only 2d possible atm.");
		}
		//assert(min>=0);
		return minPos;
	}

}
