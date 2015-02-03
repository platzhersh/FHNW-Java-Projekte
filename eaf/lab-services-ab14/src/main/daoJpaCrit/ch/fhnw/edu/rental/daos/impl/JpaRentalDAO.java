package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.Rental_;
import ch.fhnw.edu.rental.model.User;

public class JpaRentalDAO extends AbstractManagedDAO<Rental> implements RentalDAO {

	public JpaRentalDAO() {
		super(Rental.class);
	}

	@Override
	public List<Rental> getRentalsByUser(User user) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Rental> cq = cb.createQuery(Rental.class);
		
		// SELECT r FROM Rental r 
		Root<Rental> r = cq.from(Rental.class);
		cq.select(r);
		
		// WHERE r.user = :user
		cq.where(cb.equal(r.get(Rental_.user), user));
		
		return em.createQuery(cq).getResultList();
	}
}
