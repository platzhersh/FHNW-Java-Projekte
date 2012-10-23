package unittesting;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {
    
    Person person;
    String validName, invalidName1, invalidName2;
    Calendar validBirthdate, invalidBirthdate;
    int validZip;
    
    @Before
    public void setUp() throws Exception {
        validName = "Peter";
        invalidName1 = "";
        invalidName2 = "12345123451234512345123451234512345";
        
        validZip = 5400;
        
        validBirthdate = Calendar.getInstance();
        validBirthdate.set(2000, 0, 2);
        
        invalidBirthdate = Calendar.getInstance();
        invalidBirthdate.set(2042, 1, 1);
        
        person = new Person(validName,validZip,validBirthdate);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPerson() {
        assertNotNull("Person Object successfully created.",new Person(validName,validZip,validBirthdate));
        // name = ""
        try {
            new Person(invalidName1,validZip,validBirthdate);
            fail("Object created with empty name.");
        } catch (IllegalArgumentException e) {
            assertEquals("illegal name",e.getMessage());
        }
        // name.length > 30
        try {
            new Person(invalidName2,validZip,validBirthdate);
            fail("Object created with name > 30 characters.");
        } catch (IllegalArgumentException e) {
            assertEquals("illegal name",e.getMessage());
        }
        // birthdate in the future
        try {
            new Person(validName,validZip,invalidBirthdate);
            fail("Object created with illegal birthdate.");
        } catch (IllegalArgumentException e) {
            assertEquals("illegal birthdate",e.getMessage());
        }
    }

    @Test
    public void testGetName() {
        assertEquals(validName,person.getName());
    }

    @Test
    public void testGetZip() {
        assertEquals(validZip,person.getZip());
    }

    @Test
    public void testGetBirthdate() {
        assertEquals(validBirthdate,person.getBirthdate());
    }

    @Test
    public void testGetAge() {
        assertEquals(validName,person.getName());
    }

}
