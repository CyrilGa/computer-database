package fr.cgaiton611.cdb.service;

import fr.cgaiton611.cdb.dao.UserDAO;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	private UserDAO userDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public User create(User user) throws DAOException{
		return userDAO.create(user);
	}

	public User loadUser(String username) {
		User user;
		try {
			user = userDAO.findByName(username);
		} catch (DAOException e) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}

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
}
