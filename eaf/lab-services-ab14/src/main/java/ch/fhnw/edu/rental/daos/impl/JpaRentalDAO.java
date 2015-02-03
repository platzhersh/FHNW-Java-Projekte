package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

@Component
public class JpaRentalDAO extends AbstractManagedDAO<Rental> implements RentalDAO {

	@Override
	public Rental getById(Long id) {
		return em.find(Rental.class, id);
	}

	@Override
	public void delete(Rental rental) {
		em.remove(rental);
	}

	@Override
	public List<Rental> getAll() {
		TypedQuery<Rental> q = em.createNamedQuery("rental.all", Rental.class);
		return q.getResultList();
	}

	public void saveOrUpdate(Rental rental) {
		if (rental.getId() != null) {
			em.merge(rental);
		} else {
			em.persist(rental);
		}
	}

	@Override
	public List<Rental> getRentalsByUser(User user) {
		TypedQuery<Rental> q = em.createNamedQuery("rental.byUser", Rental.class);
		q.setParameter("user", user);
		return q.getResultList();
	}
}
