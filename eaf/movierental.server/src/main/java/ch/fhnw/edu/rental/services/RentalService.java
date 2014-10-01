package ch.fhnw.edu.rental.services;

import java.util.Date;
import java.util.List;

import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.service.RentalServiceException;

public interface RentalService {
	public Rental getRentalById(Long id);

	public List<Rental> getAllRentals() throws RentalServiceException;
	
	public int calcRemainingDaysOfRental(Rental rental, Date date);
	
	public void deleteRental(Rental rental) throws RentalServiceException;
}
