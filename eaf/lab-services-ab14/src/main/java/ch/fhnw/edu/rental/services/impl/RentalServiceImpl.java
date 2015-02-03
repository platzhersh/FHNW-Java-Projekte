package ch.fhnw.edu.rental.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.edu.rental.daos.MovieDAO;
import ch.fhnw.edu.rental.daos.RentalDAO;
import ch.fhnw.edu.rental.daos.impl.ManagedDAO;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.services.RentalService;
import ch.fhnw.edu.rental.services.RentalServiceException;

@Service
public class RentalServiceImpl implements RentalService {
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private RentalDAO rentalDAO;
	@Autowired
	private MovieDAO movieDAO;
	
	@Override
	public List<Rental> getAllRentals() throws RentalServiceException {
		List<Rental> rentals = rentalDAO.getAll();
		if (log.isDebugEnabled()) {
			log.debug("getAllRentals() done");
		}
		return rentals;
	}

	@Override
	public int calcRemainingDaysOfRental(Rental rental, Date date) {
		return rental.calcRemainingDaysOfRental(date);
	}

	@Override
	public Rental getRentalById(Long id) {
		return rentalDAO.getById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteRental(Rental rental) throws RentalServiceException {
		if (rental == null) {
			throw new RentalServiceException("'rental' parameter is not set!");
		}
		if(rentalDAO instanceof ManagedDAO<?>) {
			rental = ((ManagedDAO<Rental>)rentalDAO).manage(rental);
		}

		rental.getUser().getRentals().remove(rental);
		rental.getMovie().setRented(false);

		rentalDAO.delete(rental);
		
		if( !(movieDAO instanceof ManagedDAO<?>)){
			movieDAO.saveOrUpdate(rental.getMovie());
		}
		
		if (log.isDebugEnabled()) {
			log.debug("rental[" + rental.getId() + "] deleted");
		}		
	}
}
