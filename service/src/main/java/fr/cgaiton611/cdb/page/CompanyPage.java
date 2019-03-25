package fr.cgaiton611.cdb.page;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.service.CompanyService;

@Service
public class CompanyPage {

	private final Logger logger = LoggerFactory.getLogger(CompanyPage.class);
	@Autowired
	private CompanyService companyService;
	private int elements = 10;
	private int page = 0;
	private int max = 0;

	public int getMax() {
		return max;
	}

	public int getPage() {
		return page;
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

	public List<Company> getCurrent() throws DAOException {
		return companyService.findPage(page, elements);
	}

	public void calculateMax() throws DAOException {
		max = (companyService.count() / elements);
	}

}
