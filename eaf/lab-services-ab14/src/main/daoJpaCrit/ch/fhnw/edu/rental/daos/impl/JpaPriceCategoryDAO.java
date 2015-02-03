package ch.fhnw.edu.rental.daos.impl;

import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.model.PriceCategory;

public class JpaPriceCategoryDAO extends AbstractManagedDAO<PriceCategory> implements PriceCategoryDAO {

	public JpaPriceCategoryDAO() {
		super(PriceCategory.class);
	}

}
