package fr.cgaiton611.servlet;

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

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerForm;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.validation.ValidationException;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.validation.ComputerValidator;

@Controller
@RequestMapping("/addComputer")
public class AddComputerServlet {

	private final Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;

	@GetMapping
	public String doGet(@RequestParam(required = false, name = "errorMsgs") List<String> errorMsgs, Model model) {

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
		Computer computer = computerMapper.toComputer(computerDTO);
		logger.debug(computer.toString());
		try {
			ComputerValidator.validateForAdd(computer);
			try {
				Computer computerNew = computerService.create(computer);
				dashboardMsg = "Computer successfully created, id: " + computerNew.getId();
			} catch (DAOException e) {
				logger.warn(e.getMessage());
				dashboardMsg = "Computer not updated, database not accessible";
			}
		} catch (ValidationException e) {
			logger.warn(e.getMessage());
			dashboardMsg = "Computer not updated, bad validation";
		}
		redirectAttributes.addAttribute("dashboardMsg", dashboardMsg);

		return "redirect:dashboard";
	}

//	@PostMapping
//	public String doPost(@RequestParam(required = false, name = "computerName") String pComputerName,
//			@RequestParam(required = false, name = "introducedDate") String pIntroducedDate,
//			@RequestParam(required = false, name = "introducedTime") String pIntroducedTime,
//			@RequestParam(required = false, name = "discontinuedDate") String pDiscontinuedDate,
//			@RequestParam(required = false, name = "discontinuedTime") String pDiscontinuedTime,
//			@RequestParam(required = false, name = "companyName") String pCompanyName,
//			RedirectAttributes redirectAttributes) {
//		String introduced = pIntroducedDate + " " + pIntroducedTime;
//		String discontinued = pDiscontinuedDate + " " + pDiscontinuedTime;
//		if ("select-option-default".equals(pCompanyName))
//			pCompanyName = null;
//		ComputerDTO computerDTO = new ComputerDTO(pComputerName, introduced, discontinued, pCompanyName);
//		String dashboardMsg;
//
//		Computer computer = computerMapper.toComputer(computerDTO);
//		try {
//			ComputerValidator.validateForAdd(computer);
//			try {
//				Computer computerNew = computerService.create(computer);
//				dashboardMsg = "Computer successfully created, id: " + computerNew.getId();
//			} catch (DAOException e) {
//				logger.warn(e.getMessage());
//				dashboardMsg = "Computer not updated, database not accessible";
//			}
//		} catch (ValidationException e) {
//			logger.warn(e.getMessage());
//			dashboardMsg = "Computer not updated, bad validation";
//		}
//		redirectAttributes.addAttribute("dashboardMsg", dashboardMsg);
//		return "redirect:dashboard";
//	}

}
