package fr.cgaiton611.model;

import java.util.List;

import fr.cgaiton611.service.ComputerService;

public class ComputerPage {
	private ComputerService computerService = ComputerService.getInstance();
	private int elements = 15;
	private int page = -1;
	private int max = 0;

	public int getMax() {
		return max;
	}

	public int getPage() {
		return page;
	}

	public ComputerPage(int elements) {
		this.elements = elements;
		calculateMax();
	}

	public List<Computer> next() {
		page++;
		if (page >= max)
			page = max;
		return computerService.findPaged(page, elements);
	}

	public List<Computer> previous() {
		page--;
		if (page <= 0)
			page = 0;
		return computerService.findPaged(page, elements);
	}

	public List<Computer> get(int page) {
		if (page <= 0)
			page = 0;
		else if (page >= max)
			page = max;
		return computerService.findPaged(page, elements);
	}

	public void calculateMax() {
		max = (computerService.count() / elements);
	}

	public void setElements(int elements) {
		if (this.elements != elements) {
			page = 0;
		}
		this.elements = elements;
		calculateMax();
	}

}
