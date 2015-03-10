/*
 * Copyright (c) 2010-2013 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.conpr.jmm.peterson;

public interface Mutex {
	void lock();
	void unlock();
}
