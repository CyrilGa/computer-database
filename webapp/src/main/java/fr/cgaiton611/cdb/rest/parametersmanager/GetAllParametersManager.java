package fr.cgaiton611.cdb.rest.parametersmanager;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.entityValidation.DatabaseErrorValidationException;
import fr.cgaiton611.cdb.exception.entityValidation.EntityValidationException;
import fr.cgaiton611.cdb.exception.entityValidation.NbElementsNotValidException;
import fr.cgaiton611.cdb.exception.entityValidation.NumPageNotValidException;
import fr.cgaiton611.cdb.exception.entityValidation.OrderAttributeNotValidException;
import fr.cgaiton611.cdb.exception.entityValidation.OrderTypeNotValidException;
import fr.cgaiton611.cdb.service.CompanyService;
import fr.cgaiton611.cdb.service.ComputerService;;

@Component
public class GetAllParametersManager {

	@Autowired
	ComputerService computerService;

	@Autowired
	CompanyService companyService;

	private final List<Integer> NBELEMENTS_AUTORISED = Arrays.asList(10, 25, 50, 75, 100);
	private final List<String> ORDERTYPE_AUTORISED = Arrays.asList("ASC", "DESC");
	private static final List<String> ORDER_ATTRIBUTES_AUTORISED = Arrays.asList("id", "computerName", "introduced",
			"discontinued", "companyName");;
	private static final List<String> ORDER_ATTRIBUTES_AUTORISED_TRANSLATED = Arrays.asList("cpu.id", "cpu.name",
			"cpu.introduced", "cpu.discontinued", "cpa.name");
	
	
	public String translateOrderAttribute(String orderAttribute) {
		return ORDER_ATTRIBUTES_AUTORISED_TRANSLATED.get(ORDER_ATTRIBUTES_AUTORISED.indexOf(orderAttribute));
	}

	public void validateForComputer(int numPage, int nbElements, String computerName, String companyName,
			String orderAttribute, String orderType) throws EntityValidationException {
		nbElementsValidator(nbElements);
		orderAttributeValidator(orderAttribute);
		orderTypeValidator(orderType);
		numPageValidatorComputer(numPage, nbElements, computerName, companyName);
	}

	public void validateForCompany(int numPage, int nbElements) throws EntityValidationException {
		nbElementsValidator(nbElements);
		numPageValidatorCompany(numPage, nbElements);
	}

	private void nbElementsValidator(int test) throws NbElementsNotValidException {
		if (!NBELEMENTS_AUTORISED.contains(test)) {
			throw new NbElementsNotValidException();
		}
	}

	private void orderAttributeValidator(String test) throws OrderAttributeNotValidException {
		if (!ORDER_ATTRIBUTES_AUTORISED.contains(test)) {
			throw new OrderAttributeNotValidException();
		}
	}

	private void orderTypeValidator(String test) throws OrderTypeNotValidException {
		if (!ORDERTYPE_AUTORISED.contains(test)) {
			throw new OrderTypeNotValidException();
		}
	}

	private void numPageValidatorComputer(int numPage, int nbElements, String computerName, String companyName)
			throws EntityValidationException {
		int maxPage;
		try {
			maxPage = getMaxPage(computerName, companyName, nbElements);
		} catch (DAOException e) {
			throw new DatabaseErrorValidationException();
		}
		if (numPage < 0 || numPage > maxPage) {
			throw new NumPageNotValidException(0, maxPage);
		}
	}

	private void numPageValidatorCompany(int numPage, int nbElements) throws EntityValidationException {
		int maxPage;
		try {
			maxPage = (companyService.count() / nbElements);
		} catch (DAOException e) {
			throw new DatabaseErrorValidationException();
		}
		if (numPage < 0 || numPage > maxPage) {
			throw new NumPageNotValidException(0, maxPage);
		}
	}
	
	public int getMaxPage(String computerName, String companyName, int nbElements) throws DAOException {
	  return (computerService.countWithParameters(computerName, companyName) / nbElements);
	}

}
