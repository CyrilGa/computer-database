package fr.cgaiton611.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.service.ComputerService;

@Service
public class ComputerByNamePage {

	@Autowired
	private ComputerService computerService;
	private int elements = 10;
	private int page = 0;
	private int max = 0;
	private String computerName = "";
	private String companyName = "";

	
	public int getMax() {
		return max;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page <= 0)
			this.page = 0;
		else if (page >= max)
			this.page = max;
		else
			this.page = page;
	}

	public List<Computer> next() {
		if (page >= max)
			page = max;
		List<Computer> computers = computerService.findByNamePaged(page, elements, computerName, companyName);
		page++;
		return computers;
	}

	public List<Computer> previous() {
		if (page <= 0)
			page = 0;
		List<Computer> computers = computerService.findByNamePaged(page, elements, computerName, companyName);
		page--;
		return computers;
	}

	public List<Computer> get() {
		return computerService.findByNamePaged(page, elements, computerName, companyName);
	}

	public void calculateMax() {
		max = (computerService.countByName(computerName, companyName) / elements);
	}

	public void setElements(int elements) {
		if (this.elements != elements) {
			page = 0;
			this.elements = elements;
			calculateMax();
		}
	}

	public void setComputerName(String computerName) {
		if (computerName != null) {
			if (!computerName.equals(this.computerName)) {
				page = 0;
				this.computerName = computerName;
				calculateMax();
			}
		}
	}

	public String getComputerName() {
		return computerName;
	}

	public void setCompanyName(String companyName) {
		if (companyName != null) {
			if (!companyName.equals(this.companyName)) {
				page = 0;
				this.companyName = companyName;
				calculateMax();
			}
		}
	}

	public String getCompanyName() {
		return companyName;
	}

}
