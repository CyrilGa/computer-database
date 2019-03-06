package fr.cgaiton611.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.service.CompanyService;

@Service
public class CompanyPage {
	@Autowired
	private CompanyService companyService;
	private int elements = 15;
	private int page = -1;
	private int max = 0;


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
		max = (companyService.count() / elements);
	}
	
	public void init() {
		elements = 15;
		page = -1;
		max = 0;
		calculateMax();
	}
}
