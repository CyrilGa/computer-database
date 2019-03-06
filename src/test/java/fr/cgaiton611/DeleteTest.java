package fr.cgaiton611;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.service.ComputerService;
import fr.cgaiton611.springconfig.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
public class DeleteTest {
	
	private static WebDriver driver;
	@Autowired
	private ComputerService computerService;

	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.gecko.driver", "/home/cyril/Téléchargements/geckodriver");
		driver = new FirefoxDriver();
	}

	@Before
	public void beforeAll() {
		driver.get("http://localhost:8888/cdb/dashboard");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}


	@Test
	public void delete() {
		int c1 = computerService.count();
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
		
		int c2 = computerService.count();
		assertEquals(c1-c2, 2);
	}

	public void notDelete() {
		int c1 = computerService.count();
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
		
		int c2 = computerService.count();
		assertEquals(c1-c2, 2);
	}
	
	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

}
