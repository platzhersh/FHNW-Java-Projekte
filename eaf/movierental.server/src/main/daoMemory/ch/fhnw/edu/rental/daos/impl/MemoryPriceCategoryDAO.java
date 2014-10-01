package ch.fhnw.edu.rental.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.fhnw.edu.rental.daos.PriceCategoryDAO;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.PriceCategoryChildren;
import ch.fhnw.edu.rental.model.PriceCategoryNewRelease;
import ch.fhnw.edu.rental.model.PriceCategoryRegular;

public class MemoryPriceCategoryDAO implements PriceCategoryDAO {
	
	private Map<Long, PriceCategory> data = new HashMap<Long, PriceCategory>();
	private long nextId = 1;

	@SuppressWarnings("unused")
	private void initData () {
		data.clear();
		nextId = 1;
		saveOrUpdate(new PriceCategoryRegular());
		saveOrUpdate(new PriceCategoryChildren());
		saveOrUpdate(new PriceCategoryNewRelease());
	}
	
	@Override
	public void delete(PriceCategory priceCategory) {
		data.remove(priceCategory.getId());
		priceCategory.setId(null);
	}

	@Override
	public List<PriceCategory> getAll() {
		return new ArrayList<PriceCategory>(data.values());
	}

	@Override
	public PriceCategory getById(Long id) {
		return data.get(id);
	}

	@Override
	public void saveOrUpdate(PriceCategory category) {
		if(category.getId()==null) category.setId(nextId++);
		data.put(category.getId(), category);
	}

}
