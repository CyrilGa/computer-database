package fr.cgaiton611.persistence;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.NoRowUpdatedException;
import fr.cgaiton611.exception.dao.QueryException;
import fr.cgaiton611.exception.dao.UpdateException;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.util.ConvertUtil;

/**
 * CRUD operations for entity Computer
 * 
 * @author cyril
 * @version 1.0
 */

@Repository
public class ComputerDAO extends DAO<Computer> {

	private ConvertUtil convertUtil = new ConvertUtil();

//	private static final String HQL_CREATE = "INSERT INTO Computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
	private static final String HQL_FIND = "SELECT cpu FROM Computer cpu WHERE cpu.id = :id";
	private static final String HQL_UPDATE = "UPDATE Computer cpu SET cpu.name = :name ,cpu.introduced = :introduced ,"
			+ "cpu.discontinued = :discontinued, cpu.company = :cpaId  WHERE id = :cpuId ";
	private static final String HQL_DELETE = "DELETE Computer cpu WHERE cpu.id = :id";
	private static final String HQL_FIND_BY_NAME_PAGE = "SELECT cpu FROM Computer cpu LEFT JOIN Company cpa "
			+ "ON cpu.company = cpa.id WHERE lower(cpu.name) LIKE lower(:cpuName) ORDER BY {0} {1}";
	private static final String HQL_FIND_BY_NAME_PAGE_WITH_COMPANY_NAME = "SELECT cpu FROM Computer cpu JOIN Company cpa "
			+ "ON cpa.id IN (SELECT cpa.id FROM Company cpa WHERE lower(cpa.name) LIKE lower(:cpaName) ) AND cpu.company = cpa.id "
			+ "WHERE lower(cpu.name) LIKE lower(:cpuName) ORDER BY {0} {1}";
	private static final String HQL_COUNT_BY_NAME = "SELECT COUNT(cpu) FROM Computer cpu "
			+ "WHERE lower(cpu.name) LIKE lower(:cpuName) ";
	private static final String HQL_COUNT_BY_NAME_WITH_COMPANY_NAME = "SELECT COUNT(cpu) FROM Computer cpu JOIN Company cpa "
			+ "ON cpa.id IN (SELECT cpa.id FROM Company cpa WHERE lower(cpa.name) LIKE lower(:cpaName) ) AND cpu.company = cpa.id "
			+ "WHERE lower(cpu.name) LIKE lower(:cpuName) ";

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Computer create(Computer obj) throws DAOException {
		try (Session session = sessionFactory.openSession()) {
			session.save(obj);
		} catch (HibernateException e) {
			throw new UpdateException();
		}
		return obj;
	}

	@Override
	public Computer find(Computer obj) throws DAOException {
		Computer computer;
		try (Session session = sessionFactory.openSession()) {
			Query<Computer> query = session.createQuery(HQL_FIND, Computer.class);
			query.setParameter("id", obj.getId());
			computer = query.uniqueResult();
		} catch (HibernateException e) {
			throw new QueryException();
		}
		return computer;
	}

	@Override
	public Computer update(Computer obj) throws DAOException {
		Long cpaId;
		if (obj.getCompany() != null && obj.getCompany().getId() != 0) {
			cpaId = obj.getCompany().getId();
		} else {
			cpaId = null;
		}
		int row;
		try (Session session = sessionFactory.openSession()) {
			try {
				session.getTransaction().begin();
				row = session.createQuery(HQL_UPDATE).setParameter("name", obj.getName())
						.setParameter("introduced", convertUtil.dateToTimestamp(obj.getIntroduced()))
						.setParameter("discontinued", convertUtil.dateToTimestamp(obj.getDiscontinued()))
						.setParameter("cpaId", cpaId).setParameter("cpuId", obj.getId()).executeUpdate();
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
	public void delete(Computer obj) throws DAOException {
		int row;
		try (Session session = sessionFactory.openSession()) {
			try {
				session.getTransaction().begin();
				row = session.createQuery(HQL_DELETE).setParameter("id", obj.getId()).executeUpdate();
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
	}

	public List<Computer> findPage(int page, int elements) throws DAOException {
		return findPage(page, elements, "", "", "cpu.id", "ASC");
	}

	public int count() throws DAOException {
		return count("", "");
	}

	public List<Computer> findPage(int page, int elements, String computerName, String companyName, String orderByName,
			String orderByOrder) throws DAOException {

		List<Computer> computers;
		try (Session session = sessionFactory.openSession()) {
			if ("".equals(companyName)) {
				Query<Computer> query = session.createQuery(
						MessageFormat.format(HQL_FIND_BY_NAME_PAGE, orderByName, orderByOrder), Computer.class);
				query.setParameter("cpuName", "%" + computerName + "%");
				query.setMaxResults(elements);
				query.setFirstResult(page * elements);
				computers = query.list();
			} else {
				Query<Computer> query = session.createQuery(
						MessageFormat.format(HQL_FIND_BY_NAME_PAGE_WITH_COMPANY_NAME, orderByName, orderByOrder),
						Computer.class);
				query.setParameter("cpaName", "%" + companyName + "%");
				query.setParameter("cpuName", "%" + computerName + "%");
				query.setMaxResults(elements);
				query.setFirstResult(page * elements);
				computers = query.list();
			}
		} catch (HibernateException e) {
			throw new QueryException();
		}
		return computers;
	}

	public int count(String computerName, String companyName) throws DAOException {
		long count;
		try (Session session = sessionFactory.openSession()) {
			if ("".equals(companyName)) {
				count = session.createQuery(HQL_COUNT_BY_NAME, Long.class)
						.setParameter("cpuName", "%" + computerName + "%").uniqueResult();
			} else {
				count = session.createQuery(HQL_COUNT_BY_NAME_WITH_COMPANY_NAME, Long.class)
						.setParameter("cpaName", "%" + companyName + "%")
						.setParameter("cpuName", "%" + computerName + "%").uniqueResult();
			}
		} catch (HibernateException e) {
			throw new QueryException();
		}
		return (int) count;
	}

}
