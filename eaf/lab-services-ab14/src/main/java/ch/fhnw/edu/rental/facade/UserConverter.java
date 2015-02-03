package ch.fhnw.edu.rental.facade;

import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;

import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.services.UserService;

public class UserConverter extends DozerConverter<User, Long> { //implements CustomConverter {

	@Autowired
	private UserService userService;
	
	public UserConverter(){
		super(User.class, Long.class);
	}

	@Override
	public User convertFrom(Long id, User user) {
		return userService.getUserById(id);
	}

	@Override
	public Long convertTo(User user, Long id) {
		return user.getId();
	}
	

//	@Override
//	public Object convert(Object destination, Object source,
//			Class<?> destinationClass, Class<?> sourceClass) {
//		if (source == null) {
//			return null;
//		} else if (source instanceof User) {
//			return ((User) source).getId();
//		} else if (source instanceof Long) {
//			throw new IllegalArgumentException();
//			// bzw. DAO.getById(source);
//		} else {
//			throw new IllegalArgumentException();
//		}
//	}

}
