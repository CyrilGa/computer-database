package fr.cgaiton611.cdb.webentity;

import static fr.cgaiton611.cdb.webentity.GetAllParametersEntity.ORDER_ATTRIBUTES_AUTORISED;

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
import fr.cgaiton611.cdb.service.ComputerService;;

@Component
public class GetAllParametersEntityValidator {

	@Autowired
	ComputerService computerService;

	private final List<Integer> NBELEMENTS_AUTORISED = Arrays.asList(10, 25, 50, 75, 100);
	private final List<String> ORDERTYPE_AUTORISED = Arrays.asList("ASC", "DESC");

	public void validate(GetAllParametersEntity entity) throws EntityValidationException {
		nbElementsValidator(entity.getNbElements());
		orderAttributeValidator(entity.getOrderAttribute());
		orderTypeValidator(entity.getOrderType());
		numPageValidator(entity);
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

	private void numPageValidator(GetAllParametersEntity entity) throws EntityValidationException {
		int maxPage;
		try {
			maxPage = (computerService.countWithParameters(entity.getComputerName(), entity.getCompanyName())
					/ entity.getNbElements());
		} catch (DAOException e) {
			throw new DatabaseErrorValidationException();
		}
		if (entity.getNumPage() < 0 || entity.getNumPage() > maxPage) {
			throw new NumPageNotValidException(0, maxPage);
		}
	}

}
