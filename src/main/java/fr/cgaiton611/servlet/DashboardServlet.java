package fr.cgaiton611.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import fr.cgaiton611.page.ComputerByNamePage;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.springconfig.SpringConfig;
import fr.cgaiton611.util.ConvertUtil;

@WebServlet(urlPatterns = { "/dashboard", "" })
public class DashboardServlet extends HttpServlet {

	private int[] ELEMENTS_AUTORISED = { 10, 50, 100 };

	private static final long serialVersionUID = 1L;

	ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
	private ComputerService computerService = context.getBean(ComputerService.class);
	private ComputerMapper computerMapper = context.getBean(ComputerMapper.class);
	private int elements = 10;
	private int page = 0;
	private String computerName = "";
	private String companyName = "";

    
	private ComputerByNamePage computerByNamePage = context.getBean(ComputerByNamePage.class);
	ConvertUtil convertUtil = new ConvertUtil();

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

		String pageAttribute = request.getParameter("page");
		Optional<Integer> pageTemp = convertUtil.stringToInteger(pageAttribute);
		if (pageTemp.isPresent()) {
				page = pageTemp.get();
		}
		computerByNamePage.setPage(page);

		computerName = request.getParameter("computerName");
		computerByNamePage.setComputerName(computerName);
		request.setAttribute("computerName", computerByNamePage.getComputerName());

		companyName = request.getParameter("companyName");
		computerByNamePage.setCompanyName(companyName);
		request.setAttribute("companyName", computerByNamePage.getCompanyName());

		String elementsAttribute = request.getParameter("elements");
		Optional<Integer> elementsTemp = convertUtil.stringToInteger(elementsAttribute);
		if (elementsTemp.isPresent()) {
			if (elementsIsValid(elementsTemp.get())) {
				elements = elementsTemp.get();
			}
		}
		computerByNamePage.setElements(elements);

		List<Computer> computers = computerByNamePage.get();

		List<ComputerDTO> computersDTO = computerMapper.toComputerDTOList(computers);

		request.setAttribute("computers", computersDTO);

		List<Integer> navigationPages = getNavigationPages(computerByNamePage);
		request.setAttribute("navigationPages", navigationPages);

		request.setAttribute("count",
				computerService.countByName(computerByNamePage.getComputerName(), computerByNamePage.getCompanyName()));

		request.setAttribute("page", computerByNamePage.getPage());

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/resources/views/dashboard.jsp");
		dispatcher.forward(request, response);

	}

	public List<Integer> getNavigationPages(ComputerByNamePage computerByNamePage) {
		List<Integer> navigationPages = new ArrayList<>();
		int max = computerByNamePage.getMax();
		int page = computerByNamePage.getPage();
		boolean inf = true, sup = true;
		for (int i = page - 2; i < page + 3; i++) {
			if (i < 0) {
				inf = false;
			} else if (i > max) {
				sup = false;
			} else {
				navigationPages.add(i);
			}
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
			if (ajout <= max)
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
			System.out.println(ids);
			for (int i = 0; i < ids.length; i++) {
				computerService.delete(Integer.parseInt(ids[i]));
			}
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("dashboardMsg", "Computer successfully deleted");
		response.sendRedirect(request.getContextPath() + "/dashboard");
	}

}
