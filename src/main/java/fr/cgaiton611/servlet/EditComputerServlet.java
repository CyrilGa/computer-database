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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.EmptyResultSetException;
import fr.cgaiton611.exception.mapping.MappingException;
import fr.cgaiton611.exception.validation.ValidationException;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.validation.ComputerValidator;

@WebServlet(urlPatterns = { "/editComputer" })
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("computerName");
		String introducedDate = request.getParameter("introducedDate");
		String introducedTime = request.getParameter("introducedTime");
		String introduced = introducedDate + " " + introducedTime;
		String discontinuedDate = request.getParameter("discontinuedDate");
		String discontinuedTime = request.getParameter("discontinuedTime");
		String discontinued = discontinuedDate + " " + discontinuedTime;
		String companyName = request.getParameter("companyName");
		if ("select-option-default".equals(companyName))
			companyName = null;
		ComputerDTO computerDTO = new ComputerDTO(id, name, introduced, discontinued, companyName);
		String dashboardMsg;

		try {
			Computer computer = computerMapper.toComputer(computerDTO);
			try {
				ComputerValidator.validateForEdit(computer);
				try {
					computerService.update(computer);
					dashboardMsg = "Computer successfully updated";
				} catch (EmptyResultSetException e) {
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
		} catch (MappingException e) {
			logger.warn(e.getMessage());
			dashboardMsg = "Computer not updated, bad mapping";
		}

		HttpSession session = request.getSession(true);
		session.setAttribute("dashboardMsg", dashboardMsg);
		response.sendRedirect(request.getContextPath() + "/dashboard");

	}

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
}
