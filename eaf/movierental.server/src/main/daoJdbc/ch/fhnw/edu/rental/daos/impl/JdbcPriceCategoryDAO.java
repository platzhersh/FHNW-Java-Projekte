package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import javax.sql.DataSource;

import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.model.PriceCategory;

public class JdbcPriceCategoryDAO implements PriceCategoryDAO {

	private DataSource ds;

	public void setDataSource(DataSource dataSource) {
		this.ds = dataSource;
	}

	@Override
	public PriceCategory getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PriceCategory> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(PriceCategory priceCategory) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(PriceCategory priceCategory) {
		// TODO Auto-generated method stub
	}
}
