package fr.cgaiton611.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.DataSourceException;
import fr.cgaiton611.exception.dao.EmptyResultSetException;
import fr.cgaiton611.model.Company;

/**
 * CRUD operations for entity Company
 * 
 * @author cyril
 * @version 1.0
 */

@Repository
public class CompanyDAO extends DAO<Company>{
	
	private static final String SQL_CREATE = "INSERT INTO company(name) VALUES(?)";
	private static final String SQL_FIND = "SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_UPDATE = "UPDATE company SET name = ? WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM company WHERE id = ? ";
	private static final String SQL_FIND_PAGED = "SELECT id, name FROM company LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT = "SELECT COUNT(*) as count FROM company";
	private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM company WHERE name = ?";
	private static final String SQL_FIND_ALL_NAME = "SELECT name FROM company";
	private static final String SQL_DELETE_COMPUTER_CASCADE = "DELETE FROM computer WHERE company_id = ? ";

	@Override
	public Company create(Company obj) throws DAOException {
		checkDataSource();
		Company company = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
			prepare.setString(1, obj.getName());
			prepare.executeUpdate();
			ResultSet rs = prepare.getGeneratedKeys();
			
			if (rs.first()) {
				int generated_id = rs.getInt(1);
				company = new Company(generated_id, obj.getName());
			}
			else {
				throw new EmptyResultSetException();
			}
		} catch (SQLException e) {
			throw new DAOException();
		}

		return company;
	}

	@Override
	public Company find(Company obj) throws DAOException {
		checkDataSource();
		Company company = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_FIND)) {
			prepare.setLong(1, obj.getId());
			ResultSet rs = prepare.executeQuery();
			
			if (rs.next()) {
				company = new Company(rs.getInt("id"), rs.getString("name"));
			}
			else {
				throw new EmptyResultSetException();
			}
		} catch (SQLException e) {
			throw new DAOException();
		}
		return company;
	}

	@Override
	public Company update(Company obj) throws DAOException {
		checkDataSource();
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_UPDATE)) {
			prepare.setString(1, obj.getName());
			prepare.setLong(2, obj.getId());
			int row = prepare.executeUpdate();
			if(row == 0) {
				throw new EmptyResultSetException();
			}

		} catch (SQLException e) {
			throw new DAOException();
		}

		return find(new Company(obj.getId()));
	}

	@Override
	public void delete(Company obj) throws DAOException {
		checkDataSource();
		try (Connection connection = ds.getConnection()) {
			connection.setAutoCommit(false);
			try (PreparedStatement prepare1 = connection.prepareStatement(SQL_DELETE_COMPUTER_CASCADE);
					PreparedStatement prepare2 = connection.prepareStatement(SQL_DELETE)) {
				prepare1.setLong(1, obj.getId());
				prepare1.executeUpdate();
				prepare2.setLong(1, obj.getId());
				int row = prepare2.executeUpdate();
				if(row == 0) {
					throw new EmptyResultSetException();
				}
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
			}
			connection.commit();
		} catch (SQLException e1) {
			throw new DAOException();
		}
	}

	public List<Company> findPaged(int page, int elements) throws DAOException {
		checkDataSource();
		List<Company> companies = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_FIND_PAGED)) {
			prepare.setInt(1, elements);
			prepare.setInt(2, page * elements);
			ResultSet rs = prepare.executeQuery();
			companies = new ArrayList<>();
			
			while (rs.next()) {
				companies.add(new Company(rs.getLong("id"), rs.getString("name")));
			}

		} catch (SQLException e) {
			throw new DAOException();
		}
		return companies;
	}

	public int count() throws DAOException {
		checkDataSource();
		int max = 0;
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_COUNT)) {
			ResultSet rs = prepare.executeQuery();
			
			if (rs.next()) {
				System.out.println("salut");
				max = rs.getInt("count");
			}
			else {
				throw new EmptyResultSetException();
			}
		} catch (SQLException e) {
			throw new DAOException();
		}
		return max;
	}

	public Company findByName(String name) throws DAOException {
		checkDataSource();
		Company company = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_FIND_BY_NAME)) {
			prepare.setString(1, name);
			ResultSet rs = prepare.executeQuery();
			
			if (rs.next()) {
				company = new Company(rs.getInt("id"), rs.getString("name"));
			}
			else {
				throw new EmptyResultSetException();
			}
		} catch (SQLException e) {
			throw new DAOException();
		}
		return company;
	}

	public List<String> findAllName() throws DAOException {
		checkDataSource();
		List<String> names = new ArrayList<>();
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_FIND_ALL_NAME)) {
			ResultSet rs = prepare.executeQuery();
			
			while (rs.next()) {
				names.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			throw new DAOException();
		}
		return names;
	}
	
	private void checkDataSource() throws DataSourceException {
		try(Connection connection = ds.getConnection()) {
		} catch (IllegalArgumentException | SQLException e) {
			throw new DataSourceException();
		}
	}

}
