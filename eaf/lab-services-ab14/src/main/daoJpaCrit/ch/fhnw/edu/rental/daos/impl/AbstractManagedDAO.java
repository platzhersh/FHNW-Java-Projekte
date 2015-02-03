package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractManagedDAO<T> implements ManagedDAO<T> {
	@PersistenceContext
	protected EntityManager em;
	
	final private Class<T> type;
	
	protected AbstractManagedDAO(Class<T> type) {
		this.type = type;
	}

	@Override
	public T manage(T instance) {
		return em.merge(instance);
	}

	public T getById(Long id) {
		return em.find(type, id);
	}
	
	public List<T> getAll() {
		CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(type);
		TypedQuery<T> q = em.createQuery(cq.select(cq.from(type)));
		return q.getResultList();
	}

	public void delete(T entity) {
		em.remove(entity);
	}
	
	public void saveOrUpdate(T entity) {
		PersistenceUnitUtil puu = em.getEntityManagerFactory().getPersistenceUnitUtil();
		if (puu.getIdentifier(entity) != null) {
			em.merge(entity);
		} else {
			em.persist(entity);
		}
	}

}
