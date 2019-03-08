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

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.springconfig.SpringConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class EditTest {
	private static WebDriver driver;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "/home/cyril/Téléchargements/geckodriver");
		driver = new FirefoxDriver();
	}

	@Before
	public void beforeAll() {
		if (! companyService.findByName("TEST COMPANY EDIT").isPresent()) {
			companyService.create("TEST COMPANY EDIT");
		}
		if (!(computerService.findByNamePaged(0, 1, "TEST COMPUTER EDIT", "").size() == 1)) {
			computerService.create(new Computer("TEST COMPUTER EDIT", null, null, new Company()));
		}
		driver.get("http://localhost:8888/cdb/dashboard");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
		assertTrue("http://localhost:8888/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashMsg")).getText();
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
		assertTrue("http://localhost:8888/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashMsg")).getText();
		assertTrue("Computer successfully updated".equals(dashMsg));
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}
}
