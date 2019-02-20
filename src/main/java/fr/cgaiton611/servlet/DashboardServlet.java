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

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.model.ComputerPage;
import fr.cgaiton611.service.ComputerService;

@WebServlet(urlPatterns = { "/dashboard" })
public class DashboardServlet extends HttpServlet {

	private int[] ELEMENTS_AUTORISED = { 10, 50, 100 };

	private static final long serialVersionUID = 1L;

	private ComputerService computerService = ComputerService.getInstance();
	private int elements = 10;
	private int page = 0;
	private ComputerPage computerPage = new ComputerPage(elements);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pageAttribute = request.getParameter("page");
		if (pageAttribute != null)
			page = Integer.parseInt(pageAttribute);
		
		String elementsAttribute = request.getParameter("elements");
		if (elementsAttribute != null) {
			int temp = Integer.parseInt(elementsAttribute);
			if (elementsIsValid(temp)) {
				elements = temp;
				page = 0;
			}
		}


		computerPage.setElements(elements);

		List<Computer> computers = computerPage.get(page);

		List<ComputerDTO> computersDTO = ComputerMapper.toComputerDTOList(computers);

		request.setAttribute("computers", computersDTO);

		List<Integer> navigationPages = getNavigationPages(computerPage);
		request.setAttribute("navigationPages", navigationPages);

		request.setAttribute("count", computerService.count());

		request.setAttribute("page", computerPage.getPage());

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	public List<Integer> getNavigationPages(ComputerPage computerPage) {
		List<Integer> navigationPages = new ArrayList<>();
		int max = computerPage.getMax();
		int page = computerPage.getPage();
		boolean inf = true, sup = true;
		for (int i = page - 2; i < page + 3; i++) {
			if (i < 0)
				inf = false;
			else if (i > max)
				sup = false;
			else
				navigationPages.add(i);
		}

		while (navigationPages.size() < 5 && inf) {
			int ajout = navigationPages.get(0) - 1;
			if (ajout >= 0)
				navigationPages.add(0, ajout);
			else
				inf = false;
		}

		while (navigationPages.size() < 5 && sup) {
			int ajout = navigationPages.get(navigationPages.size() - 1) + 1;
			if (ajout >= 0)
				navigationPages.add(navigationPages.size(), ajout);
			else
				sup = false;
		}
		
		return navigationPages;
	}

	public boolean elementsIsValid(int elements) {
		for (int i : ELEMENTS_AUTORISED) {
			if (i == elements)
				return true;
		}
		return false;
	}

}
