package fr.cgaiton611.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.cgaiton611.cli.util.ConvertUtil;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;

/**
 * CRUD operations for entity Computer
 * 
 * @author cyril
 * @version 1.0
 */
public class ComputerDAO extends DAO<Computer> {
	
	private ConvertUtil convertUtil = new ConvertUtil();

	private static final String SQL_CREATE = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)";
	private static final String SQL_FIND = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ? ,introduced = ? ,discontinued = ? ,company_id = ?  WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ? ";
	private static final String SQL_FIND_PAGED = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT = "SELECT COUNT(*) as count FROM computer";
	private static final String SQL_FIND_BY_NAME_PAGED = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name LIKE ? LIMIT ? OFFSET ? ";
	private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) as count FROM computer WHERE name LIKE ? ";

	
	private static ComputerDAO instance = new ComputerDAO();

	private ComputerDAO() {
	};

	public static ComputerDAO getInstance() {
		return instance;
	}

	@Override
	public Optional<Computer> create(Computer obj) {
		Computer computer = null;
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_CREATE,
				Statement.RETURN_GENERATED_KEYS)) {
			prepare.setString(1, obj.getName());
			prepare.setTimestamp(2, convertUtil.DateToTimestamp(obj.getIntroduced()));
			prepare.setTimestamp(3, convertUtil.DateToTimestamp(obj.getDiscontinued()));
			prepare.setLong(4, obj.getCompany().getId());

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
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_FIND)) {
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
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_UPDATE)) {
			prepare.setString(1, obj.getName());
			prepare.setTimestamp(2, convertUtil.DateToTimestamp(obj.getIntroduced()));
			prepare.setTimestamp(3, convertUtil.DateToTimestamp(obj.getDiscontinued()));
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
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_DELETE)) {
			prepare.setLong(1, obj.getId());
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Computer> findPaged(int page, int elements) {
		List<Computer> computers = new ArrayList<>();
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_FIND_PAGED)) {
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
	
	public List<Computer> findByNamePaged(int page, int elements, String name) {
		List<Computer> computers = new ArrayList<>();
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_FIND_BY_NAME_PAGED)) {
			prepare.setString(1, "%"+name+"%");
			prepare.setInt(2, elements);
			prepare.setInt(3, page * elements);
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
	
	public int countByName(String name) {
		int max = 0;
		try (PreparedStatement prepare = this.connection.prepareStatement(SQL_COUNT_BY_NAME)) {
			prepare.setString(1, "%"+name+"%");
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