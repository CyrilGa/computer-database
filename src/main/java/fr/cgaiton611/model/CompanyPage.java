package fr.cgaiton611.model;

import java.util.List;

import fr.cgaiton611.service.CompanyService;

public class CompanyPage {
	private CompanyService companyService = CompanyService.getInstance();
	private int elements = 15;
	private int page = -1;
	private int max = 0;

	public CompanyPage() {
		System.out.println("constructor");
		calculateMax();
	}

	public List<Company> next() {
		page++;
		if (page >= max)
			page = max;
		return companyService.findPaged(page, elements);
	}

	public List<Company> previous() {
		page--;
		if (page <= 0)
			page = 0;
		return companyService.findPaged(page, elements);
	}

	public void calculateMax() {
		System.out.println("dvev");
		max = (companyService.count() / elements);
	}
}
