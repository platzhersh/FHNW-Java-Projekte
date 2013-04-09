package ch.fhnw.ds.rest.msg.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import ch.fhnw.ds.rest.msg.resources.MsgResource;


public class MsgApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public MsgApplication() {
		singletons.add(new MsgResource());
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

