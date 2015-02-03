package ch.fhnw.edu.rental.services;

public class RentalServiceException extends RuntimeException {
	private static final long serialVersionUID = -7450723773538995540L;

	public RentalServiceException(String message) {
		super(message);
	}
	
	public RentalServiceException(String message, Throwable t) {
		super(message, t);
	}
}
