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
import ch.fhnw.edu.rental.service.util.ObjectUnifier;

public class JdbcTemplateMovieDAO extends JdbcDaoSupport implements MovieDAO {

	private PriceCategoryDAO priceCategoryDAO;
	
	public void setPriceCategoryDAO(PriceCategoryDAO priceCategoryDAO) {
		this.priceCategoryDAO = priceCategoryDAO;
	}

	private ObjectUnifier<Movie> cache = new ObjectUnifier<Movie>();
	
	public void clearCache(){
		cache.clear();
	}
	
	public Movie getById(Long id){
		Movie m = cache.getObject(id);
		if(m != null) return m;
		JdbcTemplate template = getJdbcTemplate();
		return template.queryForObject("select * from MOVIES where MOVIE_ID = ?",
			(ResultSet rs, int row) -> createMovie(rs),
			id
		);
	}

	public List<Movie> getAll() {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from MOVIES",
			(ResultSet rs, int row) -> createMovie(rs)
		);
	}
	
	public List<Movie> getByTitle(String name) {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from MOVIES where MOVIE_TITLE = ?",
			(ResultSet rs, int row) -> createMovie(rs),
			name
		);
	}

	public void saveOrUpdate(final Movie movie) {
		JdbcTemplate template = getJdbcTemplate();
		if(movie.getId() == null){	// insert
//			Variant 1)
//			long id = template.queryForObject("select max(MOVIE_ID) from MOVIES", Long.class) + 1;
//			movie.setId(id);
//			template.update("INSERT INTO MOVIES (MOVIE_ID, MOVIE_TITLE, MOVIE_RELEASEDATE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?,?,?,?,?)",
//				id, movie.getTitle(), movie.getReleaseDate(), movie.isRented(), movie.getPriceCategory().getId()
//			);
			
//			// Variant 4)
//			try {
//				insertMovie(movie);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			// Variant 5)
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


//			// Variant3)
//			template.update("INSERT INTO MOVIES (MOVIE_TITLE, MOVIE_RELEASEDATE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?,?,?,?)",
//				movie.getTitle(), movie.getReleaseDate(), movie.isRented(), movie.getPriceCategory().getId()
//			);
//			Long res = template.queryForObject("CALL IDENTITY()", Long.class);
//				// does not work, i.e. returns 0. different connection?
//				// http://forum.springsource.org/archive/index.php/t-22532.html
//			System.out.println(res);
//			movie.setId(res);

//			// Variant2)
//			// Throws a "this function is not suppored" exception
//			KeyHolder keyHolder = new GeneratedKeyHolder();
//			JdbcOperations ops = template.getJdbcOperations();
//			ops.update(
//				new PreparedStatementCreator(){
//					public PreparedStatement createPreparedStatement(
//							Connection conn) throws SQLException {
//						PreparedStatement ps =
//			                conn.prepareStatement(
//			                		"INSERT INTO MOVIES (MOVIE_TITLE, MOVIE_RELEASEDATE, MOVIE_RENTED, PRICECATEGORY_FK) VALUES (?,?,?,?)"
//			                		// , new String[] {"MOVIE_ID"}
//			                		, Statement.RETURN_GENERATED_KEYS
//			                		);
//			            ps.setString(1, movie.getTitle());
//			            ps.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
//			            ps.setBoolean(3, movie.isRented());
//			            ps.setLong(4, movie.getPriceCategory().getId());
//			            return ps;
//					}}
//					, keyHolder);

		}
		else { // update
			template.update("UPDATE MOVIES SET MOVIE_TITLE=?, MOVIE_RELEASEDATE=?, MOVIE_RENTED=?, PRICECATEGORY_FK=? where MOVIE_ID=?",
				movie.getTitle(), movie.getReleaseDate(), movie.isRented(), movie.getPriceCategory().getId(), movie.getId()
			);
		}
		// TODO saveOrUpdate is a problem is the object which is updated is already in the cache
		if(cache.getObject(movie.getId()) != null && cache.getObject(movie.getId()) != movie){
			throw new RuntimeException("objects need to be unified");
		}
		cache.putObject(movie.getId(), movie);
	}
	
	public void insertMovie(Movie movie) throws SQLException {
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
		movie.setId(id);
	}
	
	public void delete(Movie movie) {
		JdbcTemplate template = getJdbcTemplate();
		template.update("delete from MOVIES where MOVIE_ID = ?", movie.getId());
		cache.remove(movie.getId());
		movie.setId(null);
	}
	

	private Movie createMovie(ResultSet rs) throws SQLException {
		Long id = rs.getLong("MOVIE_ID");
		Movie m = cache.getObject(id);
		if (m == null) {
			long priceCategory = rs.getLong("PRICECATEGORY_FK");
			m = new Movie(rs.getString("MOVIE_TITLE"), 
					rs.getDate("MOVIE_RELEASEDATE"), 
					priceCategoryDAO.getById(priceCategory));
			m.setId(rs.getLong("MOVIE_ID"));
			m.setRented(rs.getBoolean("MOVIE_RENTED"));
			cache.putObject(id, m);
		}
		return m;
	}


}
