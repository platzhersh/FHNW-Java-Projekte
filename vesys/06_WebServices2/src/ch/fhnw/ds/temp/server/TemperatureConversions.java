package ch.fhnw.ds.temp.server;

import java.math.BigDecimal;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebParam.Mode;
import javax.xml.ws.Holder;

@WebService
public interface TemperatureConversions {


    /**
     * Converts a Celcius temperature to a Fahrenheit value
     */
	public void celciusToFahrenheit(
			@WebParam(name = "arg", mode = Mode.INOUT)
			Holder<BigDecimal> parameter);

    /**
     * Converts a Fahrenheit temperature to a Celcius value
     */
    public void fahrenheitToCelsius(
    		@WebParam(name = "arg", mode = Mode.INOUT)
    		Holder<BigDecimal> parameter);
    
    public void getRandomTemperature(
    		@WebParam(name = "resultC", mode = Mode.OUT)
    		Holder<BigDecimal> resultC,
    		@WebParam(name = "resultF", mode = Mode.OUT)
    		Holder<BigDecimal> resultF);	
}
