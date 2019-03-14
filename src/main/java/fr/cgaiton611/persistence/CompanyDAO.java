package fr.cgaiton611.persistence;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.NoRowUpdatedException;
import fr.cgaiton611.exception.dao.NotOneResultException;
import fr.cgaiton611.exception.dao.QueryException;
import fr.cgaiton611.exception.dao.UpdateException;
import fr.cgaiton611.model.Company;

/**
 * CRUD operations for entity Company
 * 
 * @author cyril
 * @version 1.0
 */

@Repository
public class CompanyDAO extends DAO<Company> {

	private static final String SQL_CREATE = "INSERT INTO company(name) VALUES(?)";
	private static final String SQL_FIND = "SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_UPDATE = "UPDATE company SET name = ? WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM company WHERE id = ? ";
	private static final String SQL_FIND_PAGED = "SELECT id, name FROM company LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT = "SELECT COUNT(*) as count FROM company";
	private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM company WHERE name = ?";
	private static final String SQL_FIND_ALL_NAME = "SELECT name FROM company";
	private static final String SQL_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ? ";

	private RowMapper<Company> companyRowMapper = (rs, pRowNum) -> new Company(rs.getLong("id"), rs.getString("name"));

	@Override
	public Company create(Company obj) throws DAOException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		int row;
		try {
			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, obj.getName());
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
	public Company find(Company obj) throws DAOException {
		Company company;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Object[] params = { new SqlParameterValue(Types.INTEGER, obj.getId()) };
		try {
			company = jdbcTemplate.queryForObject(SQL_FIND, companyRowMapper, params);
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new NotOneResultException();
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return company;
	}

	@Override
	public Company update(Company obj) throws DAOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Object[] params = { new SqlParameterValue(Types.VARCHAR, obj.getName()),
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
	@Transactional
	public void delete(Company obj) throws DAOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Object[] params = { new SqlParameterValue(Types.INTEGER, obj.getId()) };
		int row;
		try {
			row = jdbcTemplate.update(SQL_DELETE_COMPUTER_BY_COMPANY_ID, params);
			row = jdbcTemplate.update(SQL_DELETE, params);
		} catch (DataAccessException e) {
			throw new UpdateException();
		}
		if (row == 0) {
			throw new NoRowUpdatedException();
		}
	}

	public List<Company> findPage(int page, int elements) throws DAOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Company> companies;
		try {
			companies = jdbcTemplate.query(SQL_FIND_PAGED, companyRowMapper, elements, page * elements);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return companies;
	}

	public int count() throws DAOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		int count;
		try {
			count = jdbcTemplate.queryForObject(SQL_COUNT, Integer.class);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return count;
	}

	public Company findByName(String name) throws DAOException {
		Company company;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Object[] params = { new SqlParameterValue(Types.VARCHAR, name) };
		try {
			company = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, companyRowMapper, params);
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return company;
	}

	public List<String> findAllName() throws DAOException {
		List<String> names;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		try {
			names = jdbcTemplate.query(SQL_FIND_ALL_NAME, (RowMapper<String>) (rs, rowNum) -> rs.getString("name"));
		} catch (DataAccessException e) {
			throw new QueryException();
		}
		return names;
	}

}
