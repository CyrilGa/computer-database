package fr.cgaiton611.selenium;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.dao.EmptyResultSetException;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.springconfig.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class EditTest {

	private final Logger logger = LoggerFactory.getLogger(EditTest.class);
	private static WebDriver driver;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;

	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "/home/cyril/Téléchargements/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Before
	public void beforeAll() {
		try {
			companyService.findByName("TEST COMPANY EDIT");
			logger.info("TEST COMANY EDIT already created");
		} catch (EmptyResultSetException e) {
			try {
				companyService.create("TEST COMPANY EDIT");
			} catch (DAOException e1) {
				logger.error(e.getMessage());
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}

		try {
			List<Computer> computers = computerService.findPageWithParameters(0, 1, "TEST COMPUTER EDIT", "",
					"computer.id", "ASC");
			if (computers.size() == 0) {
				computerService.create(new Computer("TEST COMPUTER EDIT", null, null, new Company()));
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		driver.get("http://localhost:9090/cdb/dashboard");
	}

	@Test
	public void computerNameAndCompanyName() {
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/a")).click();
		driver.findElement(By.id("computerName")).sendKeys(" edited");
		new Select(driver.findElement(By.id("companyName"))).selectByVisibleText("TEST COMPANY EDIT");
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("page-title")).click();
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("btnSubmit")).click();

		String url = driver.getCurrentUrl();
		assertTrue("http://localhost:9090/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashboardMsg")).getText();
		assertTrue("Computer successfully updated".equals(dashMsg));
	}

	@Test
	public void computerNameAndIntroduced() {
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/a")).click();
		driver.findElement(By.id("computerName")).sendKeys(" edited");
		driver.findElement(By.id("computerName")).sendKeys("computer");
		driver.findElement(By.id("introducedDate")).sendKeys("20012012");
		driver.findElement(By.id("introducedTime")).sendKeys("2222");
		new Select(driver.findElement(By.id("companyName"))).selectByVisibleText("TEST COMPANY EDIT");
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("page-title")).click();
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("btnSubmit")).click();

		String url = driver.getCurrentUrl();
		assertTrue("http://localhost:9090/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashboardMsg")).getText();
		assertTrue("Computer successfully updated".equals(dashMsg));
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}
}
