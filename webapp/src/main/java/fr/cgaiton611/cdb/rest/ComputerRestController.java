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
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.mapper.ComputerMapper;
import fr.cgaiton611.cdb.model.Computer;
import fr.cgaiton611.cdb.service.ComputerService;

@RestController
@RequestMapping("/computer")
public class ComputerRestController {

	private final Logger logger = LoggerFactory.getLogger(ComputerRestController.class);

	@Autowired
	ComputerService computerService;

	@Autowired
	private ComputerMapper computerMapper;

	@RequestMapping("getpage")
	@GetMapping
	public ResponseEntity<Object> findPage(@RequestParam(required = true, name = "page") int pPage,
			@RequestParam(required = true, name = "elements") int pElements,
			@RequestParam(required = false, name = "computerName", defaultValue = "") String pComputerName,
			@RequestParam(required = false, name = "companyName", defaultValue = "") String pCompanyName,
			@RequestParam(required = false, name = "orderByName", defaultValue = "cpu.name") String pOrderByName,
			@RequestParam(required = false, name = "orderByName", defaultValue = "ASC") String pOrderByOrder) {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = computerService.findPageWithParameters(pPage, pElements, pComputerName, pCompanyName,
					pOrderByName, pOrderByOrder);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTO(computer), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody ComputerDTO computerDTO) {
		Computer computer = computerMapper.toComputer(computerDTO);
		try {
			computer = computerService.create(computer);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTO(computer), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody ComputerDTO computerDTO) {
		Computer computer = computerMapper.toComputer(computerDTO);
		try {
			computer = computerService.update(computer);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(computerMapper.toComputerDTO(computer), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam(required = true, name = "id") int pId) {
		try {
			computerService.delete(pId);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Sucessfully deleted", HttpStatus.OK);
	}
	
}
