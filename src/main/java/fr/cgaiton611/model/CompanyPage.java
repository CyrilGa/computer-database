package fr.cgaiton611.model;

import java.util.ArrayList;
import java.util.List;

import fr.cgaiton611.service.CompanyService;

public class CompanyPage {
	private CompanyService companyService  = CompanyService.getInstance();
	private int elements = 15;
	private int page = 0;
	private int max = 0;
	
	public CompanyPage() {
		calculateMax();
	}
	
	public List<Company> next() {
		if (page == max) {
			return new ArrayList<Company>();
		}
		else {
			return companyService.findPaged(page, elements);
		}
	}
	
	public List<Company> previous() {
		if (page == 0) {
			return new ArrayList<Company>();
		}
		else {
			return companyService.findPaged(page, elements);
		}
	}
	
	public void calculateMax() {
		max = (companyService.count()/elements) + 1;
	}
}
