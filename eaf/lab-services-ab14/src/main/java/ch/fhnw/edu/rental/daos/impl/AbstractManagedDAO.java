package ch.fhnw.edu.rental.daos.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractManagedDAO<T> implements ManagedDAO<T> {
	@PersistenceContext
	protected EntityManager em;

	@Override
	public T manage(T instance) {
		return em.merge(instance);
	}

}
