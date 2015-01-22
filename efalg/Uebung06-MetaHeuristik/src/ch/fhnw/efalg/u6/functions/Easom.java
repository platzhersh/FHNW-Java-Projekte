package ch.fhnw.efalg.u6.functions;

public class Easom implements Function {

	@Override
	public double Obj_f(double[] x) {
		return -Math.cos(x[0]) * Math.cos(x[1]) * Math.exp(
				- (x[0] - Math.PI) * (x[0] - Math.PI) - (x[1] - Math.PI) * (x[1] - Math.PI));
	}

	@Override
	public double expectedMinima() {
		return -1;
	}

	@Override
	public double[] expectedMinimaPos() {
		return new double[]{ Math.PI, Math.PI };
	}
	
}
