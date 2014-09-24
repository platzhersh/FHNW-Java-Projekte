package ch.fhnw.depa.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSimpleCollection {

	private SimpleCollection<Integer> col;

	@Before
	public void setUp() throws Exception {
		col = new SimpleCollection<Integer>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		assertEquals(0, col.size());
		assertTrue(col.add(1));
		assertEquals(1, col.size());
		assertTrue(col.add(2));
		assertTrue(col.add(4));
		assertEquals(3, col.size());
		assertTrue(col.add(null));
		assertEquals(4, col.size());
	}

	@Test(expected = NoSuchElementException.class)
	public void testIterator() {
		// test iterator on empty collection
		Iterator<Integer> it = col.iterator();
		assertNotNull(it);
		assertFalse(it.hasNext());

		// test iterator on one element collection
		col.add(2);
		it = col.iterator();
		assertNotNull(it);
		assertTrue(it.hasNext());
		Integer i = it.next();
		assertNotNull(i);
		assertEquals(2, i.intValue());
		assertFalse(it.hasNext());

		// test iterator on three element collection
		col.add(11);
		col.add(11);
		it = col.iterator();
		assertNotNull(it);
		assertTrue(it.hasNext());
		i = it.next();
		assertNotNull(i);
		assertEquals(11, i.intValue());
		assertTrue(it.hasNext());
		i = it.next();
		assertNotNull(i);
		assertEquals(11, i.intValue());
		assertTrue(it.hasNext());
		i = it.next();
		assertNotNull(i);
		assertEquals(2, i.intValue());
		assertFalse(it.hasNext());
		it.next(); // provoke an illegal state exception
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testIteratorRemove() {
		col.add(3);
		col.add(5);
		Iterator<Integer> it = col.iterator();
		try {
			it.remove();
			fail();
		} catch (UnsupportedOperationException e) {
		}
		it.next();
		it.remove();
	}

	@Test
	public void testIteratorConcurrency() {
		col.add(3);
		col.add(5);

		Iterator<Integer> it1 = col.iterator();
		Integer i1 = it1.next();
		assertEquals(5, i1.intValue());

		col.add(7); // invalidate it1
		try {
			it1.hasNext();
			fail();
		} catch (ConcurrentModificationException e) {
		}
		try {
			it1.next();
			fail();
		} catch (ConcurrentModificationException e) {
		}
		try {
			it1.remove();
			fail();
		} catch (UnsupportedOperationException e) {
		}

		Iterator<Integer> it2 = col.iterator();
		it2.next();
		it2.next();
		it2.next(); // it2 has reached end of collection
		col.add(11); // invalidate it2 anyways
		try {
			it2.hasNext();
			fail();
		} catch (ConcurrentModificationException e) {
		}
		try {
			it2.next();
			fail();
		} catch (ConcurrentModificationException e) {
		}
		try {
			it2.remove();
			fail();
		} catch (UnsupportedOperationException e) {
		}

	}

	@Test
	public void testIsEmpty() {
		assertEquals(0, col.size());
		assertTrue(col.isEmpty());
		col.add(1);
		assertFalse(col.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(0, col.size());
		col.add(1);
		assertEquals(1, col.size());
		col.add(1);
		assertEquals(2, col.size());
		col.add(3);
		assertEquals(3, col.size());
	}

	@Test
	public void testContains() {
		assertFalse(col.contains(5));
		col.add(1);
		col.add(2);
		assertFalse(col.contains(5));
		assertTrue(col.contains(2));
		assertTrue(col.contains(1));
	}

	@Test
	public void testToArray() {
		Object[] arr = col.toArray();
		assertNotNull(arr);
		assertEquals(0, arr.length);

		col.add(815);
		arr = col.toArray();
		assertNotNull(arr);
		assertEquals(1, arr.length);
		assertEquals(815, arr[0]);

		col.add(4711);
		arr = col.toArray();
		assertNotNull(arr);
		assertEquals(2, arr.length);
		assertFalse(arr[0].equals(arr[1]));

		assertTrue(arr[0].equals(815) || arr[1].equals(815));
		assertTrue(arr[0].equals(4711) || arr[1].equals(4711));
	}

	@Test
	public void testToArrayTArray() {
		// fill some data into collection
		col.add(815);
		col.add(4711);

		// test with empty array argument
		Integer[] orig = new Integer[0];
		Integer[] arr = col.toArray(orig);

		assertNotNull(arr);
		assertFalse(arr == orig);
		assertEquals(2, arr.length);
		assertFalse(arr[0].equals(arr[1]));

		assertTrue(arr[0].equals(815) || arr[1].equals(815));
		assertTrue(arr[0].equals(4711) || arr[1].equals(4711));

		// test with correctly sized array argument
		orig = new Integer[2];
		arr = col.toArray(orig);

		assertNotNull(arr);
		assertSame(arr, orig);
		assertEquals(2, arr.length);
		assertFalse(arr[0].equals(arr[1]));

		assertTrue(arr[0].equals(815) || arr[1].equals(815));
		assertTrue(arr[0].equals(4711) || arr[1].equals(4711));

		// test with over sized array argument
		orig = new Integer[4];
		orig[2] = 222;
		orig[3] = 333;
		arr = col.toArray(orig);

		assertNotNull(arr);
		assertSame(arr, orig);
		assertEquals(4, arr.length);
		assertNull(arr[2]);
		assertFalse(arr[0].equals(arr[1]));

		assertTrue(arr[0].equals(815) || arr[1].equals(815));
		assertTrue(arr[0].equals(4711) || arr[1].equals(4711));
	}

	@Test
	public void testToArrayTArrayEmpty() {
		// test with over sized array argument on empty collection
		Integer[] orig = new Integer[4];
		orig[0] = 11;
		orig[1] = 12;
		orig[3] = 12;
		Integer[] arr = col.toArray(orig);
		assertNull(arr[0]);
	}

	@Test(expected = NullPointerException.class)
	public void testToArrayTArrayNull() {
		col.toArray(null);
	}

	@Test(expected = ArrayStoreException.class)
	public void testToArrayTArrayWrongType() {
		col.add(4711);
		col.add(815);
		col.toArray(new String[4]);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove() {
		col.add(42);
		col.remove(42);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear() {
		col.add(1);
		col.clear();
	}

	@Test
	public void testContainsAll() {
		col.add(3);
		col.add(7);
		col.add(11);
		List<Integer> l = new LinkedList<Integer>();
		l.add(7);
		l.add(11);
		assertTrue(col.containsAll(l));
		l.add(13);
		assertFalse(col.containsAll(l));
		List<String> sl = new LinkedList<String>();
		sl.add("Hello");
		sl.add("World");
		assertFalse(col.containsAll(sl));
	}

	@Test
	public void testAddAll() {
		List<Integer> l = new LinkedList<Integer>();
		l.add(17);
		l.add(19);
		l.add(23);
		assertTrue(col.addAll(l));
		assertEquals(3, col.size());
		assertTrue(col.containsAll(l));

		// nothing should change when nothing is added
		assertFalse(col.addAll(new LinkedList<Integer>()));
		assertEquals(3, col.size());
		assertTrue(col.containsAll(l));

		// elements can be added twice
		assertTrue(col.addAll(l));
		assertEquals(6, col.size());
		assertTrue(col.containsAll(l));
	}

	@Test(expected = NullPointerException.class)
	public void testAddAllException() {
		col.addAll(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAll() {
		col.add(3);

		List<Integer> r = new LinkedList<Integer>();
		r.add(1);
		r.add(3);
		r.add(7);

		col.removeAll(r);

	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRetainAll() {
		col.add(4);
		List<Integer> r = new LinkedList<Integer>();
		r.add(1);
		r.add(3);
		r.add(7);

		col.retainAll(r);
	}

}
