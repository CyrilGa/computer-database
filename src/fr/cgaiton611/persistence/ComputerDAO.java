package fr.cgaiton611.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.cgaiton611.model.Computer;


public class ComputerDAO extends DAO<Computer>{
	
	@Override
	public Computer create(Computer obj) {
		try {
            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);
            prepare.setString(1, obj.getName());
            prepare.setTimestamp(2, obj.getIntroduced());
            prepare.setTimestamp(3, obj.getDiscontinued());
            prepare.setLong(4, obj.getCompany_id());
            
            prepare.executeUpdate();
            
            ResultSet rs = prepare.getGeneratedKeys();
            if (rs.next()){
               int generated_id = rs.getInt(1);
               obj.setId(generated_id);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
	}

	@Override
	public Computer find(Computer obj) {
        try {
            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "SELECT * FROM computer WHERE id = ?"
                    );
            prepare.setLong(1, obj.getId());
            ResultSet rs = prepare.executeQuery();
            if(rs.first()) {
                obj = new Computer(
                        obj.getId(),
                        rs.getString("name"),
                        rs.getTimestamp("introduced"),
                        rs.getTimestamp("discontinued"),
                        rs.getLong("company_id")
                );
            }
            else {
            	obj= null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
	}

	@Override
	public Computer update(Computer obj) {
		try {
            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "UPDATE computer SET name = ? ,"
                    			+ "introduced = ? ,"
                            	+ "discontinued = ? ,"
                    			+ "company_id = ? "
                    			+ " WHERE id = ? "
                    );
            prepare.setString(1, obj.getName());
            prepare.setTimestamp(2, obj.getIntroduced());
            prepare.setTimestamp(3, obj.getDiscontinued());
            prepare.setLong(4, obj.getCompany_id());
            prepare.setLong(5, obj.getId());
            prepare.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
	}

	@Override
	public void delete(Computer obj) {
		try {

            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "DELETE FROM computer WHERE id = ? "
                    );
           prepare.setLong(1, obj.getId());
           prepare.executeUpdate();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public List<Computer> find_all(int l1, int l2) {
		List<Computer> computers = null;
        try {
            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "SELECT * FROM computer LIMIT ?, ? "
                    );
            prepare.setInt(1, l1);
            prepare.setInt(2, l2);
            ResultSet rs = prepare.executeQuery();
            computers = new ArrayList<>();
            while(rs.next()) {
                computers.add(new Computer(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getTimestamp("introduced"),
                        rs.getTimestamp("discontinued"),
                        rs.getLong("company_id"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computers;
	}
	

}