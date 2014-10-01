package ch.fhnw.edu.rental.services;

import java.util.List;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.service.RentalServiceException;

public interface UserService {
	public User getUserById(Long id) throws RentalServiceException;
	
	public void saveOrUpdateUser(User user) throws RentalServiceException;
	
	public void deleteUser(User user) throws RentalServiceException;
	
	public List<User> getAllUsers() throws RentalServiceException;
	
	public List<User> getUsersByName(String name) throws RentalServiceException;
	
	public Rental rentMovie(User user, Movie movie, int days) throws RentalServiceException;
	
	public void returnMovie(User user, Movie movie) throws RentalServiceException;
}
