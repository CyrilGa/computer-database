package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AddTest {
	private static WebDriver driver;

	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "/home/cyril/Téléchargements/geckodriver");
		driver = new FirefoxDriver();
	}

	@BeforeEach
	public void beforeAll() {
		driver.get("http://localhost:8888/cdb/addComputer");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}


	@Test
	public void computerNameAndCompanyName() {
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("computerName")).sendKeys("computer");
		assertFalse(driver.findElement(By.id("btnSubmit")).isEnabled());
		new Select(driver.findElement(By.id("companyName"))).selectByVisibleText("Commodore International");
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

	public void computerNameAndIntroduced() {
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("computerName")).sendKeys("computer");
		assertFalse(driver.findElement(By.id("btnSubmit")).isEnabled());
		driver.findElement(By.id("introducedDate")).sendKeys("20012012");
		assertFalse(driver.findElement(By.id("btnSubmit")).isEnabled());
		driver.findElement(By.id("introducedTime")).sendKeys("2222");
		assertFalse(driver.findElement(By.id("btnSubmit")).isEnabled());
		new Select(driver.findElement(By.id("companyName"))).selectByVisibleText("Commodore International");
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
	
	@AfterAll
	public static void tearDown() {
		driver.quit();
	}
}
