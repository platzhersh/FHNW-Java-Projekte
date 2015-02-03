package ch.fhnw.edu.rental.services;

import java.util.List;

import ch.fhnw.edu.rental.dto.RentalDTO;

public interface RmiRentalService {
	List<RentalDTO> getAllRentals() throws RentalServiceException;
	RentalDTO getRentalById(Long id) throws RentalServiceException;
//	Long saveOrUpdateRental(RentalDTO rental) throws RentalServiceException;
	void deleteRental(Long id) throws RentalServiceException;
	
	void rentMovie(Long userId, Long movieId, int days);
}
