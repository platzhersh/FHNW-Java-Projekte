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
import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.model.Movie;

public class JdbcMovieDAO implements MovieDAO {

	private DataSource ds;
	private PriceCategoryDAO priceCategoryDAO;
	
	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}

	public void setPriceCategoryDAO(PriceCategoryDAO priceCategoryDAO) {
		this.priceCategoryDAO = priceCategoryDAO;
	}

	@Override
	public Movie getById(Long id){
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from MOVIES where MOVIE_ID = "+id);
			if(rs.next()){
				return createMovie(rs);
			}
			else return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public List<Movie> getByTitle(String name) {
		List<Movie> movies = new LinkedList<Movie>();
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from MOVIES where MOVIE_TITLE = '" + name + "'");
			while(rs.next()){
				movies.add(createMovie(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return movies;
	}

	@Override
	public List<Movie> getAll() {
		List<Movie> movies = new LinkedList<Movie>();
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from MOVIES");
			while(rs.next()){
				movies.add(createMovie(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return movies;
	}
	
	@Override
	public void saveOrUpdate(Movie movie) {
		Connection conn = null;
		try {
			conn = ds.getConnection();

			PreparedStatement pst;
			if(movie.getId() == null){	// insert
				long id = 0;
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select max(MOVIE_ID) from MOVIES");
				if(rs.next()){id = rs.getLong(1)+1;}
				movie.setId(id);
				
				pst = conn.prepareStatement("INSERT INTO MOVIES (MOVIE_ID, MOVIE_TITLE, MOVIE_RELEASEDATE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?,?,?,?,?)");
				pst.setLong(1, id);
				pst.setString(2, movie.getTitle());
				pst.setDate(3, new Date(movie.getReleaseDate().getTime()));
				pst.setBoolean(4, movie.isRented());
				pst.setLong(5, movie.getPriceCategory().getId());
				pst.execute();
			}
			else {	// update
				pst = conn.prepareStatement("UPDATE MOVIES SET MOVIE_TITLE=?, MOVIE_RELEASEDATE=?, MOVIE_RENTED=?, PRICECATEGORY_FK=? where MOVIE_ID=?");
				pst.setLong(5, movie.getId());
				pst.setString(1, movie.getTitle());
				pst.setDate(2, new Date(movie.getReleaseDate().getTime()));
				pst.setBoolean(3, movie.isRented());
				pst.setLong(4, movie.getPriceCategory().getId());
				pst.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	@Override
	public void delete(Movie movie) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			st.execute("delete from MOVIES where MOVIE_ID = " + movie.getId());
			movie.setId(null);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	private Movie createMovie(ResultSet rs) throws SQLException {
		long priceCategory = rs.getLong("PRICECATEGORY_FK");
		Movie m = new Movie(
				rs.getString("MOVIE_TITLE"), 
				rs.getDate("MOVIE_RELEASEDATE"),
				priceCategoryDAO.getById(priceCategory));
		m.setId(rs.getLong("MOVIE_ID"));
		m.setRented(rs.getBoolean("MOVIE_RENTED"));
		return m;
	}

}
