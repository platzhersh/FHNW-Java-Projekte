package ch.fhnw.edu.rental.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public class JdbcTemplateRentalDAO extends JdbcDaoSupport implements RentalDAO {

	private MovieDAO movieDAO;
	private UserDAO  userDAO;
	
	public void setMovieDAO(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public Rental getById(Long id){
		JdbcTemplate template = getJdbcTemplate();
		return template.queryForObject("select * from RENTALS where RENTAL_ID = ?",
			(ResultSet rs, int row) -> createRental(rs),
			id
		);
	}

	@Override
	public List<Rental> getAll() {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from RENTALS",
			(ResultSet rs, int row) -> createRental(rs)
		);
	}
	
	@Override
	public List<Rental> getRentalsByUser(final User user) {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from RENTALS where USER_ID = ?",
			(ResultSet rs, int row) -> 
//					createRental(rs), // ends in a StackOverflowError
					createRental(rs, user),
			user.getId()
		);
	}

	@Override
	public void saveOrUpdate(Rental rental) {
		JdbcTemplate template = getJdbcTemplate();
		if(rental.getId() == null){	// insert
			long id = template.queryForObject("select max(RENTAL_ID) from RENTALS", Long.class) + 1;
			rental.setId(id);
			template.update("INSERT INTO RENTALS (RENTAL_ID, RENTAL_RENTALDATE, RENTAL_RENTALDAYS, USER_ID, MOVIE_ID) VALUES (?,?,?,?,?)",
				id, rental.getRentalDate(), rental.getRentalDays(), rental.getUser().getId(), rental.getMovie().getId()
			);
		}
		else { // update
			template.update("UPDATE RENTALS SET RENTAL_RENTALDATE=?, RENTAL_RENTALDAYS=?, USER_ID=?, MOVIE_ID=? where RENTAL_ID=?",
				rental.getRentalDate(), rental.getRentalDays(), rental.getUser().getId(), rental.getMovie().getId(), rental.getId()
			);
		}
	}
	
	@Override
	public void delete(Rental movie) {
		JdbcTemplate template = getJdbcTemplate();
		template.update("delete from RENTALS where RENTAL_ID = ?", movie.getId());
		movie.setId(null);
	}
	
	private Rental createRental(ResultSet rs) throws SQLException {
		Long id = rs.getLong("RENTAL_ID");
		User user = userDAO.getById(rs.getLong("USER_ID"));
		
//		return createRental(rs, user);
		for(Rental r : user.getRentals()){
			if(r.getId().equals(id)) return r;
		}
		throw new RuntimeException("inconsistent user");
	}
	
	private Rental createRental(ResultSet rs, User u) throws SQLException {
		Movie m = movieDAO.getById(rs.getLong("MOVIE_ID"));
		if(!m.isRented()) throw new RuntimeException("movie must be rented if read from DB");
		// FIXME why was this check introduced? => otherwise DB is inconsistent because we read a rental
		m.setRented(false); // necessary, otherwiese Rental constructor complains
		Rental r = new Rental(u, m, rs.getInt("RENTAL_RENTALDAYS"));
		m.setRented(true); // not necessary, was performed in Rental constructor
		r.setId(rs.getLong("RENTAL_ID"));
		return r;
	}

}
