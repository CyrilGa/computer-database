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
import fr.cgaiton611.cdb.error.ErrorModel.ErrorModelBuilder;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.MappingException;
import fr.cgaiton611.cdb.exception.entityValidation.EntityValidationException;
import fr.cgaiton611.cdb.mapper.CompanyMapper;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.service.CompanyService;
import fr.cgaiton611.cdb.webentity.GetAllParametersEntity;
import fr.cgaiton611.cdb.webentity.GetAllParametersEntityValidator;

@RestController
@RequestMapping("/company")
public class CompanyRestController {

	private final Logger logger = LoggerFactory.getLogger(CompanyRestController.class);

	@Autowired
	CompanyService companyService;

	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private GetAllParametersEntityValidator entityValidator;

	@RequestMapping("/all")
	@GetMapping
	public ResponseEntity<Object> findPage(@RequestBody GetAllParametersEntity entity) {
		List<Company> companies = new ArrayList<>();
		try {
			entityValidator.validate(entity);
			companies = companyService.findPage(entity);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		} catch (EntityValidationException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.FAILED_DEPENDENCY.toString()).setMessage(e.getMessage()).build(), HttpStatus.FAILED_DEPENDENCY);
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
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(companyMapper.toCompanyDTO(company), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody CompanyDTO companyDTO) {
		Company company;
		try {
			company = companyMapper.toCompany(companyDTO);
			company = companyService.create(company);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		} catch (MappingException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.FAILED_DEPENDENCY.toString()).setMessage(e.getMessage()).build(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(companyMapper.toCompanyDTO(company), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody CompanyDTO companyDTO) {
		Company company;
		try {
			company = companyMapper.toCompany(companyDTO);
			company = companyService.update(company);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		} catch (MappingException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.FAILED_DEPENDENCY.toString()).setMessage(e.getMessage()).build(), HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(companyMapper.toCompanyDTO(company), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam(required = true, name = "id") int pId) {
		try {
			companyService.delete(pId);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<>(new ErrorModelBuilder().setHttpCode(HttpStatus.BAD_REQUEST.toString()).setMessage(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Sucessfully deleted", HttpStatus.OK);
	}
	
}
