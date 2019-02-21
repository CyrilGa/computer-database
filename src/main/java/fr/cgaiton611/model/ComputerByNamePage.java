package fr.cgaiton611.model;

import java.util.List;

import fr.cgaiton611.service.ComputerService;

public class ComputerByNamePage {

	private ComputerService computerService = ComputerService.getInstance();
	private int elements = 15;
	private int page = -1;
	private int max = 0;
	private String name;

	public int getMax() {
		return max;
	}

	public int getPage() {
		return page;
	}

	public ComputerByNamePage(int elements, String name) {
		this.elements = elements;
		this.name = name;
		calculateMax();
	}

	public List<Computer> next() {
		page++;
		if (page >= max)
			page = max;
		return computerService.findByNamePaged(page, elements, name);
	}

	public List<Computer> previous() {
		page--;
		if (page <= 0)
			page = 0;
		return computerService.findByNamePaged(page, elements, name);
	}

	public List<Computer> get(int ppage) {
		page = ppage;
		if (page <= 0)
			page = 0;
		else if (page >= max)
			page = max;
		return computerService.findByNamePaged(page, elements, name);
	}

	public void calculateMax() {
		max = (computerService.countByName(name) / elements);
	}

	public void setElements(int elements) {
		if (this.elements != elements) {
			page = -1;
			this.elements = elements;
			calculateMax();
		}
	}

	public void setName(String name) {
		if (name != null) {
			if (this.name != name) {
				page = -1;
				this.name = name;
				calculateMax();
			}
		}
	}

	public String getName() {
		return name;
	}
	

}
