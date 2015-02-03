package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

/**
 * 
 * On the association User -> Rentals the cascade type Cascade.ALL is defined,
 * i.e. the insert/update/delete operations should also delete the associated
 * tentals (could also be defined as a constraint on the data base).
 * 
 */ 
public class IbatisUserDAO  extends SqlMapClientDaoSupport implements UserDAO {

	private RentalDAO rentalDAO;
	
	public void setRentalDAO(RentalDAO rentalDAO) {
		this.rentalDAO = rentalDAO;
	}

	@Override
	public User getById(Long id) {
		return (User)getSqlMapClientTemplate().queryForObject("User.getById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		return (List<User>)getSqlMapClientTemplate().queryForList("User.getAll");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getByName(String name) {
		return (List<User>)getSqlMapClientTemplate().queryForList("User.getByName", name);
	}

	@Override
	public void saveOrUpdate(User user) {
		if(user.getId() == null){
			// insert
			getSqlMapClientTemplate().insert("User.insert", user);
		}
		else {
			// update
			getSqlMapClientTemplate().update("User.update", user);
		}
		for(Rental r : user.getRentals()) // TODO deleted entries???? Who is responsible to delete such rentals?
			rentalDAO.saveOrUpdate(r);

	}

	@Override
	public void delete(User user) {
		getSqlMapClientTemplate().delete("User.delete", user);
		user.setId(null);
		for(Rental r : user.getRentals())
			rentalDAO.delete(r);

	}


}
