package fr.cgaiton611.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.springconfig.SpringConfig;
import fr.cgaiton611.validation.ComputerValidator;

@WebServlet(urlPatterns = { "/addComputer" })
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
	private ComputerService computerService = context.getBean(ComputerService.class);
	private CompanyService companyService = context.getBean(CompanyService.class);
	private ComputerMapper computerMapper = context.getBean(ComputerMapper.class);
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> names = companyService.findAllName();
		request.setAttribute("names", names);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/resources/views/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		ComputerDTO computerDTO = new ComputerDTO(name, introduced, discontinued, companyName);
		Optional<Computer> computer = computerMapper.toComputer(computerDTO);
		String dashboardMsg;
		if (computer.isPresent()) {
			if (ComputerValidator.validateForAdd(computer.get())) {
				computer = computerService.create(computer.get());
				if (computer.isPresent())
					dashboardMsg = "Computer successfully created";
				else
					dashboardMsg = "Computer not created, bad entries (service)";
			}
			else {
				dashboardMsg = "Computer not created, bad entries (validator)";
			}
		} else {
			dashboardMsg = "Computer not created, bad entries (mapping)";
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("dashboardMsg", dashboardMsg);
		response.sendRedirect(request.getContextPath() + "/dashboard");

	}

}
