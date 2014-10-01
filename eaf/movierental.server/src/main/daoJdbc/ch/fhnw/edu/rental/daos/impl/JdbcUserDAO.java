package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.sql.DataSource;

import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.User;

public class JdbcUserDAO implements UserDAO {

	private DataSource ds;
	private RentalDAO rentalDAO;

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}

	public void setRentalDAO(RentalDAO rentalDAO) {
		this.rentalDAO = rentalDAO;
	}

	@Override
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
	}

}
