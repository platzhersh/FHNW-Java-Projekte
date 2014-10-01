package ch.fhnw.edu.rental.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.services.RentalService;
import ch.fhnw.edu.rental.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/business.xml", "/datasource.xml"})
@Transactional
public class UserServiceTest {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserService userService;
	@Autowired
	private RentalService rentalService;

	@Test
	public void testGetAllUsers() {
		List<User> users = userService.getAllUsers();
		assertEquals(4, users.size());
	}

	@Test
	public void testSaveUser() {
		User user = new User("Tester", "Hugo");
		assertNull("id must be null as long as object is not yet saved", user.getId());
		userService.saveOrUpdateUser(user);
		assertNotNull("id must be assigned after insertion into db", user.getId());
		List<User> users = userService.getAllUsers();
		assertEquals(5, users.size());
	}

	@Test
	public void testCreateUser() {
		User user = new User("Tester", "Hugo");
		userService.saveOrUpdateUser(user);
		List<User> users = userService.getAllUsers();
		assertTrue("users list must contain new user", users.contains(user));
	}

	// Tests whether all rentals are deleted if a user is deleted
	@Test
	public void testDeleteUser() {
		List<Rental> rentals = rentalService.getAllRentals();
		assertEquals(3, rentals.size());
		List<User> users = userService.getUsersByName("Keller");
		assertEquals(1, users.size());
		User user = users.get(0);
		assertEquals(2, user.getRentals().size());
		userService.deleteUser(user);
		rentals = rentalService.getAllRentals();
		assertEquals(1, rentals.size());
	}

	@Test
	public void testGetUsersByName() {
		List<User> users = userService.getUsersByName("Keller");
		assertEquals(1, users.size());
		User user = users.get(0);
		assertEquals("Keller", user.getLastName());
	}
	
	@Test
	public void testDeleteRental() {
		List<User> users = userService.getUsersByName("Keller");
		assertEquals(1, users.size());
		List<Rental> rentals = rentalService.getAllRentals();
		assertEquals(3, rentals.size());
		User user = users.get(0);
		assertEquals("Keller", user.getLastName());
		assertEquals(2, user.getRentals().size());
		Rental rental = user.getRentals().get(1);
		userService.returnMovie(user, rental.getMovie());
		assertEquals(1, user.getRentals().size());
		rentals = rentalService.getAllRentals();
		assertEquals(2, rentals.size());
	}

	@Test
	public void testGetRentalsOfUserUsingLazyLoading() {
		List<User> users = userService.getUsersByName("Keller");
		assertEquals(1, users.size());
		User user = users.get(0);
		assertEquals("Keller", user.getLastName());
		List<Rental> rentals = user.getRentals();
		assertEquals(2, rentals.size());
		Rental rental = rentals.get(0);
		Movie movie = rental.getMovie();
		assertEquals("Lord of the Rings", movie.getTitle());
	}
	
	@Test
	public void testGetEmailAddress() {
		List<User> users = userService.getUsersByName("Keller");
		assertEquals(1, users.size());
		User user = users.get(0);
		assertEquals("Keller", user.getLastName());
		assertEquals("marc.keller@gmail.com", user.getEmail());

		users = userService.getUsersByName("Knecht");
		assertEquals(1, users.size());
		user = users.get(0);
		assertEquals("Knecht", user.getLastName());
	}
}

