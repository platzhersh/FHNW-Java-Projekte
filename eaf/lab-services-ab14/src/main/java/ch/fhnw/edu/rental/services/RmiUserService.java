package ch.fhnw.edu.rental.services;

import java.util.List;

import ch.fhnw.edu.rental.dto.UserDTO;

public interface RmiUserService {
	List<UserDTO> getAllUsers() throws RentalServiceException;
	UserDTO getUserById(Long id) throws RentalServiceException;
	Long saveOrUpdateUser(UserDTO user) throws RentalServiceException;
	void deleteUser(Long id) throws RentalServiceException;
}
