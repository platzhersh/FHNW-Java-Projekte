package ch.fhnw.edu.rental.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UserServiceTest.class,
	MovieServiceTest.class,
	RentalServiceTest.class
})
public class AllServiceTests {
}