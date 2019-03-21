package fr.cgaiton611.selenium;

import static org.junit.Assert.assertEquals;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.springconfig.HibernateConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class DeleteTest {

	private final Logger logger = LoggerFactory.getLogger(DeleteTest.class);

	private static WebDriver driver;
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
			if (computerService.findPageWithParameters(0, 1, "TEST 1 COMPUTER DELETE", "", "cpu.id", "ASC")
					.size() == 0) {
				computerService.create(new Computer("TEST 1 COMPUTER DELETE", null, null, new Company()));
			}
			if (computerService.findPageWithParameters(0, 1, "TEST 2 COMPUTER DELETE", "", "cpu.id", "ASC")
					.size() == 0) {
				computerService.create(new Computer("TEST 2 COMPUTER DELETE", null, null, new Company()));
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		driver.get("http://localhost:9090/cdb/dashboard");
	}

	@Test
	public void delete() {
		int c1 = 0;
		try {
			c1 = computerService.count();
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		driver.findElement(By.id("editComputer")).click();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
		driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]/input")).click();
		;
		driver.findElement(By.id("deleteSelected")).click();
		driver.switchTo().alert().accept();
		new WebDriverWait(driver, 2);
		String url = driver.getCurrentUrl();
		assertTrue(url.startsWith("http://localhost:9090/cdb/dashboard"));
		String dashMsg = driver.findElement(By.id("dashboardMsg")).getText();
		assertTrue(dashMsg.endsWith("Computer(s) successfully deleted"));

		int c2 = 0;
		try {
			c2 = computerService.count();
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		logger.debug("c1 "+c1);
		logger.debug("c2 "+c2);
		assertEquals(c1 - c2, 2);
	}

	public void notDelete() {
		int c1 = 0;
		try {
			c1 = computerService.count();
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		driver.findElement(By.id("editComputer")).click();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
		driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]/input")).click();
		driver.findElement(By.id("deleteSelected")).click();
		driver.switchTo().alert().dismiss();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
		driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]/input")).click();
		driver.findElement(By.id("editComputer")).click();

		String url = driver.getCurrentUrl();
		assertTrue(url.startsWith("http://localhost:9090/cdb/dashboard"));

		int c2 = 0;
		try {
			c2 = computerService.count();
		} catch (DAOException e) {
			logger.warn(e.getMessage());
		}
		assertEquals(c1 - c2, 2);
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

}
