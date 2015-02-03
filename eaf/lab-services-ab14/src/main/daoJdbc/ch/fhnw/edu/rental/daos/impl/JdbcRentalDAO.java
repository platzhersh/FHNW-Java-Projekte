package ch.fhnw.edu.rental.daos.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public class JdbcRentalDAO implements RentalDAO {

	private DataSource ds;
	private MovieDAO movieDAO;
	private UserDAO userDAO;

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}

	public void setMovieDAO(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public Rental getById(Long id) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("select * from RENTALS where RENTAL_ID = "
							+ id);
			if (rs.next()) {
				return createRental(rs);
			} else
				return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public List<Rental> getAll() {
		List<Rental> rentals = new LinkedList<Rental>();
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from RENTALS");
			while (rs.next()) {
				rentals.add(createRental(rs));
			}
			return rentals;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public List<Rental> getRentalsByUser(User user) {
		List<Rental> rentals = new LinkedList<Rental>();
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("select * from RENTALS where USER_ID = "
							+ user.getId());
			while (rs.next()) {
				rentals.add(createRental(rs, user));
			}
			return rentals;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public void saveOrUpdate(Rental rental) {
		Connection conn = null;
		try {
			conn = ds.getConnection();

			PreparedStatement pst;
			if (rental.getId() == null) { // insert
				long id = 0;
				Statement st = conn.createStatement();
				ResultSet rs = st
						.executeQuery("select max(RENTAL_ID) from RENTALS");
				if (rs.next()) {
					id = rs.getLong(1) + 1;
				}
				rental.setId(id);

				pst = conn
						.prepareStatement("INSERT INTO RENTALS (RENTAL_ID, RENTAL_RENTALDATE, RENTAL_RENTALDAYS, USER_ID, MOVIE_ID) VALUES (?,?,?,?,?)");
				pst.setLong(1, rental.getId());
				pst.setDate(2, new Date(rental.getRentalDate().getTime()));
				pst.setInt(3, rental.getRentalDays());
				pst.setLong(4, rental.getUser().getId());
				pst.setLong(5, rental.getMovie().getId());
				pst.execute();
			} else { // update
				pst = conn
						.prepareStatement("UPDATE RENTALS SET RENTAL_RENTALDATE=?, RENTAL_RENTALDAYS=?, USER_ID=?, MOVIE_ID=? where RENTAL_ID=?");
				pst.setLong(5, rental.getId());
				pst.setDate(1, new Date(rental.getRentalDate().getTime()));
				pst.setInt(2, rental.getRentalDays());
				pst.setLong(3, rental.getUser().getId());
				pst.setLong(4, rental.getMovie().getId());
				pst.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

	@Override
	public void delete(Rental rental) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			st.execute("delete from RENTALS where RENTAL_ID = "
					+ rental.getId());
			rental.setId(null);
			rental.getUser().getRentals().remove(rental);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private Rental createRental(ResultSet rs) throws SQLException {
		User user = userDAO.getById(rs.getLong("USER_ID"));
		return createRental(rs, user);
	}

	private Rental createRental(ResultSet rs, User u) throws SQLException {
		Movie m = movieDAO.getById(rs.getLong("MOVIE_ID"));
		if (!m.isRented())
			throw new RuntimeException("movie must be rented if read from DB");
		m.setRented(false);
		Rental r = new Rental(u, m, rs.getInt("RENTAL_RENTALDAYS"));
		m.setRented(true);
		r.setId(rs.getLong("RENTAL_ID"));
		return r;
	}

}
