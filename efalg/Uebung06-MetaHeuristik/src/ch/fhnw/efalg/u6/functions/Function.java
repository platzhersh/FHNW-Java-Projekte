package ch.fhnw.efalg.u6.functions;

public interface Function {

	public double Obj_f(final double[] x);
	
	public double expectedMinima();
	
	public double[] expectedMinimaPos();
}
