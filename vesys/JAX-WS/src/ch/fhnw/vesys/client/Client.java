package ch.fhnw.vesys.client;

import java.util.Currency;

import ch.fhnw.vesys.jaxws.CurrencyConvertor;
import ch.fhnw.vesys.jaxws.CurrencyConvertorSoap;

public class Client {
	CurrencyConvertor cc = new CurrencyConvertor();
	
	public void main(String[] args) {
		CurrencyConvertorSoap ccSoap = cc.getCurrencyConvertorSoap();
		//System.out.println(ccSoap.conversionRate(, "CHF"));
	}
}
