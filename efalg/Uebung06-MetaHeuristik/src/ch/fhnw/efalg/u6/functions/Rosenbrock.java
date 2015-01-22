package ch.fhnw.efalg.u6.functions;

public class Rosenbrock implements Function {

	@Override
	public double Obj_f(final double[] x) {
		return (1.0-x[0])*(1.0-x[0]) + 100.0*(x[1]-x[0]*x[0])*(x[1]-x[0]*x[0]);
	}

	@Override
	public double expectedMinima() {
		return 0.0;
	}

	@Override
	public double[] expectedMinimaPos() {
		return new double[]{1, 1};
	}

}
