package patterns.clone.company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CloningTest {
	private Company c1, c2;
	private Person p1, p2, p3;
	private ArrayList<Person> employees;

	@Before
	public void setUp() throws Exception {
		p1 = new Person(25, "P1");
		p2 = new Person(26, "P2");
		p3 = new Person(22, "P3");
		employees = new ArrayList<Person>();
		employees.add(p1);
		employees.add(p2);
		employees.add(p3);
		c1 = new Company("C1", employees);
	}

	@Test
	public void testCompanyClone() {
		c2 = c1.clone();
		assertTrue(c1 != c2);
		assertEquals(c1.getClass(), c2.getClass());
		assertEquals(c1.getName(), c2.getName());
	}

	@Test
	public void testPersonClone() {
		p2 = p1.clone();
		assertTrue(p1 != p2);
		assertEquals(p1.getClass(), p2.getClass());
		assertEquals(p1.getName(), p2.getName());
		assertEquals(p1.getAge(), p2.getAge());
	}

	@Test
	public void testShallow() {
		c2 = c1.clone();

		assertEquals(c1, c2);
	}

	@Test
	public void testDeep1() {
		c2 = c1.clone();
		assertEquals(c1, c2);
		c1.addEmployee(new Person(22, "new"));
		assertFalse(c1.equals(c2));
	}

	@Test
	public void testDeep2() {
		c2 = c1.clone();
		assertEquals("this is not even a shallow copy", c1, c2);
		c1.addEmployee(new Person(22, "new"));
		assertFalse("this is only a shallow copy", c1.equals(c2));
		c2 = c1.clone();
		p1.setName("Changed");
		assertFalse("you didn't copy deep enough", c1.equals(c2));
	}

}
