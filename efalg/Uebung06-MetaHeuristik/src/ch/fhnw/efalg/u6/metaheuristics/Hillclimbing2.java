package ch.fhnw.efalg.u6.metaheuristics;

import ch.fhnw.efalg.u6.Matrix;

public class Hillclimbing2 implements MetaHeuristic {

	long steps;
	double stepSize;
	Matrix m;
	double e;	// epsilon
	double[] tabuX;
	double[] tabuY;
	
	/* Constructor */
	public Hillclimbing2(Matrix m) {
		this.m = m;
		this.steps = 0;
		stepSize = 1.0;
		System.out.println("Set stepSize = " + stepSize);
		e = 0.000000000001;
	}
	
	/***
	 * primitive hillclimbing with random starting point
	 * @param m
	 * @return double array of length 2, [0] = minimum, [1] = steps needed
	 */
	@Override
	public double[] getMinimum(final Matrix m) {
		// get random starting point
		double[] vars = new double[m.n];
		System.out.print("starting point: ");
		for (int i = 0; i < m.n; i++) {
//			vars[i] = (int)(Math.random()*1000) % Math.min(Math.abs(m.upperLimit), Math.abs(m.lowerLimit));
//			if (Math.random() > 0.4) vars[i]*=(-1);
			vars[i] = (int)randomWithRange(m.lowerLimit, m.upperLimit);
			System.out.print(vars[i]+", ");
		}
		System.out.println();
		
		System.out.println("upper limit: " + m.upperLimit);
		System.out.println("lower limit: " + m.lowerLimit);
		
		double min = Double.MAX_VALUE;
		double[] lastVars = new double[m.n];
		while(m.f(vars) <= min) {
			min = m.f(vars);
			lastVars = vars.clone();
			// get smallest neighbor coordinates and save in vars
			vars = getSmallestNeighbor(vars);
			System.out.println("stepsize: " + stepSize);
			System.out.println("min: "+min+" at " + m.arrayToString(lastVars));
			//setStepSize();
		}
		
		double[] mins = new double[4];
		double[] diffs = new double[4];
		
		System.out.println("-----------------------------");
		System.out.println("min: "+min);
		System.out.println("Pos: " + m.arrayToString(lastVars));
		
		double[] diff = new double[lastVars.length];
		double[] exp = m.f.expectedMinimaPos();
		double maxDiff = 0;
		for(int i = 0; i < lastVars.length; i++){
			diff[i] = Math.abs(Math.abs(lastVars[i])-Math.abs(exp[i]));
			if (diff[i] > maxDiff) maxDiff = diff[i]/exp[i] * 100;			
		}
		
		return new double[]{maxDiff, steps};
	}
	
	/***
	 * returns a random double between min and max
	 * @param min lower limit
	 * @param max upper limit 
	 * @return double between min and max
	 */
	double randomWithRange(double min, double max) {
		double range = Math.abs(max - min);     
		return (Math.random() * range) + (min <= max ? min : max);
	}
	
	
	/***
	 * 
	 */
	void setStepSize() {
		if (stepSize == 0.0) {
			//stepSize = (Math.abs(m.lowerLimit) + Math.abs(m.upperLimit)) / 2.0;
			stepSize = (int) (Math.abs(m.lowerLimit) + Math.abs(m.upperLimit)) / 3;
		} else {
			stepSize = stepSize/(stepSize/10);
		}
	}
	
	public void setStepSize(double s) {
		stepSize = s;
	}

	/***
	 * 
	 * @param m
	 * @param vars starting position
	 * @return
	 */
	double[] getSmallestNeighbor(double[] vars) {
		return getNeighborExtreme(vars, 1);
	}

	
	/***
	 * 
	 * @param start	starting position
	 * @param modifier set 1 to find smallest Neighbor, -1 to find biggest
	 * @return
	 */
	double[] getNeighborExtreme(double[] vars, int modifier) {
		steps++;
		double[] exPos = new double[vars.length];
		double ex = Double.MAX_VALUE;
		
		// TODO: make dynamic
		// check all neighbor fields, in 2d: 8 fields, 3d: 26 fields, 4d: ..?
		// number of fields to check = 3^n - 1
		// vars.length == n
		/*for (int i = 1; i < Math.pow(3,vars.length); i++) {		
			
		}*/
		
		if (vars.length == 2) {
			// neighbors 1 row up (3 neighbors)
			double[] varsTop = vars.clone(); varsTop[0]+=stepSize;
			double[] varsMiddle = vars.clone(); 
			double[] varsBottom = vars.clone(); varsBottom[0]-=stepSize;
			
			if (varsTop[0] < m.upperLimit) {
				if (modifier*m.f(varsTop) < modifier*ex) {
					ex = m.f(varsTop);
					exPos = varsTop;
				}
				
				double[] varsTopLeft = varsTop.clone(); varsTopLeft[1]-=stepSize;
				double[] varsTopRight = varsTop.clone(); varsTopRight[1]+=stepSize;
				
				
				if (varsTopLeft[1] > m.lowerLimit) {
					if (modifier*m.f(varsTopLeft) < modifier*ex) {
						ex = m.f(varsTopLeft);
						exPos = varsTopLeft;
					}
				}
				if (varsTopRight[1] < m.upperLimit) {
					if (modifier*m.f(varsTopRight) < modifier*ex) {
						ex = m.f(varsTopRight);
						exPos = varsTopRight;
					}
				}
			}
			// neighbors same row (2 neighbors)
			double[] varsMiddleLeft = varsMiddle.clone(); varsMiddleLeft[1]-=stepSize;
			double[] varsMiddleRight = varsMiddle.clone(); varsMiddleRight[1]+=stepSize;
			if (varsMiddleLeft[1] > m.lowerLimit) {
				if (modifier*m.f(varsMiddleLeft) < modifier*ex) {
					ex = m.f(varsMiddleLeft);
					exPos = varsMiddleLeft;
				}
			}
			if (varsMiddleRight[1] < m.upperLimit) {
				if (modifier*m.f(varsMiddleRight) < modifier*ex) {
					ex = m.f(varsMiddleRight);
					exPos = varsMiddleRight;
				}
			}
			// neighbors 1 row down (3 neighbors)
			if (varsBottom[0] > m.lowerLimit) {
				double[] varsBottomLeft = varsTop.clone(); varsBottomLeft[1]-=stepSize;
				double[] varsBottomRight = varsTop.clone(); varsBottomRight[1]+=stepSize;
				
				if (modifier*m.f(varsTop) < modifier*ex) {
					ex = m.f(varsTop);
					exPos = varsTop;
				}
				if (varsBottomLeft[1] > m.lowerLimit) {
					if (modifier*m.f(varsBottomLeft) < modifier*ex) {
						ex = m.f(varsBottomLeft);
						exPos = varsBottomLeft;
					}
				}
				if (varsBottomRight[1] < m.upperLimit) {
					if (modifier*m.f(varsBottomRight) < modifier*ex) {
						ex = m.f(varsBottomRight);
						exPos = varsBottomRight;
					}
				}
			}
			
			vars = exPos;
			
		} else {
			System.out.println("only 2d possible atm.");
		}
		//assert(min>=0);
		return exPos;
	}
}
