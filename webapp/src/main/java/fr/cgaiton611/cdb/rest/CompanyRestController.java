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

import fr.cgaiton611.cdb.dto.CompanyDTO;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.mapper.CompanyMapper;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyRestController {

	private final Logger logger = LoggerFactory.getLogger(CompanyRestController.class);

	@Autowired
	CompanyService companyService;

	@Autowired
	private CompanyMapper companyMapper;

	@RequestMapping("/all")
	@GetMapping
	public ResponseEntity<Object> findPage(@RequestParam(required = true, name = "page") int pPage,
			@RequestParam(required = true, name = "elements") int pElements) {
		List<Company> companies = new ArrayList<>();
		try {
			companies = companyService.findPage(pPage, pElements);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(companyMapper.toCompanyDTOList(companies), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Object> find(@RequestParam(required = true, name = "id") int pId) {
		Company company = null;
		try {
			company = companyService.find(new Company(pId));
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(companyMapper.toCompanyDTO(company), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody CompanyDTO companyDTO) {
		Company company = companyMapper.toCompany(companyDTO);
		try {
			company = companyService.create(company);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(companyMapper.toCompanyDTO(company), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody CompanyDTO companyDTO) {
		Company company = companyMapper.toCompany(companyDTO);
		try {
			company = companyService.update(company);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(companyMapper.toCompanyDTO(company), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam(required = true, name = "id") int pId) {
		try {
			companyService.delete(pId);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Sucessfully deleted", HttpStatus.OK);
	}
	
}
