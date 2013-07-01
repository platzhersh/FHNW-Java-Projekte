package ch.fhnw.vesys;

import java.net.Inet4Address;


public class Main {
	
	static Inet4Address local;
	
	
	public static void main(String args[]){
		try {
			local = (Inet4Address) Inet4Address.getLocalHost();
		}
		catch (Exception e) { System.out.println(e); }
		
		System.out.println("Localhost: " + local.toString());
		System.out.println("Hostname: " + local.getHostName());
		System.out.println("Canonical: " + local.getCanonicalHostName());
		System.out.println("Address: " + local.getHostAddress());		
	} 	
}
