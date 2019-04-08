package fr.cgaiton611.cdb.selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

import fr.cgaiton611.cdb.config.HibernateConfigTest;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.NoRowUpdatedException;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.service.CompanyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
public class AddTest {

	private final Logger logger = LoggerFactory.getLogger(AddTest.class);
	private static WebDriver driver;
	@Autowired
	private CompanyService companyService;

	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "/home/cyril/Téléchargements/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Before
	public void beforeAll() {
		try {
			companyService.findByName("TEST COMPANY ADD");
		} catch (NoRowUpdatedException e) {
			try {
				companyService.create(new Company("TEST COMPANY ADD"));
			} catch (DAOException e1) {
				logger.error(e.getMessage());
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		driver.get("http://localhost:9090/cdb/addComputer");
	}

	@Test
	public void computerNameAndCompanyName() {
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("computerName")).sendKeys("computer");
		assertFalse(driver.findElement(By.id("btnSubmit")).isEnabled());
		new Select(driver.findElement(By.id("companyName"))).selectByVisibleText("TEST COMPANY ADD");
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("page-title")).click();
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("btnSubmit")).click();

		String url = driver.getCurrentUrl();
		logger.debug(url);
		assertTrue(url.startsWith("http://localhost:9090/cdb/dashboard"));
		String dashMsg = driver.findElement(By.id("dashboardMsg")).getText();
		logger.debug(dashMsg);
		assertTrue(dashMsg.startsWith("Computer successfully created"));
	}

	public void computerNameAndIntroduced() {
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("computerName")).sendKeys("computer");
		assertFalse(driver.findElement(By.id("btnSubmit")).isEnabled());
		driver.findElement(By.id("introducedDate")).sendKeys("20012012");
		assertFalse(driver.findElement(By.id("btnSubmit")).isEnabled());
		driver.findElement(By.id("introducedTime")).sendKeys("2222");
		assertFalse(driver.findElement(By.id("btnSubmit")).isEnabled());
		new Select(driver.findElement(By.id("companyName"))).selectByVisibleText("TEST COMPANY ADD");
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("page-title")).click();
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("btnSubmit")).click();

		String url = driver.getCurrentUrl();
		System.out.println(url);
		assertTrue(url.startsWith("http://localhost:9090/cdb/dashboard"));
		String dashMsg = driver.findElement(By.id("dashMsg")).getText();
		assertTrue("Computer successfully created".equals(dashMsg));
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}
}
