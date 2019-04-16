package fr.cgaiton611.cdb.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.cgaiton611.cdb.dto.ComputerDTO;
import fr.cgaiton611.cdb.error.ErrorModel.ErrorModelBuilder;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.MappingException;
import fr.cgaiton611.cdb.exception.entityValidation.EntityValidationException;
import fr.cgaiton611.cdb.mapper.ComputerMapper;
import fr.cgaiton611.cdb.model.Computer;
import fr.cgaiton611.cdb.service.ComputerService;
import fr.cgaiton611.cdb.webentity.GetAllParametersEntity;
import fr.cgaiton611.cdb.webentity.GetAllParametersEntityValidator;

@RestController
@RequestMapping("/computer")
public class ComputerRestController {

	private final Logger logger = LoggerFactory.getLogger(ComputerRestController.class);

	@Autowired
	ComputerService computerService;

	@Autowired
	private ComputerMapper computerMapper;
	
	@Autowired
	private GetAllParametersEntityValidator entityValidator;

	@RequestMapping("/all")
	@GetMapping
	public ResponseEntity<Object> findPage(@RequestBody GetAllParametersEntity entity) {
		List<Computer> computers = new ArrayList<>();
		try {
			entityValidator.validate(entity);
			entity.translateOrderAttribute();
			computers = computerService.findPageWithParameters(entity);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		} catch (EntityValidationException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.FAILED_DEPENDENCY.toString()).setMessage(e.getMessage()).build(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTOList(computers), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Object> find(@RequestParam(required = true, name = "id") int pId) {
		Computer computer = null;
		try {
			computer = computerService.find(new Computer(pId));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
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
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		} catch (MappingException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.FAILED_DEPENDENCY.toString()).setMessage(e.getMessage()).build(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTO(computer), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody ComputerDTO computerDTO) {
		System.out.println(computerDTO);
		Computer computer;
		try {
			computer = computerMapper.toComputer(computerDTO);
			System.out.println(computer);
			computer = computerService.update(computer);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		} catch (MappingException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.FAILED_DEPENDENCY.toString()).setMessage(e.getMessage()).build(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTO(computer), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam(required = true, name = "id") int pId) {
		try {
			computerService.delete(pId);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Sucessfully deleted", HttpStatus.OK);
	}

}
