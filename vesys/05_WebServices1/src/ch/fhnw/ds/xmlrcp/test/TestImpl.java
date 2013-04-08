package ch.fhnw.ds.xmlrcp.test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;


public class TestImpl {
	public TestImpl(){ System.out.println("TestImpl ctor called");}
	public String echo(Object x) { return "Object"; }
	public String echo(int x) { return "int"; }
	public String echo(Integer x) { return "Integer"; }
	public String echo(long x) { return "long"; }
	public String echo(Long x) { return "Long"; }
	public String echo(float x) { return "float"; }
	public String echo(Float x) { return "Float"; }
	public String echo(double x) { return "double"; }
	public String echo(Double x) { return "Double"; }
	public String echo(BigInteger x) { return "BigInteger"; }

	public String echo(boolean x) { return "boolean"; }
	public String echo(Boolean x) { return "Boolean"; }
	
	public String echo(java.util.Date x) { return "java.util.Date"; }
	public String echo(java.sql.Date x) { return "java.sql.Date"; }

	public String echo(String x) throws TestException { 
		if(x.equals("ex")) throw new RuntimeException("argument must not be `ex`");
		else if(x.equals("tex")) throw new TestException();
		return "String"; 
	}

	public String echo(X x) { return "X"; }

	public String echo(byte[] x) { return "byte[]"; }
	public String echo(Object[] x) { return "Object[]"; }
	
	public String echo(Map x) { return "Map"; }
	public String echo(HashMap x) { return "HashMap"; }
	public String echo(TreeMap x) { return "TreeMap"; }
	public String echo(Hashtable x) { return "Hashtable"; }
}
