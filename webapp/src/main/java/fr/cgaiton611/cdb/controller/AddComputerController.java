package fr.cgaiton611.cdb.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.cgaiton611.cdb.dto.ComputerDTO;
import fr.cgaiton611.cdb.dto.ComputerForm;
import fr.cgaiton611.cdb.mapper.ComputerMapper;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.MappingException;
import fr.cgaiton611.cdb.exception.validation.ValidationException;
import fr.cgaiton611.cdb.model.Computer;
import fr.cgaiton611.cdb.service.CompanyService;
import fr.cgaiton611.cdb.service.ComputerService;
import fr.cgaiton611.cdb.validation.ComputerValidator;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {

	private final Logger logger = LoggerFactory.getLogger(AddComputerController.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;

	@GetMapping
	public String doGet(@RequestParam(required = false, name = "errorMsgs") List<String> errorMsgs, Model model,
			Principal principal) {

		if (principal != null) {
			model.addAttribute("username", principal.getName());
		}

		List<String> names = new ArrayList<>();
		try {
			names = companyService.findAllName();
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		model.addAttribute("names", names);

		model.addAttribute("errorMsgs", errorMsgs);

		return "addComputer";
	}

	@ModelAttribute("computerForm")
	public ComputerForm getComputerForm() {
		return new ComputerForm();
	}

	@PostMapping
	public String doPost(@ModelAttribute("computerForm") @Valid ComputerForm computerForm, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (FieldError error : result.getFieldErrors()) {
				errors.add(error.getField() + " - " + error.getDefaultMessage());
			}
			for (ObjectError error : result.getGlobalErrors()) {
				errors.add(error.getObjectName() + " - " + error.getDefaultMessage());
			}
			redirectAttributes.addAttribute("errorMsgs", errors);
			return "redirect:addComputer";
		}

		String dashboardMsg;

		ComputerDTO computerDTO = new ComputerDTO(computerForm);
		logger.debug(computerDTO.toString());
		try {
			Computer computer = computerMapper.toComputer(computerDTO);
			logger.debug(computer.toString());
			ComputerValidator.validateForAdd(computer);
			try {
				Computer computerNew = computerService.create(computer);
				dashboardMsg = "Computer successfully created, id: " + computerNew.getId();
			} catch (DAOException e) {
				logger.warn(e.getMessage());
				dashboardMsg = "Computer not updated, database not accessible";
			}
		} catch (ValidationException | MappingException e) {
			logger.warn(e.getMessage());
			dashboardMsg = "Computer not updated, bad validation";
		}
		redirectAttributes.addAttribute("dashboardMsg", dashboardMsg);

		return "redirect:dashboard";
	}

}
