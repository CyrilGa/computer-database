package fr.cgaiton611.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

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
	private static final String SQL_FIND = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ? ,introduced = ? ,discontinued = ? ,company_id = ?  WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ? ";
	private static final String SQL_FIND_PAGED = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT = "SELECT COUNT(*) as count FROM computer";
	private static final String SQL_FIND_BY_NAME_PAGED = "SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as companyName "
			+ "FROM computer left JOIN company ON company_id = company.id WHERE computer.name LIKE ? "
			+ "ORDER BY computer.id ASC " + "LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) as count FROM computer "
			+ "WHERE name LIKE ? ";
	private static final String SQL_FIND_BY_NAME_PAGED_WITH_COMPANY_NAME = "SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as companyName "
			+ "FROM computer JOIN company "
			+ "ON company.id IN (SELECT id FROM company WHERE name LIKE ? ) AND company_id = company.id "
			+ "WHERE computer.name LIKE ? ORDER BY computer.id ASC LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT_BY_NAME_WITH_COMPANY_NAME = "SELECT COUNT(*) as count FROM computer JOIN company "
			+ "ON company.id IN (SELECT id FROM company WHERE name LIKE ? ) AND company_id = company.id "
			+ "WHERE computer.name LIKE ? ";


	@Override
	public Optional<Computer> create(Computer obj) {
		Computer computer = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_CREATE,
				Statement.RETURN_GENERATED_KEYS)) {
			prepare.setString(1, obj.getName());
			prepare.setTimestamp(2, convertUtil.dateToTimestamp(obj.getIntroduced()));
			prepare.setTimestamp(3, convertUtil.dateToTimestamp(obj.getDiscontinued()));
			if (obj.getCompany().getId() != -1)
				prepare.setLong(4, obj.getCompany().getId());
			else
				prepare.setNull(4, Types.INTEGER);

			prepare.executeUpdate();

			ResultSet rs = prepare.getGeneratedKeys();
			if (rs.next()) {
				int generated_id = rs.getInt(1);
				computer = new Computer(generated_id, obj.getName(), (Date) obj.getIntroduced(),
						(Date) obj.getDiscontinued(), obj.getCompany());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(computer);
	}

	@Override
	public Optional<Computer> find(Computer obj) {
		Computer computer = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_FIND)) {
			prepare.setLong(1, obj.getId());
			ResultSet rs = prepare.executeQuery();
			if (rs.next()) {
				computer = new Computer(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"), new Company(rs.getLong("company_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(computer);
	}

	@Override
	public Optional<Computer> update(Computer obj) {
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_UPDATE)) {
			prepare.setString(1, obj.getName());
			prepare.setTimestamp(2, convertUtil.dateToTimestamp(obj.getIntroduced()));
			prepare.setTimestamp(3, convertUtil.dateToTimestamp(obj.getDiscontinued()));
			prepare.setLong(4, obj.getCompany().getId());
			prepare.setLong(5, obj.getId());
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return find(new Computer(obj.getId()));
	}

	@Override
	public void delete(Computer obj) {
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_DELETE)) {
			prepare.setLong(1, obj.getId());
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Computer> findPaged(int page, int elements) {
		List<Computer> computers = new ArrayList<>();
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_FIND_PAGED)) {
			prepare.setInt(1, elements);
			prepare.setInt(2, page * elements);
			ResultSet rs = prepare.executeQuery();
			while (rs.next()) {
				computers.add(new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"), new Company(rs.getLong("company_id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	public int count() {
		int max = 0;
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL_COUNT)) {
			ResultSet rs = prepare.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}

	public List<Computer> findByNamePaged(int page, int elements, String computerName, String companyName) {
		List<Computer> computers = new ArrayList<>();
		String SQL;
		if ("".equals(companyName)) {
			SQL = SQL_FIND_BY_NAME_PAGED;
		} else {
			SQL = SQL_FIND_BY_NAME_PAGED_WITH_COMPANY_NAME;
		}

		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL)) {

			if ("".equals(companyName)) {
				prepare.setString(1, "%" + computerName + "%");
				prepare.setInt(2, elements);
				prepare.setInt(3, page * elements);
			} else {
				prepare.setString(1, "%" + companyName + "%");
				prepare.setString(2, "%" + computerName + "%");
				prepare.setInt(3, elements);
				prepare.setInt(4, page * elements);
			}
			ResultSet rs = prepare.executeQuery();
			while (rs.next()) {
				computers.add(new Computer(rs.getLong("id"), rs.getString("name"), rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"),
						new Company(rs.getLong("company_id"), rs.getString("companyName"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	public int countByName(String computerName, String companyName) {
		int max = 0;
		String SQL;
		if ("".equals(companyName)) {
			SQL = SQL_COUNT_BY_NAME;
		} else {
			SQL = SQL_COUNT_BY_NAME_WITH_COMPANY_NAME;
		}
		try (Connection connection = ds.getConnection();
				PreparedStatement prepare = connection.prepareStatement(SQL)) {
			if ("".equals(companyName)) {
				prepare.setString(1, "%" + computerName + "%");
			} else {
				prepare.setString(1, "%" + companyName + "%");
				prepare.setString(2, "%" + computerName + "%");
			}
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