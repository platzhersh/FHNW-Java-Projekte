package ch.fhnw.edu.rental.services;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.edu.rental.model.Rental;

@Transactional
public interface RentalService {
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public Rental getRentalById(Long id);

	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public List<Rental> getAllRentals() throws RentalServiceException;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public int calcRemainingDaysOfRental(Rental rental, Date date);
	
	public void deleteRental(Rental rental) throws RentalServiceException;
}
