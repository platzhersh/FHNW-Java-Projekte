package server.rest.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import server.MyBank;


public class BankApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();
	
	
	public BankApplication() {
		singletons.add(new BankResource());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
