package ch.fhnw.edu.rental.daos.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.model.Movie;

public class MemoryMovieDAO implements MovieDAO {
	
	private Map<Long, Movie> data = new HashMap<Long, Movie>();
	private long nextId = 1;

	private PriceCategoryDAO priceCategoryDAO;

	public void setPriceCategoryDAO(PriceCategoryDAO priceCategoryDAO) {
		this.priceCategoryDAO = priceCategoryDAO;
	}

	
	@SuppressWarnings("unused")
	private void initData () {
		data.clear();
		nextId = 1;
		
		Calendar c = Calendar.getInstance();
		c.clear();
		
		c.set(2001, Calendar.DECEMBER, 19);
		saveOrUpdate(new Movie("Lord of the Rings", c.getTime(), priceCategoryDAO.getById(1L)));

		c.set(1999, Calendar.MARCH, 31);
		saveOrUpdate(new Movie("Matrix", c.getTime(), priceCategoryDAO.getById(3L)));

		c.set(2003, Calendar.MAY, 15);
		saveOrUpdate(new Movie("Matrix Reloaded", c.getTime(), priceCategoryDAO.getById(3L)));

		c.set(2003, Calendar.AUGUST, 26);
		saveOrUpdate(new Movie("Eragon", c.getTime(), priceCategoryDAO.getById(2L)));

		c.set(1998, Calendar.SEPTEMBER, 25);
		saveOrUpdate(new Movie("Batman", c.getTime(), priceCategoryDAO.getById(1L)));
	}
	
	@Override
	public void delete(Movie movie) {
		data.remove(movie.getId());
		movie.setId(null);
	}

	@Override
	public List<Movie> getAll() {
		return new ArrayList<Movie>(data.values());
	}

	@Override
	public Movie getById(Long id) {
		return data.get(id);
	}

	@Override
	public List<Movie> getByTitle(String name) {
		List<Movie> result = new ArrayList<Movie>();
		for(Movie m : data.values()){
			if(m.getTitle().equals(name)) result.add(m);
		}
		return result;
	}

	@Override
	public void saveOrUpdate(Movie movie) {
		if(movie.getId() == null){
			movie.setId(nextId++);
			data.put(movie.getId(), movie);
		}
		else {
			// Alternative: copy the new values over the old ones.
			data.put(movie.getId(), movie);
		}
	}

}
