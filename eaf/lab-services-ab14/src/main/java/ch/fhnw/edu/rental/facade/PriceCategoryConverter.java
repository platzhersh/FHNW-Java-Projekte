package ch.fhnw.edu.rental.facade;

import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;

import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.services.MovieService;

public class PriceCategoryConverter extends DozerConverter<PriceCategory, String>  {
	@Autowired
	private MovieService movieService;

	public PriceCategoryConverter() {
		super(PriceCategory.class, String.class);
	}

	@Override
	public PriceCategory convertFrom(String str, PriceCategory category) {
		for(PriceCategory pc : movieService.getAllPriceCategories()){
			if(str.equals(pc.toString())){
				return pc;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String convertTo(PriceCategory pc, String str) {
		return pc.toString();
	}

}
