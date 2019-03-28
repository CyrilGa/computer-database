package fr.cgaiton611.cdb.page;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.Computer;
import fr.cgaiton611.cdb.service.ComputerService;

@Service
public class ComputerPage {

	private final Logger logger = LoggerFactory.getLogger(ComputerPage.class);

	private ComputerService computerService;

	public int[] ELEMENTS_AUTORISED = { 10, 50, 100 };
	public String[] ORDERBYNAME_AUTORISED = { "cpu.name", "cpu.introduced", "cpu.discontinued", "cpa.name" };
	public String[] ORDERBYORDER_AUTORISED = { "ASC", "DESC" };
	private int elements = ELEMENTS_AUTORISED[0];
	private int page = 0;
	private int max = 0;
	private String computerName = "";
	private String companyName = "";
	private String orderByName = ORDERBYNAME_AUTORISED[0];
	private String orderByOrder = ORDERBYORDER_AUTORISED[0];

	public ComputerPage(ComputerService computerService) {
		this.computerService = computerService;
		try {
			calculateMax();
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
	}

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

	public int getElements() {
		return elements;
	}

	public String getOrderByName() {
		return orderByName;
	}

	public String getOrderByOrder() {
		return orderByOrder;
	}

	public void setPage(int page) {
		this.page = page;
		if (this.page < 0)
			this.page = 0;
		else if (this.page > max)
			this.page = max;
	}

	public void setElements(int elements) {
		if (elementsIsValid(elements)) {
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

	public void setOrderByName(String orderByName) {
		if (orderByName != null) {
			if (orderByNameIsValid(orderByName)) {
				if (this.orderByName.equals(orderByName)) {
					invOrderByOrder();
				} else {
					orderByOrder = ORDERBYORDER_AUTORISED[0];
				}
				this.orderByName = orderByName;
				page = 0;
			}
		}
	}

	public void invOrderByOrder() {
		if (ORDERBYORDER_AUTORISED[0].equals(orderByOrder)) {
			orderByOrder = ORDERBYORDER_AUTORISED[1];
		} else {
			orderByOrder = ORDERBYORDER_AUTORISED[0];
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
		return computerService.findPageWithParameters(page, elements, computerName, companyName, orderByName,
				orderByOrder);
	}

	public void calculateMax() throws DAOException {
		max = (computerService.countWithParameters(computerName, companyName) / elements);
	}

	public boolean elementsIsValid(int test) {
		for (int i : ELEMENTS_AUTORISED) {
			if (i == test)
				return true;
		}
		return false;
	}

	public boolean orderByNameIsValid(String test) {
		for (String s : ORDERBYNAME_AUTORISED) {
			if (s.equals(test))
				return true;
		}
		return false;
	}

	public boolean orderByOrderIsValid(String test) {
		for (String s : ORDERBYORDER_AUTORISED) {
			if (s.equals(test))
				return true;
		}
		return false;
	}

}
