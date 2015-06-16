package ch.fhnw.edu.emoba.lab6;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RGBColor implements Serializable {
	int r;
	int g;
	int b;
	
	@Override
	public String toString() {
		return "r="+r+", g="+g+", b="+b;
	}
}
