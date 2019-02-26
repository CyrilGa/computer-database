package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		driver.get("http://localhost:8888/cdb/dashboard");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}


	@Test
	public void delete() {
		int c1 = ComputerService.getInstance().count();
		driver.findElement(By.id("editComputer")).click();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
		driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]/input")).click();
		driver.findElement(By.id("deleteSelected")).click();
		driver.switchTo().alert().accept();
		new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("http://localhost:8888/cdb/dashboard"));
		String url = driver.getCurrentUrl();
		System.out.println(url);
		assertTrue("http://localhost:8888/cdb/dashboard".equals(url));
		String dashMsg = driver.findElement(By.id("dashMsg")).getText();
		System.out.println("DASHMSG ::::::::::::: "+dashMsg);
		assertTrue("Computer successfully deleted".equals(dashMsg));
		
		int c2 = ComputerService.getInstance().count();
		assertEquals(c1-c2, 2);
	}

	public void notDelete() {
		int c1 = ComputerService.getInstance().count();
		driver.findElement(By.id("editComputer")).click();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
		driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]/input")).click();
		driver.findElement(By.id("deleteSelected")).click();
		driver.switchTo().alert().dismiss();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/input")).click();
		driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]/input")).click();
		driver.findElement(By.id("editComputer")).click();
		
		String url = driver.getCurrentUrl();
		System.out.println(url);
		assertTrue("http://localhost:8888/cdb/dashboard#".equals(url));
		
		int c2 = ComputerService.getInstance().count();
		assertEquals(c1-c2, 2);
	}
	
	@AfterAll
	public static void tearDown() {
		driver.quit();
	}

}
