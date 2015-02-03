package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.User;

@Component
public class JpaUserDAO extends AbstractManagedDAO<User> implements UserDAO {

	public User getById(Long id) {
		return em.find(User.class, id);
	}

	public void delete(User user) {
		em.remove(user);
	}

	public List<User> getAll() {
//		List<User> users = em.createQuery("from User").getResultList();
//		return (users.isEmpty()) ? Collections.EMPTY_LIST : users;
		TypedQuery<User> q = em.createNamedQuery("user.all", User.class);
		return q.getResultList();
	}

	public void saveOrUpdate(User user) {
		if (user.getId() != null) {
			em.merge(user);
		} else {
			em.persist(user);
		}
	}

	public List<User> getByName(String name) {
		TypedQuery<User> q = em.createNamedQuery("user.byName2", User.class); // FIXME LAZY 2
		q.setParameter("name", name);
		return q.getResultList();
	}

}
