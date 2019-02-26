package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import fr.cgaiton611.service.ComputerService;

public class DeleteTest {
	
	private static WebDriver driver;

	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "/home/cyril/Téléchargements/geckodriver");
		driver = new FirefoxDriver();
	}

	@BeforeEach
	public void beforeAll() {
		driver.get("http://localhost:8080/cdb/dashboard");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}


	@Test
	public void delete() {
		int c1 = ComputerService.getInstance().count();
		driver.findElement(By.id("editComputer")).click();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/input")).click();
		driver.findElement(By.xpath("deleteSelected")).click();
		driver.findElement(By.xpath("html")).sendKeys(Keys.ENTER);
		
		String url = driver.getCurrentUrl();
		System.out.println(url);
		assertTrue("http://localhost:8080/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashMsg")).getText();
		assertTrue("Computer successfully deleted".equals(dashMsg));
		
		int c2 = ComputerService.getInstance().count();
		assertEquals(c1-c2, 2);
	}

	public void computerNameAndIntroduced() {
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("computerName")).sendKeys(" edited");
		driver.findElement(By.id("computerName")).sendKeys("computer");
		driver.findElement(By.id("introducedDate")).sendKeys("20012012");
		driver.findElement(By.id("introducedTime")).sendKeys("2222");
		assertTrue(driver.findElement(By.id("btnSubmit")).isEnabled());
		new Select(driver.findElement(By.id("companyName"))).selectByVisibleText("Commodore International");
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("page-title")).click();
		driver.findElement(By.id("computerName")).click();
		driver.findElement(By.id("btnSubmit")).click();

		String url = driver.getCurrentUrl();
		System.out.println(url);
		assertTrue("http://localhost:8080/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashMsg")).getText();
		assertTrue("Computer successfully updated".equals(dashMsg));
	}
	
	@AfterAll
	public static void tearDown() {
		driver.quit();
	}

}
