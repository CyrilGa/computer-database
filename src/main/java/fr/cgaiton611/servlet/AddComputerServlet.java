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

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.model.ComputerValidator;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;

@WebServlet(urlPatterns = { "/addComputer" })
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ComputerService computerService = ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> names = companyService.findAllName();
		request.setAttribute("names", names);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ressources/views/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		ComputerDTO computerDTO = new ComputerDTO(name, introduced, discontinued, companyId);
		Optional<Computer> computer = ComputerMapper.toComputer(computerDTO);
		String dashboardMsg;
		if (computer.isPresent() && ComputerValidator.validate(computer.orElse(null))) {
			computer = computerService.create(computer.get());
			if (computer.isPresent())
				dashboardMsg = "Computer successfully created";
			else
				dashboardMsg = "Computer not created, bad entries (service)";
		} else {
			dashboardMsg = "Computer not created, bad entries (mapping & validator)";
		}
		HttpSession session = request.getSession(false);
		session.setAttribute("dashboardMsg", dashboardMsg);
		response.sendRedirect(request.getContextPath() + "/dashboard");

	}

}
