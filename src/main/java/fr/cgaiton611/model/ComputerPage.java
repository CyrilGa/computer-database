package fr.cgaiton611.model;

import java.util.ArrayList;
import java.util.List;

import fr.cgaiton611.service.ComputerService;

public class ComputerPage {
	private ComputerService computerService  = ComputerService.getInstance();
	private int elements = 15;
	private int page = 0;
	private int max = -1;
	
	public ComputerPage() {
		calculateMax();
	}
	
	public List<Computer> next() {
		if (page >= max) {
			return new ArrayList<Computer>();
		}
		else {
			return computerService.findPaged(page, elements);
		}
	}
	
	public List<Computer> previous() {
		if (page <= 0) {
			return new ArrayList<Computer>();
		}
		else {
			return computerService.findPaged(page, elements);
		}
	}
	
	public void calculateMax() {
		max = (computerService.count()/elements) + 1;
	}
}
