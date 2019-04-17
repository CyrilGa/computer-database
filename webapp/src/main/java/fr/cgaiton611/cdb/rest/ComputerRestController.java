package fr.cgaiton611.cdb.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.cgaiton611.cdb.dto.ComputerDTO;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.MappingException;
import fr.cgaiton611.cdb.exception.entityValidation.EntityValidationException;
import fr.cgaiton611.cdb.mapper.ComputerMapper;
import fr.cgaiton611.cdb.model.Computer;
import fr.cgaiton611.cdb.rest.parametersmanager.GetAllParametersManager;
import fr.cgaiton611.cdb.service.ComputerService;

@RestController
@RequestMapping("/api/v1/computers")
public class ComputerRestController {

	private final Logger logger = LoggerFactory.getLogger(ComputerRestController.class);

	private ComputerService computerService;
	private ComputerMapper computerMapper;
	private GetAllParametersManager getAllParametersManager;
	
	public ComputerRestController(ComputerService computerService, ComputerMapper computerMapper, GetAllParametersManager getAllParametersManager) {
	  this.computerService = computerService;
	  this.computerMapper = computerMapper;
	  this.getAllParametersManager = getAllParametersManager;
	}

	@GetMapping
	public ResponseEntity<Object> findPage(@RequestParam(required = false, defaultValue = "0") int numPage,
			@RequestParam(required = false, defaultValue = "10") int nbElements,
			@RequestParam(required = false, defaultValue = "") String computerName,
			@RequestParam(required = false, defaultValue = "") String companyName,
			@RequestParam(required = false, defaultValue = "id") String orderAttribute,
			@RequestParam(required = false, defaultValue = "ASC") String orderType) {
		List<Computer> computers = new ArrayList<>();
		try {
			getAllParametersManager.validateForComputer(numPage, nbElements, computerName, companyName, orderAttribute,
					orderType);
			orderAttribute = getAllParametersManager.translateOrderAttribute(orderAttribute);
			computers = computerService.findPageWithParameters(numPage, nbElements, computerName, companyName, orderAttribute,
					orderType);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (EntityValidationException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTOList(computers), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> find(@PathVariable("id") int pId) {
		Computer computer = null;
		try {
			computer = computerService.find(new Computer(pId));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTO(computer), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody ComputerDTO computerDTO) {
		Computer computer;
		try {
			computer = computerMapper.toComputer(computerDTO);
			computer = computerService.create(computer);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MappingException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTO(computer), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody ComputerDTO computerDTO) {
		Computer computer;
		try {
			computer = computerMapper.toComputer(computerDTO);
			computer = computerService.update(computer);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (MappingException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTO(computer), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") int pId) {
		try {
			computerService.delete(pId);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Sucessfully deleted", HttpStatus.OK);
	}

}
