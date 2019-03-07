package fr.cgaiton611.selenium;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.springconfig.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class AddTest {
	private static WebDriver driver;
	@Autowired
	private CompanyService companyService;

	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "/home/cyril/Téléchargements/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Before
	public void beforeAll() {
		if (!companyService.findByName("TEST COMPANY ADD").isPresent()) {
			companyService.create("TEST COMPANY ADD");
		}
		driver.get("http://localhost:8888/cdb/addComputer");
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
		assertTrue("http://localhost:8888/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashMsg")).getText();
		assertTrue("Computer successfully created".equals(dashMsg));
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
		assertTrue("http://localhost:8888/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashMsg")).getText();
		assertTrue("Computer successfully created".equals(dashMsg));
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}
}
