package fr.cgaiton611.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.util.ConvertUtil;

@WebServlet(urlPatterns = { "addComputer" })
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ComputerService computerService = ComputerService.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ressources/views/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("computerName");
		Optional<Date> introduced = request.getParameter("introduced");
		Optional<Date> discontinued = request.getParameter("discontinued");
		Optional<Long> companyId = ConvertUtilrequest.getParameter("companyId");
		if (name != null) {
			computerService.create(name, introduced, discontinued, companyId)
		}
		response.sendRedirect(request.getContextPath() + "/addComputer");
		
	}

}
