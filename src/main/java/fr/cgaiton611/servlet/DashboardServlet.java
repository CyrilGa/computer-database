package fr.cgaiton611.servlet;

import java.io.IOException;
import java.text.MessageFormat;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.page.ComputerPage;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.util.ConvertUtil;

@WebServlet(urlPatterns = { "/dashboard", "" })
public class DashboardServlet extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

	private static final long serialVersionUID = 1L;
	
	private final String[] tableNames = {"Computer name", "Introduced date", "Discontinued date", "Company"};

	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerMapper computerMapper;

	@Autowired
	private ComputerPage computerPage;
	
	ConvertUtil convertUtil = new ConvertUtil();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String dashboardMsg = (String) session.getAttribute("dashboardMsg");
		request.setAttribute("dashboardMsg", dashboardMsg);
		session.removeAttribute("dashboardMsg");

		Optional<Integer> page = convertUtil.stringToInteger(request.getParameter("page"));
		if (page.isPresent()) {
			computerPage.setPage(page.get());
		}
		
		Optional<Integer> elements = convertUtil.stringToInteger(request.getParameter("elements"));
		if (elements.isPresent()) {
				computerPage.setElements(elements.get());
		}

		computerPage.setComputerName(request.getParameter("computerName"));
		request.setAttribute("computerName", computerPage.getComputerName());

		computerPage.setCompanyName(request.getParameter("companyName"));
		request.setAttribute("companyName", computerPage.getCompanyName());
		
		computerPage.setOrderByName(request.getParameter("orderByName"));
		request.setAttribute("orderByName", computerPage.getOrderByName());

		request.setAttribute("orderByOrder", computerPage.getOrderByOrder());



		List<Computer> computers = new ArrayList<>();
		try {
			computers = computerPage.getCurrent();
			List<ComputerDTO> computersDTO = computerMapper.toComputerDTOList(computers);
			request.setAttribute("computers", computersDTO);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			request.setAttribute("errorMsgList", "Database error. The computer list could not be loaded");
		}
		

		request.setAttribute("navigationPages", getNavigationPages(computerPage));

		int count = 0;
		try {
			count = computerService.countWithParameters(computerPage.getComputerName(), computerPage.getCompanyName());
			request.setAttribute("count", count);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			request.setAttribute("errorMsgCount", "Database error. The computer list could not be counted");
		}

		request.setAttribute("page", computerPage.getPage());
		
		request.setAttribute("ELEMENTS_AUTORISED", computerPage.ELEMENTS_AUTORISED);
		request.setAttribute("elements", computerPage.getElements());

		request.setAttribute("ORDERBYNAME_AUTORISED", computerPage.ORDERBYNAME_AUTORISED);
		request.setAttribute("tableNames", tableNames);
		

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/resources/views/dashboard.jsp");
		dispatcher.forward(request, response);

	}

	public List<Integer> getNavigationPages(ComputerPage computerPage) {
		List<Integer> navigationPages = new ArrayList<>();
		int max = computerPage.getMax();
		int page = computerPage.getPage();
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dashboardMsg = "Computer(s) successfully deleted";
		int count = 0;
		int N = 0;
		String selection = request.getParameter("selection");
		if (selection != null) {
			String[] ids = selection.split(",");
			N = ids.length;
			count = N;
			for (int i = 0; i < ids.length; i++) {
				try {
					computerService.delete(Integer.parseInt(ids[i]));
				} catch (NumberFormatException | DAOException e) {
					logger.warn(e.getMessage());
					count--;
				}
			}
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("dashboardMsg", MessageFormat.format("{0}sur{1} {2}", count, N, dashboardMsg));
		response.sendRedirect(request.getContextPath() + "/dashboard");
	}

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

}
