package ch.fhnw.edu.rental.facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import ch.fhnw.edu.rental.dto.MovieDTO;
import ch.fhnw.edu.rental.dto.RentalDTO;
import ch.fhnw.edu.rental.dto.UserDTO;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.services.MovieService;
import ch.fhnw.edu.rental.services.RentalService;
import ch.fhnw.edu.rental.services.RmiMovieService;
import ch.fhnw.edu.rental.services.RmiRentalService;
import ch.fhnw.edu.rental.services.RmiUserService;
import ch.fhnw.edu.rental.services.UserService;

public class RmiFacadeDozer implements RmiMovieService, RmiUserService, RmiRentalService {
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RentalService rentalService;

	@Autowired
	private Mapper mapper;


	@Override
	public List<MovieDTO> getAllMovies() {
		ArrayList<MovieDTO> dtos = new ArrayList<MovieDTO>();
		for (Movie m : movieService.getAllMovies()) {
			dtos.add(mapper.map(m, MovieDTO.class));
		}
		return dtos;
	}

	@Override
	public MovieDTO getMovieById(Long id) {
		return mapper.map(movieService.getMovieById(id), MovieDTO.class);
	}


	@Override
	/*
	 * Saves or updates a movie DTO. For update operations the title and the release
	 * date fields are ignored.
	 */
	public Long saveOrUpdateMovie(MovieDTO dto) {
		Movie movie = mapper.map(dto, Movie.class);
		movieService.saveOrUpdateMovie(movie);
		// FIXME kommt hier bei merge das richtige Objekt zurueck?
		return movie.getId();
	}

	@Override
	public void deleteMovie(Long id) {
		Movie m = movieService.getMovieById(id);
		movieService.deleteMovie(m);
	}
	
	@Override
	public List<UserDTO> getAllUsers() {
		ArrayList<UserDTO> dtos = new ArrayList<UserDTO>();
		for (User u : userService.getAllUsers()) {
			dtos.add((UserDTO)mapper.map(u, UserDTO.class));
		}
		return dtos;
	}
	
	public UserDTO getUserById(Long id) {
		return (UserDTO)mapper.map(userService.getUserById(id), UserDTO.class);
	}


	@Override
	public Long saveOrUpdateUser(UserDTO dto) {
		User user = mapper.map(dto, User.class);
//		if (dto.getId() != null) {
//			user = userService.getUserById(dto.getId());
//			user.setFirstName(dto.getFirstName());
//			user.setLastName(dto.getLastName());
//		} else {
//			user = new User(dto.getLastName(), dto.getFirstName());
//			user.setId(null);
//		}
//		user.setEmail(dto.getEmail());
//		// TODO: check what to do with rentals
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
//			RentalDTO dto = new RentalDTO(r.getId(), r.getRentalDate(), 
//					r.getRentalDays(), r.calcRemainingDaysOfRental(now), 
//					r.getUser().getId(), r.getMovie().getId(), r.getRentalFee());
			RentalDTO dto = mapper.map(r, RentalDTO.class);
			dto.setRemainingDays(r.calcRemainingDaysOfRental(now));
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
//	public RentalDTO getRentalById(Long id) throws RentalServiceException {
//		Rental r = rentalService.getRentalById(id);
//		Date now = Calendar.getInstance().getTime();
//		RentalDTO dto = new RentalDTO(r.getId(), r.getRentalDate(), 
//				r.getRentalDays(), r.calcRemainingDaysOfRental(now), 
//				r.getUser().getId(), r.getMovie().getId(), r.getRentalFee());
//		return dto;
//	}
	public RentalDTO getRentalById(Long id) {
		Rental r = rentalService.getRentalById(id);
		RentalDTO dto = mapper.map(r, RentalDTO.class);
		dto.setRemainingDays(r.calcRemainingDaysOfRental(Calendar.getInstance().getTime()));
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
