package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ch.fhnw.edu.rental.daos.UserDAO;
import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.model.User_;

public class JpaUserDAO extends AbstractManagedDAO<User> implements UserDAO {

	public JpaUserDAO() {
		super(User.class);
	}

	public List<User> getByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		
		// SELECT u FROM User u
		Root<User> u = cq.from(User.class);
		cq.select(u);
		
		// WHERE u.lastName = :name
		cq.where(cb.equal(u.get(User_.lastName), name));
		
		return em.createQuery(cq).getResultList();
	}

}
