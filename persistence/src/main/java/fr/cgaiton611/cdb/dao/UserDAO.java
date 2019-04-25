package fr.cgaiton611.cdb.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.NoResultRowException;
import fr.cgaiton611.cdb.exception.QueryException;
import fr.cgaiton611.cdb.exception.UpdateException;
import fr.cgaiton611.cdb.model.User;

@Repository
public class UserDAO {

	private static final String HQL_FIND_BY_NAME = "SELECT user FROM User user WHERE username = :username ";
	private final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	SessionFactory sessionFactory;

	public User findByName(String name) throws DAOException {
		User user;
		try (Session session = sessionFactory.openSession()) {
			Query<User> query = session.createQuery(HQL_FIND_BY_NAME, User.class);
			query.setParameter("username", name);
			user = query.uniqueResult();
		} catch (HibernateException e) {
			throw new QueryException();
		}
		if (user == null) {
			throw new NoResultRowException();
		}
		return user;
	}
	
	public User create(User obj) throws DAOException {
		try (Session session = sessionFactory.openSession()) {
			session.save(obj);
		} catch (HibernateException e) {
			logger.warn(e.getCause().getClass().toString());
			throw new UpdateException();
		}
		return obj;
	}
	
}
