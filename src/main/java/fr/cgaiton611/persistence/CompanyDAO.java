package fr.cgaiton611.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.cgaiton611.model.Company;

/**
 * CRUD operations for entity Company
 * @author cyril
 * @version 1.0
 */
public class CompanyDAO extends DAO<Company>{

	@Override
	public Company create(Company obj) {
		try {
            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "INSERT INTO company(name) VALUES(?)"
                    , Statement.RETURN_GENERATED_KEYS);
            prepare.setString(1, obj.getName());
            
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
	public Company find(Company obj) {
        try {
            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "SELECT * FROM company WHERE id = ?"
                    );
            prepare.setLong(1, obj.getId());
            ResultSet rs = prepare.executeQuery();
            if(rs.first()) {
                obj = new Company(
                        obj.getId(),
                        rs.getString("name")
                );
            }
            else {
            	obj = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
	}

	@Override
	public Company update(Company obj) {
		try {
            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "UPDATE company SET name = ?"
                                    + " WHERE id = ? "
                    );
            prepare.setString(1, obj.getName());
            prepare.setLong(2, obj.getId());
            prepare.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
	}

	@Override
	public void delete(Company obj) {
		try {
			PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "DELETE FROM company WHERE id = ? "
                    );
           prepare.setLong(1, obj.getId());
           prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public List<Company> findPaged(int page, int elements) {
		List<Company> companies = null;
        try {
            PreparedStatement prepare = this.connection
                    .prepareStatement(
                            "SELECT * FROM company LIMIT ? OFFSET ? "
                    );
            prepare.setInt(1, elements);
            prepare.setInt(2, page*elements);
            ResultSet rs = prepare.executeQuery();
            companies = new ArrayList<>();
            while(rs.next()) {
                companies.add(new Company(
                        rs.getLong("id"),
                        rs.getString("name"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
	}

}
