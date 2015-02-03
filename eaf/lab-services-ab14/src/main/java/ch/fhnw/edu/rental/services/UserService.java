package ch.fhnw.edu.rental.services;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

@Transactional
public interface UserService {
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public User getUserById(Long id) throws RentalServiceException;
	
	public void saveOrUpdateUser(User user) throws RentalServiceException;
	
	public void deleteUser(User user) throws RentalServiceException;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public List<User> getAllUsers() throws RentalServiceException;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public List<User> getUsersByName(String name) throws RentalServiceException;
	
	public Rental rentMovie(User user, Movie movie, int days) throws RentalServiceException;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public void returnMovie(User user, Movie movie) throws RentalServiceException;
}
