package ch.fhnw.edu.rental.daos.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public class MemoryRentalDAO implements RentalDAO {

	private Map<Long, Rental> data = new HashMap<Long, Rental>();
	private long nextId = 1;

	private UserDAO userDAO;
	private MovieDAO movieDAO;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setMovieDAO(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	
	@SuppressWarnings("unused")
	private void initData () {
		data.clear();
		nextId = 1;
		Calendar cal = new GregorianCalendar(2011, GregorianCalendar.OCTOBER, 5);
		Rental r;
		r = new Rental(userDAO.getById(1L), movieDAO.getById(1L), 7);
		r.setRentalDate(cal.getTime());
		saveOrUpdate(r);
		r = new Rental(userDAO.getById(1L), movieDAO.getById(2L), 6);
		r.setRentalDate(cal.getTime());
		saveOrUpdate(r);
		r = new Rental(userDAO.getById(3L), movieDAO.getById(3L), 6);
		r.setRentalDate(cal.getTime());
		saveOrUpdate(r);
	}

	@Override
	public Rental getById(Long id) {
		return data.get(id);
	}

	@Override
	public List<Rental> getAll() {
		return new ArrayList<Rental>(data.values());
	}

	@Override
	public List<Rental> getRentalsByUser(User user) {
		List<Rental> res = new ArrayList<Rental>();
		for(Rental r : data.values()){
			if(r.getUser().equals(user)) res.add(r);
		}
		return res;
	}

	@Override
	public void saveOrUpdate(Rental rental) {
		if (rental.getId() == null) rental.setId(nextId++);
		data.put(rental.getId(), rental);
	}

	@Override
	public void delete(Rental rental) {
		data.remove(rental.getId());
		rental.setId(null);
	}
}
