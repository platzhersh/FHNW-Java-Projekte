package ch.fhnw.edu.rental.test;

import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.PriceCategoryRegular;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.services.impl.util.MailModelHelper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/testContext.xml", "/datasource-annotation.xml" })
public class MailModelHelperTest {
	@Autowired
	private ApplicationContext context;
	
	private Rental rental;
	
    @Before
    public void setUp() {
    	User user = new User("Tester", "Hugo");
    	user.setEmail("hugo.tester@gmail.com");
    	PriceCategory priceCategory = new PriceCategoryRegular();
    	Movie movie = new Movie("Test Movie", new Date(), priceCategory);
    	rental = new Rental(user, movie, 10);
    }
    
    @Test
    public void testDefaultCreateModel() throws MessagingException {
    	MailModelHelper helper = (MailModelHelper) context.getBean("mailModelHelper");
    	Map<String, Object> model = helper.fillModel(rental);
    	Assert.assertNotNull(model);
    	Assert.assertNotNull(model.get("user"));
    	Assert.assertNotNull(model.get("movie"));
    	User user = (User) model.get("user");
    	Assert.assertEquals("Tester", user.getLastName());
    	Movie movie = (Movie) model.get("movie");
    	Assert.assertEquals("Test Movie", movie.getTitle());
    }

}
