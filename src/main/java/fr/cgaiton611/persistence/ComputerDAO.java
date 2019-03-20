package fr.cgaiton611.persistence;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.NoRowUpdatedException;
import fr.cgaiton611.exception.dao.NotOneResultException;
import fr.cgaiton611.exception.dao.QueryException;
import fr.cgaiton611.exception.dao.UpdateException;
import fr.cgaiton611.model.Company;
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

	private static final String SQL_CREATE = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
	private static final String SQL_FIND = "SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as companyName "
			+ "FROM computer left JOIN company ON company_id = company.id WHERE computer.id = ? ";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ? ,introduced = ? ,discontinued = ? ,company_id = ?  WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ? ";
	private static final String HQL_FIND_BY_NAME_PAGED = "SELECT cpu FROM Computer cpu left JOIN Company cpa "
			+ "ON cpu.company = cpa.id WHERE lower(cpu.name) LIKE lower(:cpuName) ORDER BY {0} {1}";
	private static final String HQL_FIND_BY_NAME_PAGED_WITH_COMPANY_NAME = "SELECT cpu FROM Computer cpu JOIN Company cpa "
			+ "ON cpa.id IN (SELECT cpa.id FROM Company cpa WHERE lower(cpa.name) LIKE lower(:cpaName) ) AND cpu.company_id = cpa.id "
			+ "WHERE lower(cpu.name) LIKE lower(:cpuName) ORDER BY {0} {1}";
	private static final String HQL_COUNT_BY_NAME = "SELECT COUNT(cpu) FROM Computer cpu "
			+ "WHERE lower(cpu.name) LIKE lower(:cpuName) ";
	private static final String HQL_COUNT_BY_NAME_WITH_COMPANY_NAME = "SELECT COUNT(cpu) FROM Computer cpu JOIN Company cpa "
			+ "ON cpa.id IN (SELECT cpa.id FROM Company cpa WHERE lower(cpa.name) LIKE lower(:cpaName) ) AND cpu.company_id = cpa.id "
			+ "WHERE lower(cpu.name) LIKE lower(:cpuName) ";

	@Autowired
	private SessionFactory sessionFactory;

	private RowMapper<Computer> computerRowMapper = (rs, pRowNum) -> new Computer(rs.getLong("id"),
			rs.getString("name"), rs.getTimestamp("introduced"), rs.getTimestamp("discontinued"),
			new Company(rs.getLong("company_id"), rs.getString("companyName")));

	@Override
	public Computer create(Computer obj) throws DAOException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		int row;
		try {
			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, obj.getName());
				ps.setTimestamp(2, convertUtil.dateToTimestamp(obj.getIntroduced()));
				ps.setTimestamp(3, convertUtil.dateToTimestamp(obj.getDiscontinued()));
				if (obj.getCompany().getId() != -1) {
					ps.setLong(4, obj.getCompany().getId());
				} else {
					ps.setNull(4, Types.INTEGER);
				}
				return ps;
			}, keyHolder);
		} catch (DataAccessException e) {
			throw new UpdateException();
		}
		if (row == 0) {
			throw new NoRowUpdatedException();
		}
		obj.setId(keyHolder.getKey().longValue());
		return obj;
	}

	@Override
	public Computer find(Computer obj) throws DAOException {
		Computer computer;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Object[] params = { new SqlParameterValue(Types.INTEGER, obj.getId()) };
		try {
			computer = jdbcTemplate.queryForObject(SQL_FIND, computerRowMapper, params);
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new NotOneResultException();
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return computer;
	}

	@Override
	public Computer update(Computer obj) throws DAOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		SqlParameterValue param;
		if (obj.getCompany().getId() != -1) {
			param = new SqlParameterValue(Types.INTEGER, obj.getCompany().getId());
		} else {
			param = new SqlParameterValue(Types.INTEGER, null);
		}
		Object[] params = { new SqlParameterValue(Types.VARCHAR, obj.getName()),
				new SqlParameterValue(Types.TIMESTAMP, convertUtil.dateToTimestamp(obj.getIntroduced())),
				new SqlParameterValue(Types.TIMESTAMP, convertUtil.dateToTimestamp(obj.getDiscontinued())), param,
				new SqlParameterValue(Types.INTEGER, obj.getId()) };
		int row;
		try {
			row = jdbcTemplate.update(SQL_UPDATE, params);
		} catch (DataAccessException e) {
			throw new UpdateException();
		}
		if (row == 0) {
			throw new NoRowUpdatedException();
		}
		return obj;
	}

	@Override
	public void delete(Computer obj) throws DAOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Object[] params = { new SqlParameterValue(Types.INTEGER, obj.getId()) };
		int row;
		try {
			row = jdbcTemplate.update(SQL_DELETE, params);
		} catch (DataAccessException e) {
			throw new UpdateException();
		}
		if (row == 0) {
			throw new NoRowUpdatedException();
		}
	}

	public List<Computer> findPage(int page, int elements) throws DAOException {
		return findPage(page, elements, "", "", "computer.id", "ASC");
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
						MessageFormat.format(HQL_FIND_BY_NAME_PAGED, orderByName, orderByOrder), Computer.class);
				query.setParameter("cpuName", "%" + computerName + "%");
				query.setMaxResults(elements);
				query.setFirstResult(page * elements);
				computers = query.list();
			} else {
				Query<Computer> query = session.createQuery(
						MessageFormat.format(HQL_FIND_BY_NAME_PAGED_WITH_COMPANY_NAME, orderByName, orderByOrder),
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
