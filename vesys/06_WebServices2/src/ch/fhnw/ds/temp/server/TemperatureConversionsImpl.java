package ch.fhnw.ds.temp.server;

import java.math.BigDecimal;
import java.util.Random;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebParam.Mode;
import javax.xml.ws.Holder;

@WebService
public class TemperatureConversionsImpl implements TemperatureConversions {
	
	static final BigDecimal dec_32 = new BigDecimal(32);
	static final BigDecimal dec_1_8 = new BigDecimal("1.8");

	@Override
	public void celciusToFahrenheit(
			@WebParam(name = "arg", mode = Mode.INOUT)
			Holder<BigDecimal> parameter){
		parameter.value = parameter.value.multiply(dec_1_8).add(dec_32);
	}

	@Override
    public void fahrenheitToCelsius(
    		@WebParam(name = "arg", mode = Mode.INOUT)
    		Holder<BigDecimal> parameter){
		parameter.value = parameter.value.subtract(dec_32).divide(dec_1_8);
	}

    
    public void getRandomTemperature(
    		@WebParam(name = "resultC", mode = Mode.OUT)
    		Holder<BigDecimal> resultC,
    		@WebParam(name = "resultF", mode = Mode.OUT)
    		Holder<BigDecimal> resultF){
    	Random r = new Random();
    	resultC.value = new BigDecimal(-200 + r.nextInt(3000)/10.0);
    	resultF.value = resultC.value;
    	this.celciusToFahrenheit(resultF);
    }

}
