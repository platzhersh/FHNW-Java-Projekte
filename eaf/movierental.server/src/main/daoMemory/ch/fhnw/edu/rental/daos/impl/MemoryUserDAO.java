package ch.fhnw.edu.rental.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

public class MemoryUserDAO implements UserDAO {
	
	private Map<Long,User> data = new HashMap<Long,User>();
	private long nextId = 1;
	
	private RentalDAO rentalDAO;
	
	public void setRentalDAO(RentalDAO rentalDAO) {
		this.rentalDAO = rentalDAO;
	}

	
	@SuppressWarnings("unused")
	private void initData () {
		data.clear();
		nextId = 1;
		saveOrUpdate(new User("Keller", "Marc"));
		saveOrUpdate(new User("Knecht", "Werner"));
		saveOrUpdate(new User("Meyer", "Barbara"));
		saveOrUpdate(new User("Kummer", "Adolf"));
		
		getById(1L).setEmail("marc.keller@gmail.com");
		getById(2L).setEmail("werner.knecht@gmail.com");
		getById(3L).setEmail("barbara.meyer@gmail.com");
		getById(4L).setEmail("adolf.kummer@gmail.com");
	}

	@Override
	public void delete(User user) {
		for(Rental r : user.getRentals()){
			rentalDAO.delete(r);
		}
		data.remove(user.getId());
		user.setId(null);
	}

	@Override
	public List<User> getAll() {
		return new ArrayList<User>(data.values());
	}

	@Override
	public User getById(Long id) {
		return data.get(id);
	}

	@Override
	public List<User> getByName(String name) {
		List<User> result = new ArrayList<User>();
		for(User u : data.values()){
			if(u.getLastName().equals(name)) result.add(u);
		}
		return result;
	}

	@Override
	public void saveOrUpdate(User user) {
		if(user.getId()==null) user.setId(nextId++);
		data.put(user.getId(), user);
	}

}
