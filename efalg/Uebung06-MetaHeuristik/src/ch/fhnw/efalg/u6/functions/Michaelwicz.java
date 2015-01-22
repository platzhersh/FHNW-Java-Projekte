package ch.fhnw.efalg.u6.functions;

public class Michaelwicz implements Function {

	@Override
	public double Obj_f(double[] x) {
		return -Math.sin(x[0]) * Math.pow(Math.sin(x[0] * x[0] / Math.PI), 2 * 10)
				-Math.sin(x[1]) * Math.pow(Math.sin(2 * x [1] * x[1] / Math.PI), 2 * 10);
	}

	@Override
	public double expectedMinima() {
		return -1.8013;
	}

	@Override
	public double[] expectedMinimaPos() {
		return new double[]{2.20319, 1.57049};
	}
	

}
