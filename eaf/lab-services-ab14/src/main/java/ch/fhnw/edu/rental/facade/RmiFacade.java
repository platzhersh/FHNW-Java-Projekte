package ch.fhnw.edu.rental.facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ch.fhnw.edu.rental.dto.MovieDTO;
import ch.fhnw.edu.rental.dto.RentalDTO;
import ch.fhnw.edu.rental.dto.UserDTO;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.services.MovieService;
import ch.fhnw.edu.rental.services.RentalService;
import ch.fhnw.edu.rental.services.RmiMovieService;
import ch.fhnw.edu.rental.services.RmiRentalService;
import ch.fhnw.edu.rental.services.RmiUserService;
import ch.fhnw.edu.rental.services.UserService;

public class RmiFacade implements RmiMovieService, RmiUserService, RmiRentalService {
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RentalService rentalService;
	
	@Override
	public List<MovieDTO> getAllMovies() {
		List<Movie> movies = movieService.getAllMovies();
		ArrayList<MovieDTO> dtos = new ArrayList<MovieDTO>();
		for (Movie m : movies) {
			MovieDTO dto = new MovieDTO(m.getId(), m.getTitle(), m.isRented(),
					m.getReleaseDate(), m.getPriceCategory().toString());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public MovieDTO getMovieById(Long id) {
		Movie m = movieService.getMovieById(id);
		MovieDTO dto = new MovieDTO(m.getId(), m.getTitle(), m.isRented(),
				m.getReleaseDate(), m.getPriceCategory().toString());
		return dto;
	}


	@Override
	/*
	 * Saves or updates a movie DTO. For update operations the title and the release
	 * date fields are ignored.
	 */
	public Long saveOrUpdateMovie(MovieDTO dto) {
		String pcString = dto.getPriceCategory();
		PriceCategory pc = null;
		List<PriceCategory> categories = movieService.getAllPriceCategories();
		for (PriceCategory priceCategory : categories) {
			if (pcString.equals(priceCategory.toString())) {
				pc = priceCategory;
			}
		}

		Movie movie = null;
		if (dto.getId() != null) {
			movie = movieService.getMovieById(dto.getId());
			movie.setPriceCategory(pc);
		} else {
			movie = new Movie(dto.getTitle(), dto.getReleaseDate(), pc);
		}

		movieService.saveOrUpdateMovie(movie);
		return movie.getId();
	}

	@Override
	public void deleteMovie(Long id) {
		Movie m = movieService.getMovieById(id);
		movieService.deleteMovie(m);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userService.getAllUsers();
		ArrayList<UserDTO> dtos = new ArrayList<UserDTO>();
		for (User u : users) {
			Long[] ids = new Long[u.getRentals().size()];
			for (int i = 0; i < ids.length; i++) {
				ids[i] = u.getRentals().get(i).getId();
			}
			UserDTO dto = new UserDTO(u.getId(), u.getLastName(), u.getFirstName(), u.getEmail(), ids);
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public UserDTO getUserById(Long id) {
		User u = userService.getUserById(id);
		Long[] ids = new Long[u.getRentals().size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = u.getRentals().get(i).getId();
		}
		UserDTO dto = new UserDTO(u.getId(), u.getLastName(), u.getFirstName(), u.getEmail(), ids);
		return dto;
	}



	@Override
	public Long saveOrUpdateUser(UserDTO dto){
		User user = null;
		if (dto.getId() != null) {
			user = userService.getUserById(dto.getId());
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
		} else {
			user = new User(dto.getLastName(), dto.getFirstName());
			user.setId(null);
		}
		user.setEmail(dto.getEmail());
		// TODO: check what to do with rentals
		userService.saveOrUpdateUser(user);
		return user.getId();
	}

	@Override
	public void deleteUser(Long id) {
		User u = userService.getUserById(id);
		userService.deleteUser(u);	
	}

	@Override
	public List<RentalDTO> getAllRentals() {
		List<Rental> rentals = rentalService.getAllRentals();
		ArrayList<RentalDTO> dtos = new ArrayList<RentalDTO>();
		Date now = Calendar.getInstance().getTime();
		for (Rental r : rentals) {
			RentalDTO dto = new RentalDTO(r.getId(), r.getRentalDate(), 
					r.getRentalDays(), r.calcRemainingDaysOfRental(now), 
					r.getUser().getId(), r.getMovie().getId(), r.getRentalFee());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public RentalDTO getRentalById(Long id) {
		Rental r = rentalService.getRentalById(id);
		Date now = Calendar.getInstance().getTime();
		RentalDTO dto = new RentalDTO(r.getId(), r.getRentalDate(), 
				r.getRentalDays(), r.calcRemainingDaysOfRental(now), 
				r.getUser().getId(), r.getMovie().getId(), r.getRentalFee());
		return dto;
	}


//	@Override
//	public Long saveOrUpdateRental(RentalDTO dto) throws RentalServiceException {
//		// FIXME update rental ist nicht implementiert, d.h. es k�nnen nur neue
//		//       rentals erzeugt werden.
//		User user = null;
//		if (dto.getUserId()!= null) {
//			user = userService.getUserById(dto.getUserId());
//		} else {
//			// FIXME was ist zu tun falls kein User referenziert wird?
//		}
//		Movie movie = movieService.getMovieById(dto.getMovieId());
//		Rental rental = new Rental(user, movie, dto.getRentalDays());
//		rentalService.saveOrUpdateRental(rental);
//		return rental.getId();
//	}
	
	@Override
	public void rentMovie(Long userId, Long movieId, int days) {
		Movie movie = movieService.getMovieById(movieId);
		User user = userService.getUserById(userId);
		userService.rentMovie(user, movie, days);
	}

	@Override
	public void deleteRental(Long id) {
		Rental rental = rentalService.getRentalById(id);
		rentalService.deleteRental(rental);
	}

}
