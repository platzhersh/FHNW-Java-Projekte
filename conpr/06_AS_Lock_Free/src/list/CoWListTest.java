package list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class CoWListTest {
	
	public CoWList<Integer> createList() {
    	// TODO return an instance of your copy-on-write list implementation, e.g.
		// return new CoWLinkedList<>();
		return null;
	}

	@Test(expected = NoSuchElementException.class)
	public void testEmptyList() {
		CoWList<Integer> l = createList();
		assertEquals(0,  l.size());
		l.removeFirst();
	}
	
	@Test
	public void testInsert() {
		CoWList<Integer> l = createList();
		assertEquals(0,  l.size());
		l.addFirst(1);
		assertEquals(1, l.size());
		l.addFirst(42);
		assertEquals(2, l.size());
	}
	
	@Test
	public void testIterator() {
		CoWList<Integer> l = createList();
		l.addFirst(1);
		l.addFirst(42);
		
		Iterator<Integer> it = l.iterator();
		int i = 0;
		while(it.hasNext()) {
			int next = it.next();
			if(i == 0) assertEquals(42, next);
			else if(i == 1) assertEquals(1, next);
			else if(i == 2) fail("Too many elements!");
			
			i++;
		}
		assertEquals(2, i);
	}
	
	@Test
	public void testIteratorNoModException() {
		CoWList<Integer> l = createList();
		l.addFirst(1);
		l.addFirst(42);
		
		for (@SuppressWarnings("unused") Integer integer : l) {
			l.addFirst(3); // Modification during iteration
			l.addFirst(8);
		}
	}

}
