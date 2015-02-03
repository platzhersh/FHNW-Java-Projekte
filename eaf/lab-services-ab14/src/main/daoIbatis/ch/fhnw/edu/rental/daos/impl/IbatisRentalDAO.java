package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public class IbatisRentalDAO extends SqlMapClientDaoSupport implements RentalDAO {

	private MovieDAO movieDAO;
	public void setMovieDAO(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}
	
//	private UserDAO userDAO;
//	public void setUserDAO(UserDAO userDAO) {
//		this.userDAO = userDAO;
//	}


	@Override
	public Rental getById(Long id) {
		return (Rental)getSqlMapClientTemplate().queryForObject("Rental.getById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rental> getAll() {
		return (List<Rental>)getSqlMapClientTemplate().queryForList("Rental.getAll");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rental> getRentalsByUser(User user) {
		return (List<Rental>)getSqlMapClientTemplate().queryForList("Rental.getByUserId", user.getId());
	}

	@Override
	public void saveOrUpdate(Rental rental) {
		if(rental.getId() == null){
			// insert
			getSqlMapClientTemplate().insert("Rental.insert", rental);
		}
		else {
			// update
			getSqlMapClientTemplate().update("Rental.update", rental);
		}
		movieDAO.saveOrUpdate(rental.getMovie());
		//userDAO.saveOrUpdate(rental.getUser());	// enters in an infinite loop if saveOrUpdate is called from User
	}

	@Override
	public void delete(Rental rental) {
		getSqlMapClientTemplate().delete("Rental.delete", rental);
		rental.setId(null);
	}

}

