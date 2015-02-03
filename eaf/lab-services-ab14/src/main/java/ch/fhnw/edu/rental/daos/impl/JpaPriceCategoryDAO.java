package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.model.PriceCategory;

@Component
public class JpaPriceCategoryDAO extends AbstractManagedDAO<PriceCategory> implements PriceCategoryDAO {

	@Override
	public PriceCategory getById(Long id) {
		return em.find(PriceCategory.class, id);
	}

	@Override
	public void delete(PriceCategory priceCategory) {
		em.remove(priceCategory);
	}

	@Override
	public List<PriceCategory> getAll() {
		TypedQuery<PriceCategory> q = em.createNamedQuery("pricecategory.all", PriceCategory.class);
		return q.getResultList();
	}

	@Override
	public void saveOrUpdate(PriceCategory priceCategory) {
		if (priceCategory.getId() != null) {
			em.merge(priceCategory);
		} else {
			em.persist(priceCategory);
		}
	}
}
