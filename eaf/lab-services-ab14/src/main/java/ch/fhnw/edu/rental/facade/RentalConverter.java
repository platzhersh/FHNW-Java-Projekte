package ch.fhnw.edu.rental.facade;

import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.services.RentalService;

public class RentalConverter extends DozerConverter<Rental, Long> { //implements CustomConverter {
	
	@Autowired
	private RentalService rentalService;
	
	public RentalConverter(){
		super(Rental.class, Long.class);
	}

	@Override
	public Rental convertFrom(Long id, Rental rental) {
		return rentalService.getRentalById(id);
	}

	@Override
	public Long convertTo(Rental rental, Long id) {
		return rental.getId();
	}

//	@Override
//	public Object convert(Object destination, Object source,
//			Class<?> destinationClass, Class<?> sourceClass) {
//		if (source == null) {
//			return null;
//		} else if (source instanceof Rental) {
//			return ((Rental) source).getId();
//		} else if (source instanceof Long) {
//			throw new IllegalArgumentException();
//			// bzw. DAO.getById(source);
//		} else {
//			throw new IllegalArgumentException();
//		}
//	}

}
