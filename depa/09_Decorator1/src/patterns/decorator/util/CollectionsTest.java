package patterns.decorator.util;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class CollectionsTest {
	private Collection<String> c1;
	private Collection<String> c2;
	
	@Before
	public void setUp() {
		c1 = new LinkedList<>();
		c1.add("one");
		c1.add("two");
		c1.add("three");
		
		c2 = new LinkedList<>();
		c2.add("zero");
		c2.add("one");
	}

	@Test
	public void testView1() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		assertTrue(c.size() == c1.size());
		assertArrayEquals(c.toArray(), c1.toArray());
		assertFalse(c.isEmpty());
		assertTrue(c.containsAll(c));
		assertTrue(c.containsAll(c1));
		assertFalse(c.containsAll(c2));
		
		c1.add("four");
		assertTrue(c.size() == c1.size());
		assertArrayEquals(c.toArray(), c1.toArray());
	}
	
	@Test
	public void testView2() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		Iterator<String> it = c.iterator();
		String s1 = it.next();
		assertEquals(s1, "one");
	}
	

	@Test(expected=UnsupportedOperationException.class)
	public void testEx1() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		c.add("four");
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testEx2() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		c.remove("one");
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testEx3() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		c.remove("");
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testEx4() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		Iterator<String> it = c.iterator();
		it.next();
		it.remove();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testEx5() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		c.clear();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testEx6() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		c.removeAll(c2);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testEx7() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		c.addAll(c2);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testEx8() {
		Collection<String> c = Collections.unmodifiableCollection(c1);
		c.retainAll(c2);
	}
	
}

