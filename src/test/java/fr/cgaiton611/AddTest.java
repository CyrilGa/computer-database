package fr.cgaiton611;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
	public class TestAdd {
		private WebDriver driver;
		private StringBuffer verificationErrors = new StringBuffer();

		@BeforeAll
		public void setUp() {

			System.setProperty("webdriver.firefox.marionette", "C:\\geckodriver.exe");
			WebDriver driver = new FirefoxDriver();
		}

		@BeforeEach
		public void beforeAll() {
			driver.get("http://localhost:8080/cdb/dashboard");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}

		@Test
		public void computerName() {
			driver.findElement(By.id("addComputer")).click();
			driver.findElement(By.id("submit")).click();
			driver.findElement(By.id("computerName")).sendKeys("user");
			driver.findElement(By.id("addComputer")).click();
			String url = driver.getCurrentUrl();
			assertTrue("http://localhost:8080/cdb/dashboard".equals(url));
			String dashMsg = driver.findElement(By.id("dashMsg")).getText();
			System.out.println(">>>" + dashMsg + "<<<");
			assertTrue("".equals(dashMsg));
		}

		@Test
		public void computerNameAndCompanyName() {
			driver.findElement(By.id("addComputer")).click();
			driver.findElement(By.id("submit")).click();
			driver.findElement(By.id("computrName")).sendKeys("user");
			new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Commodore International");
			driver.findElement(By.id("addComputer")).click();
			String url = driver.getCurrentUrl();
			System.out.println(url);
			assertTrue("http://localhost:8080/cdb/dashboard".equals(url));
			String dashMsg = driver.findElement(By.id("dashMsg")).getText();
			System.out.println(">>>" + dashMsg + "<<<");
			assertTrue("".equals(dashMsg));
		}

		@AfterAll
		public void tearDown() {
			driver.quit();
			String verificationErrorString = verificationErrors.toString();
			if (!"".equals(verificationErrorString)) {
				fail(verificationErrorString);
			}
		}
	}
}