package ch.fhnw.edu.rental.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.daos.impl.ManagedDAO;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.service.RentalServiceException;
import ch.fhnw.edu.rental.services.UserService;

public class UserServiceImpl implements UserService {
	private Log log = LogFactory.getLog(this.getClass());
	
	private UserDAO userDAO;
	private RentalDAO rentalDAO;
	private MovieDAO movieDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setRentalDAO(RentalDAO rentalDAO) {
		this.rentalDAO = rentalDAO;
	}

	public void setMovieDAO(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	@Override
	public User getUserById(Long id) throws RentalServiceException {
		User user = this.userDAO.getById(id);
// fix: explicitly load the referenced objects in order to avoid a LazyInitializationException
//		for(Rental r : user.getRentals()) {
//			r.getId();
//		}
		return user;
	}
	
	@Override
	public List<User> getAllUsers() throws RentalServiceException {
		List<User> users = userDAO.getAll();
		if (log.isDebugEnabled()) {
			log.debug("getAllUsers() done");
		}
		return users;
	}

	@Override
	public void saveOrUpdateUser(User user) throws RentalServiceException {
		userDAO.saveOrUpdate(user);
		if (log.isDebugEnabled()) {
			log.debug("saved or updated user[" + user.getId() + "]");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void deleteUser(User user) throws RentalServiceException {
		if (user == null) {
			throw new RentalServiceException("'user' parameter is not set!");
		}
		if(userDAO instanceof ManagedDAO<?>) {
			user = ((ManagedDAO<User>)userDAO).manage(user);
		}

		userDAO.delete(user);	// if (user.getRentals().size()>0) associated rentals 
								// have to be deleted by userDAO.delete(user) as well
		if (log.isDebugEnabled()) {
			log.debug("user[" + user.getId() + "] deleted");
		}
	}

	@Override
	public List<User> getUsersByName(String name) throws RentalServiceException {
		List<User> users = userDAO.getByName(name);
		return users;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Rental rentMovie(User user, Movie movie, int days)
			throws RentalServiceException {
		if (user == null) 
			throw new IllegalArgumentException("parameter 'user' is null!");
		if (movie == null) 
			throw new IllegalArgumentException("parameter 'movie' is null!");
		if (days < 1)
			throw new IllegalArgumentException("parameter 'days' must be > 0");

		// In case of managed daos, the detached entities are merged first.
		if(movieDAO instanceof ManagedDAO<?>) {
			movie = ((ManagedDAO<Movie>)movieDAO).manage(movie);
		}
		if(userDAO instanceof ManagedDAO<?>) {
			user = ((ManagedDAO<User>)userDAO).manage(user);
		}
		
		Rental rental = new Rental(user, movie, days);
		rentalDAO.saveOrUpdate(rental);
		// the constructor of rental changed the movie to rented, this change must
		// be persisted in case of non managed DAOs.
		if(! (movieDAO instanceof ManagedDAO)){
			movieDAO.saveOrUpdate(movie);
		}
		// Similarly, the user has to be saved as he refers to an additional rental
		// in case that the user is not managed and in case that the user is the 
		// owner of the association.
		// if(! (userDAO instanceof ManagedDAO)) {
		// 	userDAO.saveOrUpdate(user);
		// }
		
		return rental;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void returnMovie(User user, Movie movie)
			throws RentalServiceException {
		if (user == null) 
			throw new IllegalArgumentException("parameter 'user' is null!");
		if (movie == null) 
			throw new IllegalArgumentException("parameter 'movie' is null!");
		if(userDAO instanceof ManagedDAO<?>) {
			user = ((ManagedDAO<User>)userDAO).manage(user);
		}
		if(movieDAO instanceof ManagedDAO<?>) {
			movie = ((ManagedDAO<Movie>)movieDAO).manage(movie);
		}

		Rental rentalToRemove = null;
		for (Rental rental : user.getRentals()) {
			if (rental.getMovie().equals(movie)) {
				rentalToRemove = rental;
				break;
			}
		}
		
		user.getRentals().remove(rentalToRemove);
		rentalToRemove.getMovie().setRented(false);
		rentalDAO.delete(rentalToRemove);
	}
}
