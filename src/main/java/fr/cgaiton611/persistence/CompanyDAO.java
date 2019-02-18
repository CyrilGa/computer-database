package fr.cgaiton611.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.cgaiton611.model.Company;

/**
 * CRUD operations for entity Company
 * 
 * @author cyril
 * @version 1.0
 */
public class CompanyDAO extends DAO<Company> {
	
	private static final String SQL_CREATE = "INSERT INTO company(name) VALUES(?, ?, ?, ?)";
	private static final String SQL_FIND = "SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_UPDATE = "UPDATE company SET name = ? WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM company WHERE id = ? ";
	private static final String SQL_FIND_PAGED = "SELECT * FROM company LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT = "SELECT COUNT(*) as count FROM company";

	private static CompanyDAO instance = new CompanyDAO();
	
	private CompanyDAO() {};
	
	public static CompanyDAO getInstance() {
		return instance;
	}
	
	@Override
	public Optional<Company> create(Company obj) {
		Company company = null;
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_CREATE,
				Statement.RETURN_GENERATED_KEYS)) {
			prepare.setString(1, obj.getName());

			prepare.executeUpdate();

			ResultSet rs = prepare.getGeneratedKeys();
			if (rs.next()) {
				int generated_id = rs.getInt(1);
				company = new Company(generated_id, obj.getName());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.of(company);
	}

	@Override
	public Optional<Company> find(Company obj) {
		Company company = null;
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_FIND)) {
			prepare.setLong(1, obj.getId());
			ResultSet rs = prepare.executeQuery();
			if (rs.first()) {
				company = new Company(rs.getInt("id"), rs.getString("name"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(company);
	}

	@Override
	public Optional<Company> update(Company obj) {
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_UPDATE)) {
			prepare.setString(1, obj.getName());
			prepare.setLong(2, obj.getId());
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return find(new Company(obj.getId()));
	}

	@Override
	public void delete(Company obj) {
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_DELETE)) {
			prepare.setLong(1, obj.getId());
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Company> findPaged(int page, int elements) {
		List<Company> companies = null;
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_FIND_PAGED)) {
			prepare.setInt(1, elements);
			prepare.setInt(2, page * elements);
			ResultSet rs = prepare.executeQuery();
			companies = new ArrayList<>();
			while (rs.next()) {
				companies.add(new Company(rs.getLong("id"), rs.getString("name")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}
	
	public int count() {
		int max = 0;
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_COUNT)) {
			ResultSet rs = prepare.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

}
