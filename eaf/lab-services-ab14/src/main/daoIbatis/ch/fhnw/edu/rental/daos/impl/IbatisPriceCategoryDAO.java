package ch.fhnw.edu.rental.daos.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.model.PriceCategory;

public class IbatisPriceCategoryDAO extends SqlMapClientDaoSupport implements PriceCategoryDAO {

	@Override
	public PriceCategory getById(Long id) {
		return (PriceCategory)getSqlMapClientTemplate().queryForObject("PriceCategory.getById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceCategory> getAll() {
		return (List<PriceCategory>)getSqlMapClientTemplate().queryForList("PriceCategory.getAll");
	}

	@Override
	public void saveOrUpdate(PriceCategory priceCategory) {
		if(priceCategory.getId() == null){
			// insert
			getSqlMapClientTemplate().insert("PriceCategory.insert", priceCategory);
		}
		else {
			// update
			getSqlMapClientTemplate().update("PriceCategory.update", priceCategory);
		}
	}

	@Override
	public void delete(PriceCategory priceCategory) {
		getSqlMapClientTemplate().delete("PriceCategory.delete", priceCategory);
		priceCategory.setId(null);
	}

}
