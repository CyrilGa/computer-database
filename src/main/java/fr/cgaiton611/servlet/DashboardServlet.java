package fr.cgaiton611.servlet;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.cgaiton611.dto.ComputerDTO;
import fr.cgaiton611.dto.ComputerMapper;
import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.page.ComputerPage;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.util.ConvertUtil;

@Controller
@RequestMapping("/dashboard")
public class DashboardServlet {

	private final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

	private final String[] tableNames = { "string.computerName", "string.introduced", "string.discontinued", "string.companyName" };

	@Autowired
	private ComputerService computerService;

	@Autowired
	private ComputerMapper computerMapper;

	@Autowired
	private ComputerPage computerPage;

	ConvertUtil convertUtil = new ConvertUtil();

	@GetMapping
	public String doGet(@RequestParam(required = false, name = "dashboardMsg") String pDashboardMsg,
			@RequestParam(required = false, name = "page") String pPage,
			@RequestParam(required = false, name = "elements") String pElements,
			@RequestParam(required = false, name = "computerName") String pComputerName,
			@RequestParam(required = false, name = "companyName") String pCompanyName,
			@RequestParam(required = false, name = "orderByName") String pOrderByName, Model model) {

		Locale currentLocale = LocaleContextHolder.getLocale();
		model.addAttribute("locale", currentLocale);

		model.addAttribute("dashboardMsg", pDashboardMsg);
		Optional<Integer> page = convertUtil.stringToInteger(pPage);
		if (page.isPresent()) {
			computerPage.setPage(page.get());
		}

		Optional<Integer> elements = convertUtil.stringToInteger(pElements);
		if (elements.isPresent()) {
			computerPage.setElements(elements.get());
		}

		computerPage.setComputerName(pComputerName);
		model.addAttribute("computerName", computerPage.getComputerName());

		computerPage.setCompanyName(pCompanyName);
		model.addAttribute("companyName", computerPage.getCompanyName());

		computerPage.setOrderByName(pOrderByName);
		model.addAttribute("orderByName", computerPage.getOrderByName());

		model.addAttribute("orderByOrder", computerPage.getOrderByOrder());

		List<Computer> computers = new ArrayList<>();
		try {
			computers = computerPage.getCurrent();
			List<ComputerDTO> computersDTO = computerMapper.toComputerDTOList(computers);
			model.addAttribute("computers", computersDTO);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			model.addAttribute("errorMsgList", "Database error. The computer list could not be loaded");
		}

		model.addAttribute("navigationPages", getNavigationPages(computerPage));

		int count = 0;
		try {
			count = computerService.countWithParameters(computerPage.getComputerName(), computerPage.getCompanyName());
			model.addAttribute("count", count);
		} catch (DAOException e) {
			logger.warn(e.getMessage());
			model.addAttribute("errorMsgCount", "Database error. The computer list could not be counted");
		}

		model.addAttribute("page", computerPage.getPage());

		model.addAttribute("ELEMENTS_AUTORISED", computerPage.ELEMENTS_AUTORISED);
		model.addAttribute("elements", computerPage.getElements());

		model.addAttribute("ORDERBYNAME_AUTORISED", computerPage.ORDERBYNAME_AUTORISED);
		model.addAttribute("tableNames", tableNames);

		return "dashboard";

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

	@PostMapping
	public String doPost(@RequestParam(required = false, name = "selection") String pSelection,
			RedirectAttributes redirectAttributes) {
		String dashboardMsg = "Computer(s) successfully deleted";
		int count = 0;
		int N = 0;
		if (pSelection != null) {
			String[] ids = pSelection.split(",");
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
		redirectAttributes.addAttribute("dashboardMsg",
				MessageFormat.format("{0} sur {1} {2}", count, N, dashboardMsg));
		return "redirect:dashboard";
	}

}
