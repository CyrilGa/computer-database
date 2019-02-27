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

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.model.ComputerByNamePage;
import fr.cgaiton611.service.ComputerService;

@WebServlet(urlPatterns = { "/dashboard", "" })
public class DashboardServlet extends HttpServlet {

	private int[] ELEMENTS_AUTORISED = { 10, 50, 100 };

	private static final long serialVersionUID = 1L;

	private ComputerService computerService = ComputerService.getInstance();
	private int elements = 10;
	private int page = 0;
	private String name = "";
	private ComputerByNamePage computerByNamePage = new ComputerByNamePage(elements, name);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null) {
			String dashboardMsg = (String) session.getAttribute("dashboardMsg");
			if (dashboardMsg != null) {
				request.setAttribute("dashboardMsg", dashboardMsg);
				session.removeAttribute("dashboardMsg");
			}
		}

		name = request.getParameter("search");
		computerByNamePage.setName(name);
		request.setAttribute("valueSearch", computerByNamePage.getName());

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

		computerByNamePage.setElements(elements);

		List<Computer> computers = computerByNamePage.get(page);

		List<ComputerDTO> computersDTO = ComputerMapper.toComputerDTOList(computers);

		request.setAttribute("computers", computersDTO);

		List<Integer> navigationPages = getNavigationPages(computerByNamePage);
		request.setAttribute("navigationPages", navigationPages);

		request.setAttribute("count", computerService.countByName(computerByNamePage.getName()));

		request.setAttribute("page", computerByNamePage.getPage());

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ressources/views/dashboard.jsp");
		dispatcher.forward(request, response);

	}

	public List<Integer> getNavigationPages(ComputerByNamePage computerByNamePage) {
		List<Integer> navigationPages = new ArrayList<>();
		int max = computerByNamePage.getMax();
		int page = computerByNamePage.getPage();
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selection = request.getParameter("selection");
		if (selection != null) {
			String[] ids = selection.split(",");
			for (int i = 0; i < ids.length; i++) {
				computerService.delete(Integer.parseInt(ids[i]));
			}
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("dashboardMsg", "Computer successfully deleted");
		response.sendRedirect(request.getContextPath() + "/dashboard");
	}

}
