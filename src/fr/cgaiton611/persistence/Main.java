package fr.cgaiton611.persistence;

import fr.cgaiton611.model.Company;

public class Main {
	public static void main(String[] args) {
		System.out.println("salut !");
		CompanyDAO companyDAO = new CompanyDAO();
		Company company = companyDAO.find(new Company(3));
		System.out.println(company.toString());
	}
}
