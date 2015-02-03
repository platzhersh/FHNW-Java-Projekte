package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.model.Movie;

public class IbatisMovieDAO extends SqlMapClientDaoSupport implements MovieDAO {

	public Movie getById(Long id) {
		return (Movie)getSqlMapClientTemplate().queryForObject("Movie.getById", id);
	}

	@SuppressWarnings("unchecked")
	public List<Movie> getByTitle(String title) {
		return (List<Movie>)getSqlMapClientTemplate().queryForList("Movie.getByTitle", title);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movie> getAll() {
		return (List<Movie>)getSqlMapClientTemplate().queryForList("Movie.getAll");
	}

	@Override
	public void saveOrUpdate(Movie movie) {
		if(movie.getId() == null){
			// insert
			getSqlMapClientTemplate().insert("Movie.insert", movie);
			// TODO currently pricecategory is not stored
		}
		else {
			// update
			getSqlMapClientTemplate().update("Movie.update", movie);
		}
	}

	@Override
	public void delete(Movie movie) {
		getSqlMapClientTemplate().delete("Movie.delete", movie);
		movie.setId(null);
	}

}
