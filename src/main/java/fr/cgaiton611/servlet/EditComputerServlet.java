package fr.cgaiton611.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.NoRowUpdatedException;
import fr.cgaiton611.exception.validation.ValidationException;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.validation.ComputerValidator;

@Controller
@RequestMapping("/editComputer")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;

	@GetMapping
	public String doGet(Model model) {

		List<String> names = new ArrayList<>();
		try {
			names = companyService.findAllName();
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		request.setAttribute("names", names);

		String id = (String) request.getParameter("computerId");
		request.setAttribute("id", id);

		Computer computer;
		try {
			computer = computerService.find(Long.parseLong(id));
		} catch (NumberFormatException | DAOException e) {
			logger.warn(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/dashboard");
			return;
		}

		ComputerDTO computerDTO = computerMapper.toComputerDTO(computer);
		request.setAttribute("name", computerDTO.getName());

		String introduced = computerDTO.getIntroduced();
		if (introduced != null && !"".equals(introduced)) {
			request.setAttribute("introducedDate", introduced.substring(0, 10));
			request.setAttribute("introducedTime", introduced.substring(11));
		}
		String discontinued = computerDTO.getDiscontinued();
		if (discontinued != null && !"".equals(discontinued)) {
			request.setAttribute("discontinuedDate", discontinued.substring(0, 10));
			request.setAttribute("discontinuedTime", discontinued.substring(11));
		}
		request.setAttribute("companyName", computerDTO.getCompanyName());

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/resources/views/editComputer.jsp");
		dispatcher.forward(request, response);
	}

	@PostMapping
	public RedirectView doPost(@RequestParam(required = false, name = "computerName") String pId,
			@RequestParam(required = false, name = "computerName") String pComputerName,
			@RequestParam(required = false, name = "introducedDate") String pIntroducedDate,
			@RequestParam(required = false, name = "introducedTime") String pIntroducedTime,
			@RequestParam(required = false, name = "discontinuedDate") String pDiscontinuedDate,
			@RequestParam(required = false, name = "discontinuedTime") String pDiscontinuedTime,
			@RequestParam(required = false, name = "companyName") String pCompanyName,
			RedirectAttributes redirectAttributes) {
		String introduced = pIntroducedDate + " " + pIntroducedTime;
		String discontinued = pDiscontinuedDate + " " + pDiscontinuedTime;
		if ("select-option-default".equals(pCompanyName))
			pCompanyName = null;
		ComputerDTO computerDTO = new ComputerDTO(pId, pComputerName, introduced, discontinued, pCompanyName);
		String dashboardMsg;
		Computer computer = computerMapper.toComputer(computerDTO);
		try {
			ComputerValidator.validateForEdit(computer);
			try {
				computerService.update(computer);
				dashboardMsg = "Computer successfully updated";
			} catch (NoRowUpdatedException e) {
				logger.warn(e.getMessage());
				dashboardMsg = "Computer not updated, computer not found";
			} catch (DAOException e) {
				logger.warn(e.getMessage());
				dashboardMsg = "Computer not updated, database not accessible";
			}
		} catch (ValidationException e) {
			logger.warn(e.getMessage());
			dashboardMsg = "Computer not updated, bad validation";
		}

		redirectAttributes.addAttribute("dashboardMsg", dashboardMsg);
		return new RedirectView("dashboard");

	}
}
