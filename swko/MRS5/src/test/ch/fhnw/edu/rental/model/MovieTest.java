/**
 * 
 */
package ch.fhnw.edu.rental.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.sql.Date;

import org.junit.Test;

import ch.fhnw.edu.rental.model.ChildrenPriceCategory;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.RegularPriceCategory;

/**
 * @author christoph.denzler
 * 
 */
public class MovieTest {

    /**
     * Expected exception message.
     */
    private static final String MESSAGE = "not all input parameters are set!";
    
    @Test
    public void testAgeRaiting() {
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        PriceCategory pc = RegularPriceCategory.getInstance();

        int ager = 16;

        Movie m = new Movie("name", d, pc, ager);
        assertEquals(ager, m.getAgeRaiting());
    }

    /**
     * Hashcode and equals are always overridden and tested together. Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#hashCode()}.
     * 
     * @throws InterruptedException should not be thrown
     */
    @Test
    public void testHashCode() throws InterruptedException {
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        PriceCategory pc = RegularPriceCategory.getInstance();
        Movie x = new Movie();
        Movie y = new Movie("A", d, pc, 0);
        Movie z = new Movie("A", d, pc, 0);

        // do we get consistently the same result?
        int h = x.hashCode();
        assertEquals(h, x.hashCode());
        h = y.hashCode();
        assertEquals(h, y.hashCode());

        // do we get the same result from two equal objects?
        h = y.hashCode();
        assertEquals(h, z.hashCode());

        // still the same hashcode after changing rented state?
        z.setRented(true);
        assertEquals(h, z.hashCode());

        z = new Movie("A", d, pc, 0); // get a new Movie
        z.setPriceCategory(ChildrenPriceCategory.getInstance());
        assertEquals(h, z.hashCode());

        // now, let's see if we can get different hashcodes as well
        z = new Movie("B", d, pc, 0);
        assertFalse(h == z.hashCode());
        Thread.sleep(50); // sleep some time to get a different timestamp
        z = new Movie("A", new Date(Calendar.getInstance().getTimeInMillis()), pc, 0);
        assertFalse(h == z.hashCode());
        z = new Movie("A", d, pc, 0);
        z.setId(42);
        assertFalse(h == z.hashCode());
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.Movie#Movie()}.
     */
    @Test
    public void testMovie() {
        Movie m = new Movie();
        assertNull(m.getPriceCategory());
        assertNull(m.getReleaseDate());
        assertNull(m.getTitle());
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     * 
     * @throws InterruptedException must not be thrown
     */
    @Test
    public void testMovieStringPriceCategory() throws InterruptedException {
        // get time before object creation
        Date before = new Date(Calendar.getInstance().getTimeInMillis());
        // spend some time to be able to detect differences in timestamps
        Thread.sleep(100);

        // now allocate new instance
        Movie m = new Movie("A", RegularPriceCategory.getInstance(), 0);
        // get time after object creation
        Thread.sleep(100);
        Date after = new Date(Calendar.getInstance().getTimeInMillis());

        assertEquals("A", m.getTitle());
        assertEquals(RegularPriceCategory.class, m.getPriceCategory().getClass());
        Date releaseDate = m.getReleaseDate();
        assertNotNull(releaseDate);
        assertTrue(before.before(releaseDate));
        assertTrue("Expected release date to be earlier.", after.after(releaseDate));
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testExceptionMovieStringPriceCategory() {
        Movie m = null;
        try {
            m = new Movie(null, RegularPriceCategory.getInstance(), 0);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", null, 0);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        assertNull(m);
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, java.util.Date, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testMovieStringDatePriceCategory() {
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        Movie m = new Movie("B", d, RegularPriceCategory.getInstance(), 0);

        assertNotNull(m);
        assertEquals("B", m.getTitle());
        assertEquals(RegularPriceCategory.class, m.getPriceCategory().getClass());
        Date releaseDate = m.getReleaseDate();
        assertNotNull(releaseDate);
        assertEquals(d, releaseDate);
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, java.util.Date, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testExceptionMovieStringDatePriceCategory() {
        Movie m = null;
        try {
            m = new Movie(null, new Date(Calendar.getInstance().getTimeInMillis()), RegularPriceCategory.getInstance(),
                    0);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", null, RegularPriceCategory.getInstance(), 0);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", new Date(Calendar.getInstance().getTimeInMillis()), null, 0);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        assertNull(m);
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.Movie#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
    	Movie m1 = new Movie();
    	Movie m2 = new Movie();
    	
    	//Test for basic functionality
    	assertTrue(m1.equals(m2));
    	assertTrue(m1.equals(m1));
    	assertFalse(m1.equals(null));
    	assertFalse(m1.equals(new Object()));
    	
    	//Test functionality on Title difference
    	m2.setTitle("Movie 2");
    	assertFalse(m1.equals(m2));
    	m1.setTitle("Movie 1");
    	assertFalse(m1.equals(m2));
    	
    	//reset
    	m1 = new Movie();
    	m2 = new Movie();
    	
    	//Test functionality on ID difference
    	m1.setId(1);
    	m2.setId(2);
    	assertFalse(m1.equals(m2));
    	
    	//reset
    	m1 = new Movie();
    	m2 = new Movie();
    	
    	//Test functionality on Releasedate difference
    	m2.setReleaseDate(new Date(800000));
    	assertFalse(m1.equals(m2));
    	m1.setReleaseDate(new Date(900000));
    	assertFalse(m1.equals(m2));
    }

    @Test(expected = IllegalStateException.class)
    public void testSetTitle() {
        Movie m = new Movie();
        m.setTitle("Hallo");
        assertEquals("Hallo", m.getTitle());
        m.setTitle(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testSetReleaseDate() {
        Movie m = new Movie();
        Date d = new Date(Calendar.getInstance().getTimeInMillis());
        m.setReleaseDate(d);
        assertEquals(d, m.getReleaseDate());
        m.setReleaseDate(null);
    }
}
