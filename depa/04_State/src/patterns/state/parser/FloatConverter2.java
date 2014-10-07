package patterns.state.parser;

import patterns.state.parser.state.IDigitState;
import patterns.state.parser.state.State0;

public class FloatConverter2 {

	IDigitState state;
	IDigitState S0, S1, S2, S3, S4, S5, S6;
	
	
	/* Main */
	public static void main(String[] args) throws Exception {
		
	}
	
	/* Konstruktor */
	public FloatConverter2() {
		S0 = new State0();
	}
	
}
