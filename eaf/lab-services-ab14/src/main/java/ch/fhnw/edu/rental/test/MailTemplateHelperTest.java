package ch.fhnw.edu.rental.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.services.impl.util.MailTemplateHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/testContext.xml",
		"/datasource-annotation.xml" })
public class MailTemplateHelperTest {
	@Autowired
	private ApplicationContext context;

	private Map<String, Object> model = new HashMap<String, Object>();

	@Before
	public void setUp() {
		User user = new User("Tester", "Hugo");
		user.setEmail("hugo.tester@gugus.com");
		PriceCategory priceCategory = new PriceCategoryRegular();
		Movie movie = new Movie("Test Movie", new Date(), priceCategory);

		model.put("user", user);
		model.put("movie", movie);
	}

	@Test
	public void testDefaultPrepareMailMessage() {
		MailTemplateHelper helper = (MailTemplateHelper) context.getBean("javaMailTemplateHelper");

		String message = helper.mergeTemplate(model,
				"%s hat den Film %s bestellt.");
		Assert.assertNotNull(message);
		Assert.assertTrue(message.contains("Test Movie"));
		Assert.assertTrue(message.contains("Hugo"));
	}

	@Test
	public void testVelocityPrepareMailMessage() {
		MailTemplateHelper helper = (MailTemplateHelper) context.getBean("velocityMailTemplateHelper");
		String message = helper.mergeTemplate(model, "rentalReminder.vm");
		Assert.assertNotNull(message);
		Assert.assertTrue(message.contains("Test Movie"));
		Assert.assertTrue(message.contains("Hugo"));
	}
}
