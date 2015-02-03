package ch.fhnw.edu.rental.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public class JdbcTemplateUserDAO extends JdbcDaoSupport implements UserDAO {
	
	private RentalDAO rentalDAO;
	
	public void setRentalDAO(RentalDAO rentalDAO) {
		this.rentalDAO = rentalDAO;
	}
	
	@Override
	public User getById(Long id){
		JdbcTemplate template = getJdbcTemplate();
		return template.queryForObject("select * from USERS where USER_ID = ?",
			(ResultSet rs, int row) -> createUser(rs),
			id
		);
	}

	@Override
	public List<User> getAll() {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from USERS",
			(ResultSet rs, int row) -> createUser(rs)
		);
	}
	
	@Override
	public List<User> getByName(String name) {
		JdbcTemplate template = getJdbcTemplate();
		return template.query("select * from USERS where USER_NAME = ?",
			(ResultSet rs, int row) -> createUser(rs),
			name
		);
	}
	
	@Override
	public void saveOrUpdate(User user) {
		JdbcTemplate template = getJdbcTemplate();
		if(user.getId() == null){	// insert
			long id = template.queryForObject("select max(USER_ID) from USERS", Long.class) + 1;
			user.setId(id);
			template.update("INSERT INTO USERS (USER_ID, USER_NAME, USER_FIRSTNAME, USER_EMAIL) VALUES (?,?,?,?)",
				id, user.getLastName(), user.getFirstName(), user.getEmail()
			);
		}
		else { // update
			template.update("UPDATE USERS SET USER_NAME=?, USER_FIRSTNAME=?, USER_EMAIL=? where USER_ID=?",
				user.getLastName(), user.getFirstName(), user.getEmail(), user.getId()
			);
		}
		for(Rental r : user.getRentals()) {
			rentalDAO.saveOrUpdate(r);
		}
	}

	@Override
	public void delete(User user) {
		JdbcTemplate template = getJdbcTemplate();
		for(Rental r : user.getRentals()) {
			rentalDAO.delete(r);
		}
		template.update("delete from USERS where USER_ID = ?", user.getId());
		user.setId(null);
	}
	
	private User createUser(ResultSet rs) throws SQLException {
		User u = new User(rs.getString("USER_NAME"), rs.getString("USER_FIRSTNAME"));
		u.setId(rs.getLong("USER_ID"));
		u.setEmail(rs.getString("USER_EMAIL"));
		u.setRentals(rentalDAO.getRentalsByUser(u));
		return u;
	}
}
