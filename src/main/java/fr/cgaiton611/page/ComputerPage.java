package fr.cgaiton611.page;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.exception.DAOException;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.ComputerService;

@Service
public class ComputerPage {

	private final Logger logger = LoggerFactory.getLogger(ComputerPage.class);
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

	public String getComputerName() {
		return computerName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setPage(int page) {
		this.page = page;
		if (this.page <= 0)
			this.page = 0;
		else if (this.page >= max)
			this.page = max;
	}

	public void setElements(int elements) {
		if (this.elements != elements) {
			page = 0;
			this.elements = elements;
			try {
				calculateMax();
			} catch (DAOException e) {
				logger.warn(e.getMessage());
			}
		}
	}

	public void setComputerName(String computerName) {
		if (computerName != null) {
			if (!computerName.equals(this.computerName)) {
				page = 0;
				this.computerName = computerName;
				try {
					calculateMax();
				} catch (DAOException e) {
					logger.warn(e.getMessage());
				}
			}
		}
	}

	public void setCompanyName(String companyName) {
		if (companyName != null) {
			if (!companyName.equals(this.companyName)) {
				page = 0;
				this.companyName = companyName;
				try {
					calculateMax();
				} catch (DAOException e) {
					logger.warn(e.getMessage());
				}
			}
		}
	}

	public void pageInc() {
		page++;
		if (page >= max)
			page = max;
	}

	public void pageDec() {
		page--;
		if (page <= 0)
			page = 0;
	}

	public List<Computer> getCurrent() throws DAOException {
		return computerService.findPageWithParameters(page, elements, computerName, companyName);
	}

	public void calculateMax() throws DAOException {
		max = (computerService.countWithParameters(computerName, companyName) / elements);
	}

}
