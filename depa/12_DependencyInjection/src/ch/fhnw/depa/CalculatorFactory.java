package ch.fhnw.depa;

import java.io.Serializable;

/***
 * 
 * This class is a JavaBean
 * 
 * http://www.avajava.com/tutorials/lessons/what-is-a-javabean.html
 * 
 * @author chregi
 *
 */
public class CalculatorFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1861694962670785116L;
	
	private ComponentFactory fact;
	
	public void setComponentFactory(ComponentFactory fact) {
		this.fact = fact;
	}
}
