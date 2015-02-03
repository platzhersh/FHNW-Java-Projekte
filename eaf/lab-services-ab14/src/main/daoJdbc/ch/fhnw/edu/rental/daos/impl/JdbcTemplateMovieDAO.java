package ch.fhnw.edu.rental.daos.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.model.Movie;

public class JdbcTemplateMovieDAO extends JdbcDaoSupport implements MovieDAO {

	private PriceCategoryDAO priceCategoryDAO;
	
	public void setPriceCategoryDAO(PriceCategoryDAO priceCategoryDAO) {
		this.priceCategoryDAO = priceCategoryDAO;
	}

	@Override
	public Movie getById(Long id){
		JdbcTemplate template = getJdbcTemplate();
		return template.queryForObject("select * from MOVIES where MOVIE_ID = ?",
			(ResultSet rs, int row) -> createMovie(rs),
			id
		);
	}

	@Override
	public List<Movie> getAll() {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from MOVIES",
				(ResultSet rs, int row) -> createMovie(rs)
		);
	}
	
	@Override
	public List<Movie> getByTitle(String name) {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from MOVIES where MOVIE_TITLE = ?",
			(ResultSet rs, int row) -> createMovie(rs),
			name
		);
	}

	@Override
	public void saveOrUpdate(final Movie movie) {
		JdbcTemplate template = getJdbcTemplate();
		if(movie.getId() == null){	// insert
			try {
				insertMovie0(template, movie);	// executeAndReturnKey
//				insertMovie1(template, movie);	// PK is determined with max(MOVIE_ID)
//				insertMovie2(template, movie);	// does not compile [10/2013]
//				insertMovie3(template, movie);	// call identity(), does not work
//				insertMovie4(movie);	// call identity (same connection), aber falsche TX
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		else { // update
			template.update("UPDATE MOVIES SET MOVIE_TITLE=?, MOVIE_RELEASEDATE=?, MOVIE_RENTED=?, PRICECATEGORY_FK=? where MOVIE_ID=?",
				movie.getTitle(), movie.getReleaseDate(), movie.isRented(), movie.getPriceCategory().getId(), movie.getId()
			);
		}
	}
	
	private void insertMovie0(JdbcTemplate template, Movie movie) throws SQLException {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource())
		   .withTableName("MOVIES")
		   .usingGeneratedKeyColumns("MOVIE_ID");

		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("MOVIE_TITLE", movie.getTitle());
		parameters.put("MOVIE_RELEASEDATE", movie.getReleaseDate());
		parameters.put("MOVIE_RENTED", movie.isRented());
		parameters.put("PRICECATEGORY_FK", 
		                       movie.getPriceCategory().getId());

		Number id = insert.executeAndReturnKey(parameters);
		movie.setId((Long)id);
	}
	
	@SuppressWarnings("unused")
	private void insertMovie1(JdbcTemplate template, Movie movie) throws SQLException {
		long id = template.queryForObject("select max(MOVIE_ID) from MOVIES", Long.class) + 1;
		movie.setId(id);
		template.update("INSERT INTO MOVIES (MOVIE_ID, MOVIE_TITLE, MOVIE_RELEASEDATE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?,?,?,?,?)",
			id, movie.getTitle(), movie.getReleaseDate(), movie.isRented(), movie.getPriceCategory().getId()
		);
	}

//	private void insertMovie2(JdbcTemplate template, final Movie movie) throws SQLException {
//		// Throws a "this function is not supported" exception
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		JdbcOperations ops = template.getJdbcOperations();
//		ops.update(
//			new PreparedStatementCreator(){
//				public PreparedStatement createPreparedStatement(
//						Connection conn) throws SQLException {
//					PreparedStatement ps =
//		                conn.prepareStatement(
//		                		"INSERT INTO MOVIES (MOVIE_TITLE, MOVIE_RELEASEDATE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?,?,?,?)"
//		                		// , new String[] {"MOVIE_ID"}
//		                		, Statement.RETURN_GENERATED_KEYS
//		                		);
//		            ps.setString(1, movie.getTitle());
//		            ps.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
//		            ps.setBoolean(3, movie.isRented());
//		            ps.setLong(4, movie.getPriceCategory().getId());
//		            return ps;
//				}}
//				, keyHolder);
//	}

	@SuppressWarnings("unused")
	private void insertMovie3(JdbcTemplate template, Movie movie) throws SQLException {
		template.update(
				"INSERT INTO MOVIES (MOVIE_TITLE, MOVIE_RELEASEDATE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?,?,?,?)",
				movie.getTitle(), movie.getReleaseDate(), movie.isRented(),
				movie.getPriceCategory().getId());
		Long res = template.queryForObject("CALL IDENTITY()", Long.class);
		// does not work, i.e. returns 0. different connection?
		// http://forum.springsource.org/archive/index.php/t-22532.html
		System.out.println(">> id = " + res); // gibt hier null aus. Wenn ich dann alle Movies lade, dann ist der PK korrekt
		movie.setId(res);
	}

	@SuppressWarnings("unused")
	private void insertMovie4(Movie movie) throws SQLException {
		// Problem: Partizipiert nicht richtig mit den Transaktionen
		Connection conn = getDataSource().getConnection();

		PreparedStatement pst;
		pst = conn.prepareStatement("INSERT INTO MOVIES (MOVIE_TITLE, MOVIE_RELEASEDATE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?,?,?,?)");
		pst.setString(1, movie.getTitle());
		pst.setDate(2, new Date(movie.getReleaseDate().getTime()));
		pst.setBoolean(3, movie.isRented());
		pst.setLong(4, movie.getPriceCategory().getId());
		pst.execute();

		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("call identity()");
		rs.next();
		Long id = rs.getLong(1);
		System.out.println(">> id = " + id);
		movie.setId(id);
	}
	
	@Override
	public void delete(Movie movie) {
		JdbcTemplate template = getJdbcTemplate();
		template.update("delete from MOVIES where MOVIE_ID = ?", movie.getId());
		movie.setId(null);
	}
	

 	// variant without caching
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

//	private Movie createMovie(ResultSet rs) throws SQLException {
//		Long id = rs.getLong("MOVIE_ID");
//		Movie m = cache.getObject(id);
//		if (m == null) {
//			long priceCategory = rs.getLong("PRICECATEGORY_FK");
//			m = new Movie(rs.getString("MOVIE_TITLE"), rs
//					.getDate("MOVIE_RELEASEDATE"), priceCategoryDAO
//					.getById(priceCategory));
//			m.setId(rs.getLong("MOVIE_ID"));
//			m.setRented(rs.getBoolean("MOVIE_RENTED"));
//
//			cache.putObject(id, m);
//		}
//		return m;
//	}


}
