/*
 * Copyright (c) 2010-2013 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.conpr.jmm.peterson;

import java.util.HashMap;
import java.util.Map;

/**
 * Lock only for two threads.
 * @see http://de.wikipedia.org/wiki/Algorithmus_von_Peterson
 */
public class PetersonMutex implements Mutex {
	/** Associates the two threads with an index. */
	private final Map<Thread, Integer> idToIndex = new HashMap<>();

	/** Entry enter[i] indicates that thread i wants to get the lock. */
	private final boolean[] enter = { false, false };
	
	/** Defines which thread proceeds if both threads acquire the lock. */
	private int turn = 0;

	/** Register the two threads for simplicity. */
	public PetersonMutex(Thread t0, Thread t1) {
		idToIndex.put(t0, 0);
		idToIndex.put(t1, 1);
	}

	private int getIndex() {
		return idToIndex.get(Thread.currentThread());
	}

	public void lock() {
		int index = getIndex();
		int otherIndex = 1 - index;

		@SuppressWarnings("unused")
		long t = 0;

		enter[index] = true;
		turn = otherIndex;
		while ( enter[otherIndex] && turn != index) {
//			if(System.currentTimeMillis()-t > 100) {
//				System.out.println(turn);
//				t = System.currentTimeMillis();
//			}
		}
	}

	public void unlock() {
		enter[getIndex()] = false;
	}
}
