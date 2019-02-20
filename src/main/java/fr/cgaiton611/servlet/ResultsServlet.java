package fr.cgaiton611.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.ComputerService;

public class ResultsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private ComputerService computerService = ComputerService.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("scearch");
		if (name == null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/dashboard.jsp");
			dispatcher.forward(request, response);
		}


		List<Computer> computers = computerService.findByName(name);

		List<ComputerDTO> computersDTO = ComputerMapper.toComputerDTOList(computers);

		request.setAttribute("computers", computersDTO);

		request.setAttribute("count", computersDTO.size());

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/results.jsp");
		dispatcher.forward(request, response);
	}
	
}
