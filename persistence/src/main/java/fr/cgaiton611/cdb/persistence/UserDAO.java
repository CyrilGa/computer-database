package fr.cgaiton611.cdb.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.NoResultRowException;
import fr.cgaiton611.cdb.exception.QueryException;
import fr.cgaiton611.cdb.model.User;

@Repository
public class UserDAO {

	private static final String HQL_FIND_BY_NAME = "SELECT user FROM User user WHERE name = :name";
	
	@Autowired
	SessionFactory sessionFactory;

	public User findByName(String name) throws DAOException {
		User user;
		try (Session session = sessionFactory.openSession()) {
			Query<User> query = session.createQuery(HQL_FIND_BY_NAME, User.class);
			query.setParameter("name", name);
			user = query.uniqueResult();
		} catch (HibernateException e) {
			throw new QueryException();
		}
		if (user == null) {
			throw new NoResultRowException();
		}
		return user;
	}
	
}
