import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.failsafe.internal.util.Assert;
public class WesternUnionTask1Test {
	private WebDriver driver;
	@Before
	public void setUp() {   
		System.setProperty("webdriver.chrome.driver",
			"./src/test/resources/chromedriver/chromedriver.exe");
		 System.setProperty("webdriver.chrome.whitelistedIps", "");
		driver = new ChromeDriver();
		driver.manage().window().maximize(); 
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("https://www.westernunion.com/lt/en/home.html");
	} 
	@Test
	public void testTask1 (){
		//close tooltip, must wait to ensure the whole page is loaded.
		WebElement tip = driver.findElement(By.id("evergage-tooltip-ambzBhez"));
		tip.findElement(By.cssSelector("a[title=\"Close Message\"]")).click();
		// navigate burguerMenu, open it.
		WebElement burguerMenu = driver.findElement(By.id("hamburger-nav-item"));
		burguerMenu.click();
		// select settings item.
		WebElement subMenu = driver.findElement(By.cssSelector("#hamburger-nav-item ul li:last-child"));
		Actions actions = new Actions(driver);
		actions.moveToElement(subMenu);
		actions.click().build().perform();
		// Open countrySelect input
		WebElement countrySelect = driver.findElement(By.id("Question"));
		countrySelect.click();
		// Select value to dropdown, 252 is USA
		Select dropdown = new Select(countrySelect);
		dropdown.selectByValue("object:252");
		// Click accept button
		WebElement yesButton = driver.findElement(By.cssSelector(".info-popup-container button:not(.btn-popup-negative)"));
		yesButton.click();
		// compares new url, 
		String url = driver.getCurrentUrl();
		assertEquals("https://www.westernunion.com/us/en/home.html",url);
	}
	@After
	public void tearDown() {
		//driver.quit();
	}
	
}
