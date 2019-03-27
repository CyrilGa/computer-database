package fr.cgaiton611.cdb.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.NoResultRowException;
import fr.cgaiton611.cdb.exception.NoRowUpdatedException;
import fr.cgaiton611.cdb.exception.QueryException;
import fr.cgaiton611.cdb.exception.UpdateException;
import fr.cgaiton611.cdb.model.Company;

/**
 * CRUD operations for entity Company
 * 
 * @author cyril
 * @version 1.0
 */

@Repository
public class CompanyDAO extends DAO<Company> {

//	private static final String SQL_CREATE = "INSERT INTO company(name) VALUES(?)";
	private static final String HQL_FIND = "SELECT cpa FROM Company cpa WHERE cpa.id = :id";
	private static final String HQL_UPDATE = "UPDATE Company SET name = :name WHERE id = :id";
	private static final String HQL_DELETE = "DELETE Company WHERE id = :id ";
	private static final String HQL_FIND_PAGE = "SELECT cpa FROM company cpa";
	private static final String HQL_COUNT = "SELECT COUNT(cpa) FROM Company cpa";
	private static final String HQL_FIND_BY_NAME = "SELECT cpa FROM Company cpa WHERE name = :name";
	private static final String HQL_FIND_ALL_NAME = "SELECT cpa.name FROM Company cpa";
	private static final String HQL_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE Computer WHERE company_id = :id ";

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Company create(Company obj) throws DAOException {
		try (Session session = sessionFactory.openSession()) {
			session.save(obj);
		} catch (HibernateException e) {
			throw new UpdateException();
		}
		return obj;
	}

	@Override
	public Company find(Company obj) throws DAOException {
		Company company;
		try (Session session = sessionFactory.openSession()) {
			Query<Company> query = session.createQuery(HQL_FIND, Company.class);
			query.setParameter("id", obj.getId());
			company = query.uniqueResult();
		} catch (HibernateException e) {
			throw new QueryException();
		}
		if (company == null) {
			throw new NoResultRowException();
		}
		return company;
	}

	@Override
	public Company update(Company obj) throws DAOException {
		int row;
		try (Session session = sessionFactory.openSession()) {
			try {
				session.getTransaction().begin();
				row = session.createQuery(HQL_UPDATE).setParameter("name", obj.getName())
						.setParameter("id", obj.getId()).executeUpdate();
				session.getTransaction().commit();
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				throw new UpdateException();
			}
			if (row == 0) {
				session.getTransaction().rollback();
				throw new NoRowUpdatedException();
			}
		}
		return obj;
	}

	@Override
	public void delete(Company obj) throws DAOException {
		int row;
		try (Session session = sessionFactory.openSession()) {
			try {
				session.getTransaction().begin();
				session.createQuery(HQL_DELETE_COMPUTER_BY_COMPANY_ID).setParameter("id", obj.getId()).executeUpdate();
				row = session.createQuery(HQL_DELETE).setParameter("id", obj.getId()).executeUpdate();
				session.getTransaction().commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				throw new UpdateException();
			}
			if (row == 0) {
				session.getTransaction().rollback();
				throw new NoRowUpdatedException();
			}
		}
	}

	public List<Company> findPage(int page, int elements) throws DAOException {
		List<Company> companies;
		try (Session session = sessionFactory.openSession()) {
			Query<Company> query = session.createQuery(HQL_FIND_PAGE, Company.class);
			query.setMaxResults(elements);
			query.setFirstResult(page * elements);
			companies = query.list();
		} catch (HibernateException e) {
			throw new UpdateException();
		}
		return companies;
	}

	public int count() throws DAOException {
		long count;
		try (Session session = sessionFactory.openSession()) {
			count = session.createQuery(HQL_COUNT, Long.class).uniqueResult();
		} catch (HibernateException e) {
			throw new UpdateException();
		}
		return (int) count;
	}

	public Company findByName(String name) throws DAOException {
		Company company;
		try (Session session = sessionFactory.openSession()) {
			Query<Company> query = session.createQuery(HQL_FIND_BY_NAME, Company.class);
			query.setParameter("name", name);
			company = query.uniqueResult();
		} catch (HibernateException e) {
			throw new QueryException();
		}
		if (company == null) {
			throw new NoResultRowException();
		}
		return company;
	}

	public List<String> findAllName() throws DAOException {

		List<String> names;
		try (Session session = sessionFactory.openSession()) {
			Query<String> query = session.createQuery(HQL_FIND_ALL_NAME, String.class);
			names = query.list();
		} catch (HibernateException e) {
			throw new UpdateException();
		}
		return names;
	}

}
