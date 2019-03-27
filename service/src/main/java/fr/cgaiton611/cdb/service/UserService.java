package fr.cgaiton611.cdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.cgaiton611.cdb.dao.UserDAO;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.User;

@Service
public class UserService implements UserDetailsService {

//	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try {
			user = userDAO.findByName(username);
		} catch (DAOException e) {
			throw new UsernameNotFoundException(username);
		}
		UserBuilder builder;
		builder = org.springframework.security.core.userdetails.User.withUsername(username);
		builder.password(user.getPassword());
		builder.roles(user.getRole());
		return builder.build();
	}
	
	public User create(User user) throws DAOException{
		return userDAO.create(user);
	}

}
