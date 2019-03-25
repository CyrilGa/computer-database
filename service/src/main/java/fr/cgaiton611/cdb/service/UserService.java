package fr.cgaiton611.cdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.User;
import fr.cgaiton611.cdb.persistence.UserDAO;

@Service
public class UserService implements UserDetailsService {

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
		return user;
	}

}
