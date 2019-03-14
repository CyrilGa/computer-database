package fr.cgaiton611.persistence;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.List;

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
	private static final String SQL_FIND_BY_NAME_PAGED = "SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as companyName "
			+ "FROM computer left JOIN company ON company_id = company.id WHERE computer.name LIKE ? "
			+ "ORDER BY {0} {1} LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) as count FROM computer " + "WHERE name LIKE ? ";
	private static final String SQL_FIND_BY_NAME_PAGED_WITH_COMPANY_NAME = "SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as companyName "
			+ "FROM computer JOIN company "
			+ "ON company.id IN (SELECT id FROM company WHERE name LIKE ? ) AND company_id = company.id "
			+ "WHERE computer.name LIKE ? ORDER BY {0} {1} LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT_BY_NAME_WITH_COMPANY_NAME = "SELECT COUNT(*) as count FROM computer JOIN company "
			+ "ON company.id IN (SELECT id FROM company WHERE name LIKE ? ) AND company_id = company.id "
			+ "WHERE computer.name LIKE ? ";

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

	public List<Computer> findPage(int page, int elements) throws DAOException{
		return findPage(page, elements, "", "", "computer.id", "ASC");
	}

	public int count() throws DAOException{
		return count("", "");
	}

	public List<Computer> findPage(int page, int elements, String computerName, String companyName, String orderByName,
			String orderByOrder) throws DAOException {
		String SQL;
		if ("".equals(companyName)) {
			SQL = SQL_FIND_BY_NAME_PAGED;
		} else {
			SQL = SQL_FIND_BY_NAME_PAGED_WITH_COMPANY_NAME;
		}

		SQL = MessageFormat.format(SQL, orderByName, orderByOrder);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Computer> computers;
		try {
			if ("".equals(companyName)) {
				computers = jdbcTemplate.query(SQL, computerRowMapper, "%" + computerName + "%", elements,
						page * elements);
			} else {
				computers = jdbcTemplate.query(SQL, computerRowMapper, "%" + companyName + "%",
						"%" + computerName + "%", elements, page * elements);
			}
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return computers;
	}

	public int count(String computerName, String companyName) throws DAOException{
		String SQL;
		if ("".equals(companyName)) {
			SQL = SQL_COUNT_BY_NAME;
		} else {
			SQL = SQL_COUNT_BY_NAME_WITH_COMPANY_NAME;
		}
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		int count;
		try {
			if ("".equals(companyName)) {
				count = jdbcTemplate.queryForObject(SQL, Integer.class, "%" + computerName + "%");
			} else {
				count = jdbcTemplate.queryForObject(SQL, Integer.class, "%" + companyName + "%",
						"%" + computerName + "%");
			}
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return count;
	}

}
