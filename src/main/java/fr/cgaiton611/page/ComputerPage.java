package fr.cgaiton611.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.ComputerService;

@Service
public class ComputerPage {
	@Autowired
	private ComputerService computerService;
	private int elements = 15;
	private int page = -1;
	private int max = 0;

	public List<Computer> next() {
		System.out.println(max);
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

	public void calculateMax() {
		max = (computerService.count() / elements);
	}

	public void init() {
		elements = 15;
		page = -1;
		max = 0;
		calculateMax();
	}

}
