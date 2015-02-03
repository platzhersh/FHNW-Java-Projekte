package ch.fhnw.edu.rental.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.service.util.ObjectUnifier;

public class JdbcTemplateRentalDAO extends JdbcDaoSupport implements RentalDAO {

	private MovieDAO movieDAO;
	private UserDAO  userDAO;
	
	public void setMovieDAO(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	private ObjectUnifier<Rental> cache = new ObjectUnifier<Rental>();
	
	public void clearCache(){
		cache.clear();
	}

	
	public Rental getById(Long id){
		Rental r = cache.getObject(id);
		if(r != null) return r;
		JdbcTemplate template = getJdbcTemplate();
		return template.queryForObject("select * from RENTALS where RENTAL_ID = ?",
			new RowMapper<Rental>(){
				public Rental mapRow(ResultSet rs, int row) throws SQLException {
					return createRental(rs);
				}
			},
			id
		);
	}

	public List<Rental> getAll() {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from RENTALS",
			new RowMapper<Rental>() {
				public Rental mapRow(ResultSet rs, int row) throws SQLException {
					return createRental(rs);
				}
			}
		);
	}
	
	public List<Rental> getRentalsByUser(final User user) {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from RENTALS where USER_ID = ?",
			new RowMapper<Rental>() {
				public Rental mapRow(ResultSet rs, int row) throws SQLException {
					return createRental(rs);
				}
			},
			user.getId()
		);
	}

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
		// TODO saveOrUpdate is a problem is the object which is updated is already in the cache
		if(cache.getObject(rental.getId()) != null && cache.getObject(rental.getId()) != rental){
			throw new RuntimeException("objects need to be unified");
		}
		cache.putObject(rental.getId(), rental);
	}
	
	public void delete(Rental movie) {
		JdbcTemplate template = getJdbcTemplate();
		template.update("delete from RENTALS where RENTAL_ID = ?", movie.getId());
		cache.remove(movie.getId());
		movie.setId(null);
	}
	
	private Rental createRental(ResultSet rs) throws SQLException {
		Long id = rs.getLong("RENTAL_ID");
		Rental r = cache.getObject(id);
		if (r == null) {
			r = new Rental();
			r.setId(id);
			cache.putObject(id, r);
			User u = userDAO.getById(rs.getLong("USER_ID"));
			Movie m = movieDAO.getById(rs.getLong("MOVIE_ID"));
			if (!m.isRented())
				throw new RuntimeException(
						"movie must be rented if read from DB");
			r.setMovie(m);
			r.setUser(u);
			r.setRentalDays(rs.getInt("RENTAL_RENTALDAYS"));
		}
		return r;
	}

}
